package io.securitize.infra.webdriver;

import io.appium.java_client.ios.IOSDriver;
import io.appium.java_client.ios.options.XCUITestOptions;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.securitize.infra.config.MainConfig;
import io.securitize.infra.config.MainConfigProperty;
import io.securitize.infra.config.Users;
import io.securitize.infra.config.UsersProperty;
import io.securitize.infra.reporting.MultiReporter;
import io.securitize.infra.utils.BypassRecaptcha;
import io.securitize.infra.utils.RegexWrapper;
import io.securitize.infra.utils.ResourcesUtils;
import io.securitize.infra.utils.VideoRecorder;
import io.securitize.infra.wallets.WalletExtension;
import org.apache.commons.lang.exception.ExceptionUtils;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONObject;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.devtools.Command;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.HasDevTools;
import org.openqa.selenium.devtools.v119.network.model.Headers;
import org.openqa.selenium.remote.Augmenter;
import org.openqa.selenium.remote.LocalFileDetector;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.http.ClientConfig;
import org.openqa.selenium.support.events.EventFiringDecorator;
import org.openqa.selenium.support.events.WebDriverListener;
import org.web3j.utils.Files;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.*;
import java.util.logging.Level;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.securitize.infra.reporting.MultiReporter.*;

public class WebDriverFactory {

	private static final ThreadLocal<String> remoteSessionId = new ThreadLocal<>();
	static final HashMap<String, List<String>> networkErrors = new HashMap<>();
	private static final ThreadLocal<Method> testMethods = new ThreadLocal<>();
	private static final ThreadLocal<Boolean> shouldLoadBrowserContext = new ThreadLocal<>();

	private static ThreadLocal<WebDriver> originalDriver = new ThreadLocal<>();

