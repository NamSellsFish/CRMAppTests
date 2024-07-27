package org.nam.common.helpers;


import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.monte.media.Format;
import org.monte.screenrecorder.ScreenRecorder;
import org.nam.common.base.Constants;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Slf4j
public class CaptureHelpers extends ScreenRecorder {

    private static ScreenRecorder screenRecorder;
    private static String name;
    private static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH-mm-ss");

    public CaptureHelpers(GraphicsConfiguration cfg, Rectangle captureArea, Format fileFormat, Format screenFormat, Format mouseFormat, Format audioFormat, File movieFolder, String name) throws IOException, AWTException {
        super(cfg, captureArea, fileFormat, screenFormat, mouseFormat, audioFormat, movieFolder);
        this.name = name;
    }

    public static void captureScreenshot(WebDriver driver, String screenName) {
        try {
            String path = FileHelpers.getCurrentDir() + Constants.EXPORT_CAPTURE_PATH;
            File file = new File(path);
            if (!file.exists()) {
                log.info("No Folder: " + path);
                file.mkdir();
                log.info("Folder created: " + file);
            }

            log.info("Driver for Screenshot: " + driver);
            // Create reference of TakesScreenshot
            TakesScreenshot ts = (TakesScreenshot) driver;
            // Call the capture screenshot function - getScreenshotAs
            File source = ts.getScreenshotAs(OutputType.FILE);
            // result.getName() gets the name of the test case and assigns it to the screenshot file name
            FileUtils.copyFile(source, new File(path + "/" + screenName + "_" + dateFormat.format(new Date()) + ".png"));
            log.info("Screenshot taken: " + screenName);
            log.info("Screenshot taken current URL: " + driver.getCurrentUrl());
        } catch (Exception e) {
            System.out.println("Exception while taking screenshot: " + e.getMessage());
        }
    }
}
