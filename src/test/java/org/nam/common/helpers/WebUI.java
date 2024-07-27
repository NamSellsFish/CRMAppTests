package org.nam.common.helpers;

import lombok.extern.slf4j.Slf4j;
import org.nam.common.base.Constants;
import org.nam.common.enums.FailureHandling;
import org.nam.common.managers.DriverManager;
import org.nam.common.utils.BrowserInfoUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.util.Objects;

@Slf4j
public class WebUI {

    /**
     * Smart Waits contains waitForPageLoaded and sleep functions
     */
    public static void smartWait() {
        if (Constants.ACTIVE_PAGE_LOADED.trim().toLowerCase().equals("true")) {
            waitForPageLoaded();
        }
        sleep(Constants.WAIT_SLEEP_STEP);
    }

    /**
     * Navigate to the specified URL.
     *
     * @param URL the specified url
     */
    public static void openWebsite(String URL) {
        sleep(Constants.WAIT_SLEEP_STEP);

        DriverManager.getDriver().get(URL);
        waitForPageLoaded();

        log.info("Open website with URL: " + URL);
    }

    /**
     * Forced wait with unit of Seconds
     *
     * @param second is a positive integer corresponding to the number of Seconds
     */
    public static void sleep(double second) {
        try {
            Thread.sleep((long) (second * 1000));
        } catch (InterruptedException e) {
            log.error("Error occurred while waiting: " + e.getMessage());
        }
    }

    /**
     * Wait for a page to load with the default time from the config
     */
    public static void waitForPageLoaded() {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(Constants.WAIT_PAGE_LOADED), Duration.ofMillis(500));
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();

        // wait for Javascript to loaded
        ExpectedCondition<Boolean> jsLoad = driver -> ((JavascriptExecutor) driver).executeScript("return document.readyState").toString().equals("complete");

        //Get JS is Ready
        boolean jsReady = js.executeScript("return document.readyState").toString().equals("complete");

