chrome.webRequest.onAuthRequired.addListener(
  function handler(details) {

    var httpAuthenticationFeature = localStorage["httpAuthenticationFeature"];

    if (typeof httpAuthenticationFeature == 'undefined' || httpAuthenticationFeature !== 'true') {
        console.log("httpAuthenticationFeature not set to true. Doing nothing to request: " + JSON.stringify(details));
        return {cancel: true};
    }

  	console.log("auth required for: " + JSON.stringify(details));

   	if (localStorage["user_name"] === undefined || localStorage["password"] === undefined) {
   		console.warn("username or password not set for http authentication");
   		return {cancel: true};
   	} else {
    	console.log("Going to login using username=" + localStorage["user_name"]);
    	return {authCredentials: {username: localStorage["user_name"], password: localStorage["password"]}};
   	}
  },
  {urls: ["<all_urls>"]},
  ['blocking']
);


chrome.webRequest.onBeforeSendHeaders.addListener(
  function(details) {

    var httpCustomHeaderFeature = localStorage["httpCustomHeaderFeature"];

    if (typeof httpCustomHeaderFeature == 'undefined' || httpCustomHeaderFeature !== 'true') {
        console.log("httpCustomHeaderFeature not set to true. Doing nothing to request: " + JSON.stringify(details));
        return { requestHeaders: details.requestHeaders };
    }

    console.log("Analyzing request: " + JSON.stringify(details));

    var rawUrl = details.url;
    var parsedUrl = new URL(rawUrl);
    var requestHostName = parsedUrl.hostname;

    var domainToAddHttpHeader = localStorage["domainToAddHttpHeader"]
    var httpHeaderName = localStorage["httpHeaderName"]
    var httpHeaderValue = localStorage["httpHeaderValue"]

    if (requestHostName.endsWith(domainToAddHttpHeader)) {
        console.log("Request is for the tracked domain of " + domainToAddHttpHeader + ", adding HTTP header");
        details.requestHeaders.push({name: httpHeaderName, value: httpHeaderValue});
    } else {
        console.log("Request is for non tracked domain of " + requestHostName + " NOT adding HTTP header");
    }

    return { requestHeaders: details.requestHeaders };
  },
  {urls: ['<all_urls>']},
  ['blocking', 'requestHeaders', 'extraHeaders']
);