	public static WebDriver getWebDriver(Method testMethod, String methodFullName, WalletExtension walletExtension) throws Exception {

		WebDriver driver;
		RemoteWebDriver remoteDriver = null;
		URL hub;
		String videoFileName;
		String videoURLMoonPrefix = MainConfig.getProperty(MainConfigProperty.videoBucketMoonPrefix);
		String videoURLJenkinsPrefix = MainConfig.getProperty(MainConfigProperty.videoBucketJenkinsPrefix);
		String deviceToEmulate = MainConfig.getProperty(MainConfigProperty.browserDeviceNameToEmulate);
		remoteSessionId.remove();
		testMethods.remove();
		shouldLoadBrowserContext.remove();
		originalDriver.remove();

		testMethods.set(testMethod);

		// hide chromedriver logs when running in debug mode unless they are important
		java.util.logging.Logger.getLogger("org.openqa.selenium").setLevel(Level.SEVERE);

		String browserType = MainConfig.getProperty(MainConfigProperty.browserType).toLowerCase();
		switch (browserType) {

			case "chrome":
				ChromeOptions chromeOptions = chromeOptionsInit(testMethod, walletExtension, false);

				WebDriverManager.chromedriver().setup();
				var chromeDriver = new ChromeDriver(chromeOptions);
				validateOnlyOneTabOpened(chromeDriver, walletExtension);
				var randomSessionId = UUID.randomUUID().toString();
				remoteSessionId.set(randomSessionId);
				setChromeDevToolsConfiguration(chromeDriver, randomSessionId);


				if ((StringUtils.isEmpty(deviceToEmulate)) || (deviceToEmulate.equalsIgnoreCase("desktop"))) {
					chromeDriver.manage().window().maximize();
				} else {
					setBrowserDimensionsToMatchEmulatedDevice(chromeDriver);
				}

				if (MainConfig.getBooleanProperty(MainConfigProperty.videoRecording)) {
					videoFileName = VideoRecorder.start();
					MultiReporter.addVideoRecording(Collections.singletonMap("local video", videoFileName));
				}

				driver = chromeDriver;
				break;

			case "chrome-remote":
				hub = getHubUrl(testMethod);
				ChromeOptions co = chromeOptionsInit(testMethod, walletExtension, true);

				// only needed in case of Moon2
				if (isRemoteMoon2(testMethod)) {
					String browserVersion = MainConfig.getProperty(MainConfigProperty.browserVersion);
					if (!StringUtils.isEmpty(browserVersion)) {
						co.setCapability("browserVersion", browserVersion); // store selenoid session logs
					}
				}

				// selenoid related capabilities
				Map<String, Object> selenoidCapabilitiesMap = new HashMap<>();

				if (MainConfig.getBooleanProperty(MainConfigProperty.videoRecording)) {
					selenoidCapabilitiesMap.put("enableVideo", true);
					selenoidCapabilitiesMap.put("videoFrameRate", 8);
				}

				selenoidCapabilitiesMap.put("enableVNC", true);
				selenoidCapabilitiesMap.put("enableLog", true); // store selenoid session logs

				// custom Moon capabilities:
				// 1. verbose chromeDriver logs to debug browser crashes
				selenoidCapabilitiesMap.put("env", Collections.singletonList("VERBOSE=true"));

				if (methodFullName != null) {
					selenoidCapabilitiesMap.put("name", methodFullName);
				}

				if (hub.toString().contains("moon")) {
					// add Japanese support for JP tests running on Moon
					String currentTestDomain = RegexWrapper.getDomainNameFromClassName(testMethod.getDeclaringClass().getName());
					if (currentTestDomain.equalsIgnoreCase("jp")) {
						selenoidCapabilitiesMap.put("additionalFonts", true);
					}

					// if this is moon2, and we need to load the browser context
					if (shouldLoadBrowserContext.get() != null && shouldLoadBrowserContext.get()) {
						shouldLoadBrowserContext.remove();
						String contextFileFullPath = MainConfig.getProperty(MainConfigProperty.videoBucketMoonPrefix) + MainConfig.getProperty(MainConfigProperty.moonBrowserContextFile);
						selenoidCapabilitiesMap.put("context", contextFileFullPath);
					}
					co.setCapability("moon:options", selenoidCapabilitiesMap);
				} else {
					co.setCapability("selenoid:options", selenoidCapabilitiesMap);
				}


				int maxAttempts = 15;
				for (int i = 1; i <= maxAttempts; i++) {
					try {
						info(String.format("Attempt #%s/%s of opening remote browser on %s...", i, maxAttempts, hub));

						// The default session open timeout stands on 3 minutes which is usually
						// enough - but in case there is a long queue in Moon it might not be enough
						remoteDriver = (RemoteWebDriver) RemoteWebDriver.builder()
								.config(ClientConfig.defaultConfig()
										.connectionTimeout(Duration.ofMinutes(30))
										.readTimeout(Duration.ofMinutes(30)))
								.address(hub)
								.addAlternative(co)
								.build();
						validateOnlyOneTabOpened(remoteDriver, walletExtension);
						info("Browser opened!");
						break;
					} catch (WebDriverException e) {
						if (i < maxAttempts) {
							if (e.getMessage().contains("504 Gateway")) {
								info("An error occur trying to open browser.. Will try again in 10 seconds. Details: " + e);
							} else {
								info(String.format("Unable to open browser on %s. Details: %s", hub, ExceptionUtils.getFullStackTrace(e)));
							}
							Thread.sleep(10 * 1000L);
						} else {
							errorAndStop("Unable to open browser even after " + maxAttempts + " attempts. Details: " + e, false, e);
						}
					}
				}

				assert remoteDriver != null;
				var remoteSessionID = remoteDriver.getSessionId().toString();
				remoteSessionId.set(remoteSessionID);

				if (MainConfig.getBooleanProperty(MainConfigProperty.videoRecording)) {
					String videoFileNameMoon = videoURLMoonPrefix + remoteSessionID + "/video.mp4";
					String videoFileNameJenkins = videoURLJenkinsPrefix + remoteSessionID + ".mp4";
					Map<String, String> videoPaths = new LinkedHashMap<>();
					videoPaths.put("Moon", videoFileNameMoon);
					videoPaths.put("Jenkins", videoFileNameJenkins);
					MultiReporter.addVideoRecording(videoPaths);
				}


				remoteDriver.setFileDetector(new LocalFileDetector());

				// selenoid and moon1 don't support Selenium4 CDP
				if (hub.toString().contains("moon2")) {
					setChromeDevToolsConfiguration(remoteDriver, remoteSessionID);
				} else {
					DevToolsLegacy.setChromeDevToolsConfiguration(remoteSessionID, testMethod);
				}
				driver = remoteDriver;

				if ((StringUtils.isEmpty(deviceToEmulate)) || (deviceToEmulate.equalsIgnoreCase("desktop"))) {
					driver.manage().window().maximize();
				} else {
					setBrowserDimensionsToMatchEmulatedDevice(driver);
				}

				break;

			case "chrome-browserstack":
				String username = Users.getProperty(UsersProperty.automateUsername);
				String token = Users.getProperty(UsersProperty.automateKey);
				String browserStackHubUrl = String.format(MainConfig.getProperty(MainConfigProperty.browserStack_hubUrl), username, token);
				hub = new URL(browserStackHubUrl);
				ChromeOptions browserstackChromeOptions = chromeOptionsInit(testMethod, walletExtension, true);
				browserstackChromeOptions.setCapability("browserName", MainConfig.getProperty(MainConfigProperty.browserstack_browserName));
				browserstackChromeOptions.setCapability("device", MainConfig.getProperty(MainConfigProperty.browserstack_device));
				browserstackChromeOptions.setCapability("realMobile", MainConfig.getProperty(MainConfigProperty.browserstack_realMobile));
				browserstackChromeOptions.setCapability("os_version", MainConfig.getProperty(MainConfigProperty.browserstack_os_version));
				browserstackChromeOptions.setCapability("name", methodFullName);

				remoteDriver = new RemoteWebDriver(hub, browserstackChromeOptions);
				remoteSessionId.set(remoteDriver.getSessionId().toString());

				if (MainConfig.getBooleanProperty(MainConfigProperty.videoRecording)) {
					String videoFileNameMoon = videoURLMoonPrefix + remoteDriver.getSessionId() + "/video.mp4";
					String videoFileNameJenkins = videoURLJenkinsPrefix + remoteDriver.getSessionId() + ".mp4";
					Map<String, String> videoPaths = new LinkedHashMap<>();
					videoPaths.put("Moon", videoFileNameMoon);
					videoPaths.put("Jenkins", videoFileNameJenkins);
					MultiReporter.addVideoRecording(videoPaths);
				}

				remoteDriver.setFileDetector(new LocalFileDetector());
				driver = remoteDriver;

				break;

			case "ios-local":
				info("starting app on device...");
				String localHubUrl = "http://127.0.0.1:4723";
				XCUITestOptions localOptions = new XCUITestOptions()
						.autoDismissAlerts()
						.setBundleId("dev.io.securitize.securitize-app")
						.setUdid(System.getenv("deviceUDID"));

				driver = new IOSDriver(new URL(localHubUrl), localOptions);
				info("app started!");
				break;

			case "ios-browserstack":
				info("starting app on device...");
				XCUITestOptions options = new XCUITestOptions()
						.autoDismissAlerts()
						.setApp(Users.getProperty(UsersProperty.ios_app_id))
						.setPlatformVersion(MainConfig.getProperty(MainConfigProperty.browserstack_ios_platform_version))
						.setDeviceName(MainConfig.getProperty(MainConfigProperty.browserstack_ios_device_name));

				String browserstack_username = Users.getProperty(UsersProperty.automateUsername);
				String browserstack_token = Users.getProperty(UsersProperty.automateKey);
				String browserstackHubUrl = String.format(MainConfig.getProperty(MainConfigProperty.browserStack_hubUrl), browserstack_username, browserstack_token);
				hub = new URL(browserstackHubUrl);
				IOSDriver iosDriver;
				try {
					iosDriver = new IOSDriver(hub, options);
				} catch (SessionNotCreatedException e) {
					// if failure is due to Browserstack deleting our app: re-upload and try again
					if (ExceptionUtils.getStackTrace(e).contains("BROWSERSTACK_APP_DELETED")) {
						debug("Browserstack deleted our app.. Uploading it once again...");
						Browserstack.uploadAppToBrowserstack();
						options.setApp(Users.getProperty(UsersProperty.ios_app_id));
						debug("Trying to open the app once more...");
						iosDriver = new IOSDriver(hub, options);
					} else {
						throw e;
					}
				}

				info("app started!");
				// grab video url
				info("session id: " + iosDriver.getSessionId());
				Object response = ((JavascriptExecutor)iosDriver).executeScript("browserstack_executor: {\"action\": \"getSessionDetails\"}");
				JSONObject sessionDetails = new JSONObject(response.toString());
				String videoUrl = sessionDetails.getString("video_url");
				Map<String, String> videoPaths = new LinkedHashMap<>();
				videoPaths.put("BrowserStack", videoUrl);
				MultiReporter.addVideoRecording(videoPaths);
				driver = iosDriver;

				break;
			default:
				throw new RuntimeException("Requested browser type not yet supported: " + MainConfig.getProperty(MainConfigProperty.browserType));
		}

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(MainConfig.getIntProperty(MainConfigProperty.webDriverImplicitWaitInSeconds)));