        //Wait Javascript until it is Ready!
        if (!jsReady) {
            log.info("Javascript in NOT Ready!");
            //Wait for Javascript to load
            try {
                wait.until(jsLoad);
            } catch (Throwable error) {
                error.printStackTrace();
                Assert.fail("Timeout waiting for page load. (" + Constants.WAIT_PAGE_LOADED + "s)");
            }
        }
    }

    /**
     * Verify if the first object contains the second object.
     *
     * @param value1  The first object
     * @param value2  The second object
     * @param message The custom message if false
     * @return true/false
     */
    public static boolean verifyContains(String value1, String value2, String message) {
        boolean result = value1.contains(value2);
        if (result == true) {
            log.info("Verify Equals: " + value1 + " CONTAINS " + value2);
        } else {
            log.info("Verify Contains: " + value1 + " NOT CONTAINS " + value2);
            Assert.assertEquals(value1, value2, message);
        }
        return result;
    }


    /**
     * Get current URL from current driver
     *
     * @return the current URL as String
     */
    public static String getCurrentUrl() {
        smartWait();
        log.info("Get Current URL: " + DriverManager.getDriver().getCurrentUrl());
        return DriverManager.getDriver().getCurrentUrl();
    }

    /**
     * Verify if two object are equal.
     *
     * @param value1  The object one
     * @param value2  The object two
     * @param message The custom message if false
     * @return true/false
     */
    public static boolean verifyEquals(Object value1, Object value2, String message) {
        boolean result = value1.equals(value2);
        if (result == true) {
            log.info("Verify Equals: " + value1 + " = " + value2);
        } else {
            log.info("Verify Equals: " + value1 + " != " + value2);
            Assert.assertEquals(value1, value2, message);
        }
        return result;
    }

    /**
     * Get current web page's title
     *
     * @return the current URL as String
     */
    public static String getPageTitle() {
        smartWait();
        String title = DriverManager.getDriver().getTitle();
        log.info("Get Page Title: " + DriverManager.getDriver().getTitle());
        return title;
    }


    /**
     * Clear all text of the element.
     *
     * @param by an element of object type By
     */
    public static void clearText(By by) {
        waitForElementVisible(by).clear();
        log.info("Clear text in textbox " + by.toString());
    }

    /**
     * Wait until the given web element is visible.
     *
     * @param by an element of object type By
     * @return a WebElement object ready to be visible
     */
    public static WebElement waitForElementVisible(By by) {
        smartWait();
        waitForElementPresent(by);

        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(Constants.WAIT_EXPLICIT), Duration.ofMillis(500));
            boolean check = isElementVisible(by, 1);
            if (check == true) {
                return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            } else {
                scrollToElementAtBottom(by);
                return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            }
        } catch (Throwable error) {
            log.error("Timeout waiting for the element Visible. " + by.toString());
            Assert.fail("Timeout waiting for the element Visible. " + by.toString());
        }
        return null;
    }

    /**
     * Wait for the given element to present
     *
     * @param by an element of object type By
     * @return an existing WebElement object
     */
    public static WebElement waitForElementPresent(By by) {
        smartWait();

        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(Constants.WAIT_EXPLICIT), Duration.ofMillis(500));
            return wait.until(ExpectedConditions.presenceOfElementLocated(by));
        } catch (Throwable error) {
            log.error("Element not exist. " + by.toString());
            Assert.fail("Element not exist. " + by.toString());
        }
        return null;
    }

    /**
     * Verify element is visible. (in seconds)
     *
     * @param by      Represent a web element as the By object
     * @param timeout System will wait at most timeout (seconds) to return result
     * @return true/false
     */
    public static boolean isElementVisible(By by, int timeout) {
        smartWait();

        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            log.info("Verify element visible " + by);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Scroll an element into the visible area of the browser window. (at BOTTOM)
     *
     * @param by Represent a web element as the By object
     */
    public static void scrollToElementAtBottom(By by) {
        smartWait();

        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();
        js.executeScript("arguments[0].scrollIntoView(false);", getWebElement(by));
        log.info("Scroll to element " + by);
    }

    /**
     * Convert the By object to the WebElement
     *
     * @param by is an element of type By
     * @return Returns a WebElement object
     */
    public static WebElement getWebElement(By by) {
        return DriverManager.getDriver().findElement(by);
    }

    /**
     * Set the value of an input field
     *
     * @param by    an element of object type By
     * @param value the value to fill in the text box
     */
    public static void setText(By by, String value) {
        waitForElementVisible(by).sendKeys(value);
        log.info("Set text " + value + " on " + by.toString());
    }

    /**
     * Set the value of an input field and press the key on the keyboard
     *
     * @param by    an element of object type By
     * @param value the value to fill in the text box
     * @param keys  key on the keyboard to press
     */
    public static void setText(By by, String value, Keys keys) {
        Objects.requireNonNull(waitForElementVisible(by)).sendKeys(value, keys);
        log.info("Set text {} on {} and press key {}", value, by, keys.name());
    }

    /**
     * Click on the specified element.
     *
     * @param by an element of object type By
     */
    public static void clickElement(By by) {
        Objects.requireNonNull(waitForElementClickable(by)).click();
        log.info("Clicked on the element " + by.toString());
    }

    /**
     * Wait for the given element to be clickable.
     *
     * @param by an element of object type By
     * @return a WebElement object ready to CLICK
     */
    public static WebElement waitForElementClickable(By by) {
        smartWait();

        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(Constants.WAIT_EXPLICIT), Duration.ofMillis(500));
            return wait.until(ExpectedConditions.elementToBeClickable(getWebElement(by)));
        } catch (Throwable error) {
            Assert.fail("Timeout waiting for the element ready to click. " + by.toString());
            log.error("Timeout waiting for the element ready to click. " + by.toString());
        }
        return null;
    }


    /**
     * Wait for JQuery to finish loading with default time from config
     */
    public static void waitForJQueryLoad() {
        WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(Constants.WAIT_PAGE_LOADED), Duration.ofMillis(500));
        JavascriptExecutor js = (JavascriptExecutor) DriverManager.getDriver();

        //Wait for jQuery to load
        ExpectedCondition<Boolean> jQueryLoad = driver -> {
            assert driver != null;
            return ((Long) ((JavascriptExecutor) driver).executeScript("return jQuery.active") == 0);
        };

        //Get JQuery is Ready
        boolean jqueryReady = (Boolean) js.executeScript("return jQuery.active==0");

        //Wait JQuery until it is Ready!
        if (!jqueryReady) {
            log.info("JQuery is NOT Ready!");
            try {
                //Wait for jQuery to load
                wait.until(jQueryLoad);
            } catch (Throwable error) {
                Assert.fail("Timeout waiting for JQuery load. (" + Constants.WAIT_PAGE_LOADED + "s)");
            }
        }
    }

    /**
     * Verify text of an element. (equals)
     *
     * @param by          Represent a web element as the By object
     * @param text        Text of the element to verify.
     * @param flowControl Specify failure handling schema (STOP_ON_FAILURE, CONTINUE_ON_FAILURE, OPTIONAL) to determine whether the execution should be allowed to continue or stop
     * @return true if the element has the desired text, otherwise false.
     */
    public static boolean verifyElementTextEquals(By by, String text, FailureHandling flowControl) {
        smartWait();

        waitForElementVisible(by);

        boolean result = getTextElement(by).trim().equals(text.trim());

        if (result == true) {
            log.info("Verify text of an element [Equals]: " + result);
        } else {
            log.warn("Verify text of an element [Equals]: " + result);
        }

        if (flowControl.equals(FailureHandling.STOP_ON_FAILURE)) {
            Assert.assertEquals(getTextElement(by).trim(), text.trim(), "The actual text is '" + getTextElement(by).trim() + "' not equals '" + text.trim() + "'");
        }
        if (flowControl.equals(FailureHandling.CONTINUE_ON_FAILURE)) {
            Assert.assertEquals(getTextElement(by).trim(), text.trim(), "The actual text is '" + getTextElement(by).trim() + "' not equals '" + text.trim() + "'");
        }

        return getTextElement(by).trim().equals(text.trim());
    }

    /**
     * Get text of an element
     *
     * @param by an element of object type By
     * @return text of a element
     */
    public static String getTextElement(By by) {
        smartWait();
        return waitForElementVisible(by).getText().trim();
    }

    /**
     * Select the options with the given label (displayed text).
     *
     * @param by   Represent a web element as the By object
     * @param text the specified text of option
     */
    public static void selectOptionByText(By by, String text) {
        smartWait();
        Select select = new Select(getWebElement(by));
        select.selectByVisibleText(text);
        log.info("Select Option " + by + "by text " + text);
    }

    /**
     * Uploading files with a click shows a form to select local files on your computer
     *
     * @param by       is an element of type By
     * @param filePath the absolute path to the file on your computer
     */
    public static void uploadFileWithLocalForm(By by, String filePath) {
        smartWait();
        log.info("Upload File: " + filePath);
        Actions action = new Actions(DriverManager.getDriver());
        //Click to open form upload
        action.moveToElement(getWebElement(by)).click().perform();
        sleep(3);

        // Create Robot class
        Robot robot = null;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        // Copy File path to Clipboard
        StringSelection str = new StringSelection(filePath);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(str, null);

        //Check OS for Windows
        if (BrowserInfoUtils.isWindows()) {
            // Press Control+V to paste the file path
            robot.keyPress(KeyEvent.VK_CONTROL);
            robot.keyPress(KeyEvent.VK_V);


            // Release the Control V
            robot.keyRelease(KeyEvent.VK_CONTROL);
            robot.keyRelease(KeyEvent.VK_V);
            robot.delay(2000);
            // Press Enter
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        }
        //Check OS for MAC
        if (BrowserInfoUtils.isMac()) {
            robot.keyPress(KeyEvent.VK_META);
            robot.keyPress(KeyEvent.VK_TAB);
            robot.keyRelease(KeyEvent.VK_META);
            robot.keyRelease(KeyEvent.VK_TAB);
            robot.delay(1000);

            //Open goto MAC
            robot.keyPress(KeyEvent.VK_META);
            robot.keyPress(KeyEvent.VK_SHIFT);
            robot.keyPress(KeyEvent.VK_G);
            robot.keyRelease(KeyEvent.VK_META);
            robot.keyRelease(KeyEvent.VK_SHIFT);
            robot.keyRelease(KeyEvent.VK_G);

            //Paste the clipboard value
            robot.keyPress(KeyEvent.VK_META);
            robot.keyPress(KeyEvent.VK_V);
            robot.keyRelease(KeyEvent.VK_META);
            robot.keyRelease(KeyEvent.VK_V);
            robot.delay(1000);

            //Press Enter key to close the Goto MAC and Upload on MAC
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
            robot.keyPress(KeyEvent.VK_ENTER);
            robot.keyRelease(KeyEvent.VK_ENTER);
        }

        log.info("Upload File with Local Form: " + filePath);

    }

    /**
     * Verify if the given web element does present on DOM. (in seconds)
     *
     * @param by      Represent a web element as the By object
     * @param timeout System will wait at most timeout (seconds) to return result
     * @param message the custom message if false
     * @return true/false
     */
    public static boolean verifyElementPresent(By by, int timeout, String message) {
        smartWait();

        try {
            WebDriverWait wait = new WebDriverWait(DriverManager.getDriver(), Duration.ofSeconds(timeout));
            wait.until(ExpectedConditions.presenceOfElementLocated(by));
            log.info("Verify element present " + by);
            return true;
        } catch (Exception e) {
            if (message.isEmpty() || message == null) {
                log.error("The element does NOT present. " + e.getMessage());
                Assert.fail("The element does NOT present. " + e.getMessage());
            } else {
                log.error(message);
                Assert.fail(message);
            }

            return false;
        }
    }

    /**
     * Verify text of an element. (contains)
     *
     * @param by          Represent a web element as the By object
     * @param text        Text of the element to verify.
     * @param flowControl Specify failure handling schema (STOP_ON_FAILURE, CONTINUE_ON_FAILURE, OPTIONAL) to determine whether the execution should be allowed to continue or stop
     * @return true if the element has the desired text, otherwise false.
     */
    public static boolean verifyElementTextContains(By by, String text, FailureHandling flowControl) {
        smartWait();
        waitForElementVisible(by);

        boolean result = getTextElement(by).trim().contains(text.trim());

        if (result == true) {
            log.info("Verify text of an element [Contains]: " + result);
        } else {
            log.warn("Verify text of an element [Contains]: " + result);
        }

        if (flowControl.equals(FailureHandling.STOP_ON_FAILURE)) {
            Assert.assertTrue(result, "The actual text is " + getTextElement(by).trim() + " not contains " + text.trim());
        }
        if (flowControl.equals(FailureHandling.CONTINUE_ON_FAILURE)) {
            Assert.assertTrue(result, "The actual text is " + getTextElement(by).trim() + " not contains " + text.trim());
        }

        return getTextElement(by).trim().contains(text.trim());
    }
}