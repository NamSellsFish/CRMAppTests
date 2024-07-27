package org.nam.common.managers;


import lombok.extern.slf4j.Slf4j;
import org.nam.common.base.Constants;
import org.nam.common.enums.BrowserFactory;
import org.nam.common.enums.DeployTarget;
import org.nam.common.exceptions.TargetNotValidException;
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.net.URL;

@Slf4j
public class DriverManager {

    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private DriverManager() {
        super();
    }

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static void setDriver(WebDriver driver) {
        DriverManager.driver.set(driver);
    }

    public static void quit() {
        if (DriverManager.getDriver() != null) {
            DriverManager.getDriver().quit();
        }
    }

    public static WebDriver initDriver(String browserName, String target) {
        DeployTarget dtarget = DeployTarget.valueOf(target.toUpperCase());
        WebDriver webdriver;

        switch (dtarget) {
            case LOCAL:
                //Create new driver from Enum setup in BrowserFactory class
                webdriver = BrowserFactory.valueOf(browserName.toUpperCase()).createDriver();
                break;
            case REMOTE:
                //Create new driver on Cloud (Selenium Grid, Docker) from method below
                webdriver = createRemoteInstance(BrowserFactory.valueOf(browserName.toUpperCase()).getOptions());
                break;
            default:
                throw new TargetNotValidException(target);
        }
        return webdriver;
    }

    private static RemoteWebDriver createRemoteInstance(MutableCapabilities capability) {
        RemoteWebDriver remoteWebDriver = null;
        try {
            String gridURL = String.format("http://%s:%s", Constants.REMOTE_URL, Constants.REMOTE_PORT);
            log.info("Remote URL: " + gridURL);
            remoteWebDriver = new RemoteWebDriver(new URL(gridURL), capability);
        } catch (java.net.MalformedURLException e) {
            log.error("Grid URL is invalid or Grid Port is not available");
            log.error(String.format("Browser: %s", capability.getBrowserName()), e);
        } catch (IllegalArgumentException e) {
            log.error(String.format("Browser %s is not valid or recognized", capability.getBrowserName()), e);
        }

        return remoteWebDriver;
    }

}
