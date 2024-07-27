package org.nam.testcases;

import org.nam.common.base.Constants;
import org.nam.common.enums.DeployTarget;
import org.nam.common.managers.DriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ThreadGuard;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

public class BaseTest {

    @Parameters("BROWSER")
    @BeforeMethod
    public void createDriver(@Optional("edge") String browserName) {
        String deployTarget;
        try {
            deployTarget = Constants.DEPLOY_TARGET;
        } catch (IllegalArgumentException e) {
            deployTarget = DeployTarget.LOCAL.toString();
        }

        WebDriver driver = ThreadGuard.protect(DriverManager.initDriver(browserName, deployTarget));
        DriverManager.setDriver(driver);
        driver.manage().window().maximize();
    }

    @AfterMethod(alwaysRun = true)
    public void closeDriver() {
        DriverManager.quit();
    }

}