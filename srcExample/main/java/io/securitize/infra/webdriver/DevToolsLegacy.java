package io.securitize.infra.webdriver;

import com.github.kklisura.cdt.services.ChromeDevToolsService;
import com.github.kklisura.cdt.services.WebSocketService;
import com.github.kklisura.cdt.services.config.ChromeDevToolsServiceConfiguration;
import com.github.kklisura.cdt.services.exceptions.WebSocketServiceException;
import com.github.kklisura.cdt.services.impl.ChromeDevToolsServiceImpl;
import com.github.kklisura.cdt.services.impl.WebSocketServiceImpl;
import com.github.kklisura.cdt.services.invocation.CommandInvocationHandler;
import com.github.kklisura.cdt.services.utils.ProxyUtils;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

import static io.securitize.infra.reporting.MultiReporter.debug;
import static io.securitize.infra.reporting.MultiReporter.info;

public class DevToolsLegacy {

    private DevToolsLegacy() {

    }

    public static void setChromeDevToolsConfiguration(String sessionsId, Method testMethod) {
        try {
            logChromeNetworkErrors(sessionsId, testMethod);
        } catch (Exception e) {
            String message = "An error occur trying to connect to remote CDP session for network errors logging. Details: " + e;
            info(message);
            WebDriverFactory.networkErrors.put(sessionsId, Collections.singletonList(message));
        }
    }

    static void setChromeExtensionParameters(WebDriver driver) {
        // set default http authentication username and password

        String chromeExtensionId = MainConfig.getProperty(MainConfigProperty.chromeExtensionId);
        driver.navigate().to("chrome-extension://" + chromeExtensionId + "/logo.png");

        var javascriptExecutor = (JavascriptExecutor) driver;

        if ((MainConfig.getBooleanProperty(MainConfigProperty.handleHttpAuthentication))) {
            String httpUsername = Users.getProperty(UsersProperty.httpAuthenticationUsername);
            String httpPassword = Users.getProperty(UsersProperty.httpAuthenticationPassword);

            var script = "localStorage.setItem('httpAuthenticationFeature', 'true');";
            script += "localStorage.setItem('user_name', '" + httpUsername + "');";
            script += "localStorage.setItem('password', '" + httpPassword + "');";

            javascriptExecutor.executeScript(script);
        }

        if (MainConfig.getBooleanProperty(MainConfigProperty.addCustomHttpHeader)) {
            String domainToAddHttpHeader = MainConfig.getProperty(MainConfigProperty.addHTTPHeaderToDomain);
            String httpHeaderName = Users.getProperty(UsersProperty.httpHeaderNameToAdd);
            String httpHeaderValue = Users.getProperty(UsersProperty.httpHeaderValueToAdd);

            var script = "localStorage.setItem('httpCustomHeaderFeature', 'true');";
            script += "localStorage.setItem('domainToAddHttpHeader', '" + domainToAddHttpHeader + "');";
            script += "localStorage.setItem('httpHeaderName', '" + httpHeaderName + "');";
            script += "localStorage.setItem('httpHeaderValue', '" + httpHeaderValue + "');";

            javascriptExecutor.executeScript(script);
        }
    }

    private static void logChromeNetworkErrors(String sessionId, Method testMethod) throws URISyntaxException, WebSocketServiceException, MalformedURLException {
        WebDriverFactory.networkErrors.put(sessionId, new ArrayList<>());

        String hubUrl = WebDriverFactory.getHubUrl(testMethod).toString().replace("http", "ws").replace("/wd/hub", "");
        String webSocketURL = hubUrl + "/devtools/" + sessionId + "/page";

        // sometimes first connection attempt fails.. add retries
        var maxRetries = 5;
        var sleepSecondsBetweenAttempts = 10;
        ChromeDevToolsService devToolsService = null;

        for (var i = 1; i <= maxRetries; i++) {
            try {
                info(String.format("Connecting to remote devtools websocket of: %s. Attempt %s/%s", webSocketURL, i, maxRetries));

                var webSocketService = WebSocketServiceImpl.create(new URI(webSocketURL));
                var commandInvocationHandler = new CommandInvocationHandler();
                Map<Method, Object> commandsCache = new ConcurrentHashMap<>();
                devToolsService =
                        ProxyUtils.createProxyFromAbstract(
                                ChromeDevToolsServiceImpl.class,
                                new Class[]{WebSocketService.class, ChromeDevToolsServiceConfiguration.class},
                                new Object[]{webSocketService, new ChromeDevToolsServiceConfiguration()},
                                (unused, method, args2) ->
                                        commandsCache.computeIfAbsent(
                                                method,
                                                key -> {
                                                    Class<?> returnType = method.getReturnType();
                                                    return ProxyUtils.createProxy(returnType, commandInvocationHandler);
                                                }));
                commandInvocationHandler.setChromeDevToolsService(devToolsService);
                break;
            } catch (Exception e) {
                if (i < maxRetries) {
                    info(String.format("An error occur trying to connect to CDP. Details: %s. Sleeping for %s seconds and trying again...", e, sleepSecondsBetweenAttempts));
                    try {
                        Thread.sleep(sleepSecondsBetweenAttempts * 1000L);
                    } catch (InterruptedException ex) {
                        debug("Thread sleep was Interrupted");
                        Thread.currentThread().interrupt();
                        return;
                    }
                } else {
                    throw e;
                }
            }
        }

        // Get individual commands
        assert devToolsService != null;
        final var network = devToolsService.getNetwork();

        network.onResponseReceived(
                event -> {
                    String requestUrl;
                    String statusCode;
                    String requestHeaders;

                    try {
                        statusCode = event.getResponse().getStatus() + "";
                    } catch (Exception e) {
                        statusCode = "Can't extract status code. Details: " + e;
                    }

                    // ignore 2xx and 3xx responses
                    if (statusCode.startsWith("2") || statusCode.startsWith("3")) {
                        return;
                    }

                    try {
                        requestUrl = event.getResponse().getUrl();
                    } catch (Exception e) {
                        requestUrl = "Can't extract url. Details: " + e;
                    }


                    try {
                        Map<String, Object> rawRequestHeaders = event.getResponse().getRequestHeaders();
                        if (rawRequestHeaders == null) {
                            requestHeaders = null;
                        } else {
                            requestHeaders = rawRequestHeaders.keySet().stream()
                                    .map(key -> key + "=" + rawRequestHeaders.get(key))
                                    .collect(Collectors.joining(System.lineSeparator(), "", ""));
                        }
                    } catch (Exception e) {
                        requestHeaders = "Can't extract request headers. Details: " + e;
                    }


                    // minimal documentation for 4xx responses
                    if (statusCode.startsWith("4")) {
                        WebDriverFactory.networkErrors.get(sessionId).add("url: " + requestUrl + " status: " + statusCode);
                    } else if (statusCode.startsWith("5")) { // extended documentation for 5xx responses
                        WebDriverFactory.networkErrors.get(sessionId).add("url: " + requestUrl + " status: " + statusCode + " Request headers: " + requestHeaders);
                    }
                }
        );

        // Enable network events.
        network.enable();
    }
}