		logBrowserDetails(driver);
		if (browserType.contains("chrome")) {
			DevToolsLegacy.setChromeExtensionParameters(driver);
		}

		// wrap the driver, so we can color effected elements (for video purposes)
		WebDriverListener webDriverListener = new WebEventListener(driver);
		WebDriver decoratedDriver = new EventFiringDecorator<>(webDriverListener).decorate(driver);

		//added to verify existing on the right page for initializing snack-bar
		if (browserType.contains("chrome")) {
			decoratedDriver.navigate().to("about:blank");
		}

		originalDriver.set(driver);
		return decoratedDriver;
	}


	public static WebDriver getOriginalDriver() {
		return originalDriver.get();
	}

	private static void validateOnlyOneTabOpened(WebDriver driver, WalletExtension walletExtension) {
		// if we are adding metamask, we need to wait for it to open a secondary tab and only
		// then scan for a secondary tab and closing it - otherwise we will have tab focus issues
		if (walletExtension == WalletExtension.METAMASK || walletExtension == WalletExtension.COINBASE) {
			new Browser(driver).waitForNumbersOfTabsToBe(2);
		}

		// if we have too many open tabs due to chrome extensions having too much fun..
		while (driver.getWindowHandles().size() > 1) {
			ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
			driver.switchTo().window(tabs.get(tabs.size() - 1));
			// handle coinBase cases where the tab with coinbase content is actually tab #0
			if (!driver.getCurrentUrl().contains("chrome-extension")) {
				driver.switchTo().window(tabs.get(0));
			}
			info("Closing latest tab opened in: " + driver.getCurrentUrl());
			driver.close();

			info("Switching to new latest tab");
			tabs = new ArrayList<>(driver.getWindowHandles());
			driver.switchTo().window(tabs.get(tabs.size() - 1));
		}
	}

	static URL getHubUrl(Method testMethod) {
		String address;

		ForceBrowserHubUrl annotation = null;
		if (testMethod != null) {
			annotation = testMethod.getAnnotation(ForceBrowserHubUrl.class);
		}

		if (annotation == null) {
			// load address from configuration
			address = MainConfig.getProperty(MainConfigProperty.hubUrl);
		} else {
			String currentEnvironment = MainConfig.getProperty(MainConfigProperty.environment);
			if (annotation.forceOnEnvironments().length == 0 || Arrays.stream(annotation.forceOnEnvironments()).anyMatch(x -> x.equalsIgnoreCase(currentEnvironment))) {
				address = annotation.url();
				info("Overriding hub url address with value from test annotation: " + address);
			} else {
				address = MainConfig.getProperty(MainConfigProperty.hubUrl);
			}
		}

		try {
			return new URL(address);
		} catch (MalformedURLException e) {
			throw new RuntimeException("Invalid url format: " + address + ". Details: " + e);
		}
	}


	private static ChromeOptions chromeOptionsInit(Method testMethod, WalletExtension walletExtension, boolean isRemote) throws IOException {

		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments("--start-maximized");
		chromeOptions.addArguments("--no-proxy-server");
		chromeOptions.addArguments("--ignore-certificate-errors");

		// added to allow reading downloaded file within the browser (allows navigation to pages such as chrome://downloads)
		chromeOptions.addArguments("-–allow-file-access-from-files");

		// to handle chrome crashes with "session deleted because of page crash"
		chromeOptions.addArguments("--disable-dev-shm-usage");
		chromeOptions.addArguments("--disable-gpu");
		chromeOptions.addArguments("--no-sandbox");

		// to handle Chrome 111 issues of connecting to CDP - https://github.com/SeleniumHQ/selenium/issues/11750
		chromeOptions.addArguments("--remote-allow-origins=*");

		// to bypass 'sign-in-with-google' recaptcha if running on local Jenkins
		ForceBrowserHubUrl annotation = testMethod.getAnnotation(ForceBrowserHubUrl.class);
		if (annotation != null) {
			chromeOptions.addArguments("--user-agent=" + MainConfig.getProperty(MainConfigProperty.chromeUserAgent));
		}

		// to create fake webcam
		if (MainConfig.getBooleanProperty(MainConfigProperty.simulateWebCam)) {
			String fakeVideoFilePath = MainConfig.getProperty(MainConfigProperty.simulateWebCamVideoFile);
			if (isRemote) {
				if (isRemoteMoon2(testMethod)) {
					fakeVideoFilePath = "/home/user/" + fakeVideoFilePath;
					shouldLoadBrowserContext.set(true);
				} else {
					fakeVideoFilePath = "/tmp/mountData/" + fakeVideoFilePath;
				}
			} else {
				fakeVideoFilePath = ResourcesUtils.getResourcePathByName("images" + "/" + fakeVideoFilePath);
			}
			chromeOptions.addArguments("--use-fake-ui-for-media-stream=1", "--use-fake-device-for-media-stream", "--use-file-for-fake-video-capture=" + fakeVideoFilePath);
		}

		// to bypass recaptcha
		chromeOptions.addArguments("--disable-blink-features=AutomationControlled");
		chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
		chromeOptions.setExperimentalOption("useAutomationExtension", false);

		HashMap<String, Object> prefs = new HashMap<>();
		prefs.put("credentials_enable_service", false);
		prefs.put("profile.password_manager_enabled", false);
		prefs.put("autofill.profile_enabled", false); // disable suggestion to store addresses popup (autofill)
		chromeOptions.setExperimentalOption("prefs", prefs);


		List<String> chromeExtensionsToLoad = new ArrayList<>();

		// load our Chrome extension to handle all basic HTTP authentication needs (Selenium4 solution collides with file upload)
		String chromeExtensionPath = ResourcesUtils.getResourcePathByName("chrome-extensions/chrome-extension" + File.separator + "chrome-extension.crx");
		String chromeExtensionDecodedPath = java.net.URLDecoder.decode(chromeExtensionPath, StandardCharsets.UTF_8.name());
		chromeOptions.addExtensions(new File(chromeExtensionDecodedPath));


		if (walletExtension != WalletExtension.NONE) {
			if (isRemoteMoon2(testMethod)) {
				chromeExtensionsToLoad.add("/home/user/" + walletExtension + "Extension");
				shouldLoadBrowserContext.set(true);
			} else {
				String extensionPath = ResourcesUtils.getResourcePathByName("chrome-extensions" + File.separator + walletExtension.toString() + File.separator + walletExtension + ".crx");
				String extensionDecodedPath = java.net.URLDecoder.decode(extensionPath, StandardCharsets.UTF_8.name());
				chromeOptions.addExtensions(new File(extensionDecodedPath));
			}
		}

		// check if we need the bypass recaptcha extension
		BypassRecaptcha bypassAnnotation = testMethod.getAnnotation(BypassRecaptcha.class);
		String currentEnvironment = MainConfig.getProperty(MainConfigProperty.environment);
		if (bypassAnnotation != null) {
			Stream<String> bypassOnEnvironments = Arrays.stream(bypassAnnotation.environments());
			if (bypassOnEnvironments.anyMatch(x -> x.equalsIgnoreCase("all") || x.equalsIgnoreCase(currentEnvironment))) {
				if (isRemoteMoon2(testMethod)) {
					chromeExtensionsToLoad.add("/home/user/busterExtension");
					shouldLoadBrowserContext.set(true);
				} else {
					String extensionPath = ResourcesUtils.getResourcePathByName("chrome-extensions/buster/buster.crx");
					String extensionDecodedPath = java.net.URLDecoder.decode(extensionPath, StandardCharsets.UTF_8.name());
					chromeOptions.addExtensions(new File(extensionDecodedPath));
				}
			}
		}

		if (!chromeExtensionsToLoad.isEmpty()) {
			var extensionsAsString = String.join(",", chromeExtensionsToLoad);
			chromeOptions.addArguments("load-extension=" + extensionsAsString);
		}

		return chromeOptions;
	}


	public static boolean isRemoteMoon2() {
		return isRemoteMoon2(null);
	}
	public static boolean isRemoteMoon2(Method testMethod) {
		String browserType = MainConfig.getProperty(MainConfigProperty.browserType).toLowerCase();
		if (!browserType.toLowerCase().contains("remote")) {
			return false;
		}

		String hubHost = getHubUrl(testMethod).getHost();
		return hubHost.toLowerCase().contains("moon2");
	}

	private static void logBrowserDetails(WebDriver driver) {
		if (driver instanceof RemoteWebDriver) {
			Capabilities cap = ((RemoteWebDriver) driver).getCapabilities();
			String browserName = cap.getBrowserName().toLowerCase();
			String browserVersion = cap.getBrowserVersion();

			info("browser name:" + browserName);
			info("browser Version:" + browserVersion);
		}
	}


	public static String getNetworkErrors(String sessionId) {
		if (sessionId == null) {
			return "";
		}

		var tempString = String.join(System.lineSeparator() + "<br/>", networkErrors.get(sessionId));
		return tempString.replace(System.lineSeparator(), System.lineSeparator() + "<br/>");
	}

	public static String getRemoteSessionId() {
		return remoteSessionId.get();
	}

	private static void setBrowserDimensionsToMatchEmulatedDevice(WebDriver driver) {
		try {
			String path = ResourcesUtils.getResourcePathByName("config" + File.separator + "emulatedDevices.json");
			var rawFile = Files.readString(new File(path));
			var fileAsJson = new JSONObject(rawFile);
			var deviceDimensions = fileAsJson.getJSONObject(MainConfig.getProperty(MainConfigProperty.browserDeviceNameToEmulate));
			var width = deviceDimensions.getInt("width");
			var height = deviceDimensions.getInt("height");
			driver.manage().window().setSize(new Dimension(width, height + 250)); // add some extra height for convenience
		} catch (Exception e) {
			errorAndStop("An error occur trying to get emulated device dimensions from file. Details: " + e, false);
		}
	}


	private static void setChromeDevToolsConfiguration(WebDriver driver, String sessionsId) {
		// register go get network errors
		if (!(driver instanceof HasDevTools)) {
			driver = new Augmenter().augment(driver);
		}

		var devTools = ((HasDevTools)driver).getDevTools(); // Create devTool instance
		devTools.createSession();
		devTools.send(new Command<>("Network.enable", Map.of()));

		// log network errors in our HTML report
		registerNetworkErrorsLogging(devTools, sessionsId);

		// removed and reverted to handling the basic HTTP authentication using the Chrome extension due to bug
		// that when using this technique, file uploads don't work correctly
		// automatically handle basic http authentication
//		if (driver instanceof HasAuthentication) {
//			registerHttpBasicAuthentication((HasAuthentication)driver);
//		}
	}


	private static void registerNetworkErrorsLogging(DevTools devTools, String sessionId) {
		networkErrors.put(sessionId, new ArrayList<>());

		devTools.addListener(org.openqa.selenium.devtools.v119.network.Network.responseReceived(), event -> {
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
				Optional<Headers> rawRequestHeaders = event.getResponse().getRequestHeaders();
				if (rawRequestHeaders.isEmpty()) {
					requestHeaders = null;
				} else {
					requestHeaders = rawRequestHeaders.get().keySet().stream()
							.map(key -> key + "=" + rawRequestHeaders.get().get(key))
							.collect(Collectors.joining(System.lineSeparator(), "", ""));
				}
			} catch (Exception e) {
				requestHeaders = "Can't extract request headers. Details: " + e;
			}

			// minimal documentation for 4xx responses
			if (statusCode.startsWith("4")) {
				networkErrors.get(sessionId).add("url: " + requestUrl + " status: " + statusCode);
			} else if (statusCode.startsWith("5")) { // extended documentation for 5xx responses
				networkErrors.get(sessionId).add("url: " + requestUrl + " status: " + statusCode + " Request headers: " + requestHeaders);
			}
		});
	}


//	// handle basic authentication in the securitize.io domain
//	private static void registerHttpBasicAuthentication(HasAuthentication hasAuthentication) {
//		hasAuthentication.register(
//				uri -> uri.getHost().contains("securitize.io"),
//				UsernameAndPassword.of(
//						Users.getProperty(UsersProperty.httpAuthenticationUsername),
//						Users.getProperty(UsersProperty.httpAuthenticationPassword)));
//	}
}
