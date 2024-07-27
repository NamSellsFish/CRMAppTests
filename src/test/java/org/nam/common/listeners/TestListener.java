package org.nam.common.listeners;

import com.aventstack.extentreports.Status;
import lombok.extern.slf4j.Slf4j;
import org.nam.common.annotations.OtherTestInfo;
import org.nam.common.enums.AuthorType;
import org.nam.common.enums.CategoryType;
import org.nam.common.helpers.CaptureHelpers;
import org.nam.common.helpers.FileHelpers;
import org.nam.common.helpers.ScreenRecorderHelpers;
import org.nam.common.helpers.WebUI;
import org.nam.common.managers.DriverManager;
import org.nam.common.managers.ExtentReportManager;
import org.nam.common.utils.BrowserInfoUtils;
import org.nam.common.utils.SendEmailUtils;
import org.nam.common.utils.ZipUtils;
import org.testng.*;

import java.awt.*;
import java.io.IOException;

import static org.nam.common.base.Constants.*;

@Slf4j
public class TestListener implements ITestListener, ISuiteListener, IInvokedMethodListener {

    static int count_totalTCs;
    static int count_passedTCs;
    static int count_skippedTCs;
    static int count_failedTCs;

    private ScreenRecorderHelpers screenRecorder;

    public TestListener() {
        try {
            screenRecorder = new ScreenRecorderHelpers();
        } catch (IOException | AWTException e) {
            System.out.println(e.getMessage());
        }
    }

    public String getTestName(ITestResult result) {
        return result.getTestName() != null ? result.getTestName() : result.getMethod().getConstructorOrMethod().getName();
    }

    public String getTestDescription(ITestResult result) {
        return result.getMethod().getDescription() != null ? result.getMethod().getDescription() : getTestName(result);
    }

    @Override
    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
    }

    @Override
    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
    }

    @Override
    public void onStart(ISuite iSuite) {
        log.info("********** RUN STARTED **********");
        log.info("========= INSTALLING CONFIGURATION DATA =========");
        ExtentReportManager.initReports();
        log.info("========= INSTALLED CONFIGURATION DATA =========");
        log.info("=====> Starting Suite: " + iSuite.getName());
    }

    @Override
    public void onFinish(ISuite iSuite) {
        log.info("********** RUN FINISHED **********");
        log.info("=====> End Suite: " + iSuite.getName());
        //End Suite and execute Extents Report
        ExtentReportManager.flushReports();

        //Zip Folder report
        ZipUtils.zipReportFolder();
        //Send mail
        SendEmailUtils.sendEmail(count_totalTCs, count_passedTCs, count_failedTCs, count_skippedTCs);

        //FileHelpers.copyFile("src/test/resources/config/allure/environment.xml", "target/allure-results/environment.xml");
        FileHelpers.copyFile("src/test/resources/config/allure/categories.json", "target/allure-results/categories.json");
        FileHelpers.copyFile("src/test/resources/config/allure/executor.json", "target/allure-results/executor.json");

    }

    public AuthorType[] getAuthorType(ITestResult iTestResult) {
        if (iTestResult.getMethod().getConstructorOrMethod().getMethod().getAnnotation(OtherTestInfo.class) == null) {
            return null;
        }
        return iTestResult.getMethod().getConstructorOrMethod().getMethod().getAnnotation(OtherTestInfo.class).author();
    }

    public CategoryType[] getCategoryType(ITestResult iTestResult) {
        if (iTestResult.getMethod().getConstructorOrMethod().getMethod().getAnnotation(OtherTestInfo.class) == null) {
            return null;
        }
        return iTestResult.getMethod().getConstructorOrMethod().getMethod().getAnnotation(OtherTestInfo.class).category();
    }

    @Override
    public void onTestStart(ITestResult iTestResult) {
        log.info("Test case: " + getTestName(iTestResult) + " is starting...");
        count_totalTCs = count_totalTCs + 1;

        ExtentReportManager.createTest(iTestResult.getName());
        ExtentReportManager.addAuthors(getAuthorType(iTestResult));
        ExtentReportManager.addCategories(getCategoryType(iTestResult));
        ExtentReportManager.addDevices();
        ExtentReportManager.info(BrowserInfoUtils.getOSInfo());

        if (VIDEO_RECORD.toLowerCase().trim().equals(YES)) {
            screenRecorder.startRecording(getTestName(iTestResult));
        }

    }

    @Override
    public void onTestSuccess(ITestResult iTestResult) {
        log.info("Test case: " + getTestName(iTestResult) + " is passed.");
        count_passedTCs = count_passedTCs + 1;

        if (SCREENSHOT_PASSED_TCS.equals(YES)) {
            CaptureHelpers.captureScreenshot(DriverManager.getDriver(), getTestName(iTestResult));
            ExtentReportManager.addScreenShot(Status.PASS, getTestName(iTestResult));
        }

        ExtentReportManager.logMessage(Status.PASS, "Test case: " + getTestName(iTestResult) + " is passed.");

        if (VIDEO_RECORD.trim().toLowerCase().equals(YES)) {
            WebUI.sleep(2);
            screenRecorder.stopRecording(true);
        }
    }

    @Override
    public void onTestFailure(ITestResult iTestResult) {
        log.error("FAILED !! Test case " + getTestName(iTestResult) + " is failed.");
        log.error(String.valueOf(iTestResult.getThrowable()));

        count_failedTCs = count_failedTCs + 1;

        if (SCREENSHOT_FAILED_TCS.equals(YES)) {
            CaptureHelpers.captureScreenshot(DriverManager.getDriver(), getTestName(iTestResult));
            ExtentReportManager.addScreenShot(Status.FAIL, getTestName(iTestResult));
        }

        ExtentReportManager.logMessage(Status.FAIL, iTestResult.getThrowable().toString());

        if (VIDEO_RECORD.toLowerCase().trim().equals(YES)) {
            WebUI.sleep(2);
            screenRecorder.stopRecording(true);
        }
    }

    @Override
    public void onTestSkipped(ITestResult iTestResult) {
        log.warn("WARNING!! Test case: " + getTestName(iTestResult) + " is skipped.");
        count_skippedTCs = count_skippedTCs + 1;

        if (SCREENSHOT_SKIPPED_TCS.equals(YES)) {
            CaptureHelpers.captureScreenshot(DriverManager.getDriver(), getTestName(iTestResult));
        }

        ExtentReportManager.logMessage(Status.SKIP, "Test case: " + getTestName(iTestResult) + " is skipped.");

        if (VIDEO_RECORD.toLowerCase().trim().equals(YES)) {
            screenRecorder.stopRecording(true);
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult iTestResult) {
        ExtentReportManager.logMessage("Test failed but it is in defined success ratio: " + getTestName(iTestResult));
    }

}
