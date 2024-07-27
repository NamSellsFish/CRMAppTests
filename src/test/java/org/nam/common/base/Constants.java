package org.nam.common.base;


import org.nam.common.helpers.FileHelpers;
import org.nam.common.utils.ReportUtils;

import java.io.File;

public final class Constants {

    private Constants() {
    }

    public static final String PROJECT_PATH = FileHelpers.getCurrentDir();
    public static final String SIGN_IN_EXCEL_DATA_FILE_PATH = "src/test/resources/test-data/excel/ClientsDataExcel.xlsx";
    public static final String ADD_INVOICE_EXCEL_DATA_FILE_PATH = "src/test/resources/test-data/excel/GetInvoiceInfo.xlsx";
    public static final String CREDENTIALS_FILE_PATH = PROJECT_PATH + "src\\test\\resources\\config\\gmail\\credentials.json";
    public static final String TOKENS_DIRECTORY_PATH = PROJECT_PATH + "src\\test\\resources\\config\\gmail";
    public static final String EXTENT_REPORT_FOLDER = "reports/extent-reports";
    public static final String EXTENT_REPORT_FOLDER_PATH = PROJECT_PATH + EXTENT_REPORT_FOLDER;
    public static final String EXTENT_REPORT_NAME = "ExtentReport";
    public static final String EXTENT_REPORT_FILE_NAME = EXTENT_REPORT_NAME + ".html";
    public static String EXTENT_REPORT_FILE_PATH = EXTENT_REPORT_FOLDER_PATH + File.separator + EXTENT_REPORT_FILE_NAME;
    public static final String YES = "yes";
    public static final String NO = "no";
    public static final String OVERRIDE_REPORTS = YES;
    public static final String OPEN_REPORTS_AFTER_EXECUTION = YES;
    public static final String REPORT_TITLE = "Report | Automation Framework Selenium | Anh Tester CRM";
    public static final String AUTHOR = "Hanksenberg";
    public static final int WAIT_SLEEP_STEP = 0;
    public static final int WAIT_PAGE_LOADED = 40;
    public static final String URL_CRM = "https://rise.anhtester.com/signin";
    public static final String ACTIVE_PAGE_LOADED = "true";
    public static final int WAIT_EXPLICIT = 10;
    public static final String DEPLOY_TARGET = "LOCAL";
    public static final boolean HEADLESS = false;
    public static final String REMOTE_URL = "localhost";
    public static final String REMOTE_PORT = "4444";
    public static final String BROWSER = "chrome";
    public static final String VIDEO_RECORD = NO;
    public static final String SCREENSHOT_PASSED_TCS = NO;
    public static final String SCREENSHOT_FAILED_TCS = YES;
    public static final String SCREENSHOT_SKIPPED_TCS = YES;
    public static final String EXPORT_VIDEO_PATH = "reports/export-data/videos";
    public static final String EXPORT_CAPTURE_PATH = "reports/export-data/captures";
    public static final String ICON_OS_WINDOWS = "<i class='fa fa-windows' ></i>";
    public static final String ICON_OS_MAC = "<i class='fa fa-apple' ></i>";
    public static final String ICON_OS_LINUX = "<i class='fa fa-linux' ></i>";
    public static final String ICON_BROWSER_OPERA = "<i class=\"fa fa-opera\" aria-hidden=\"true\"></i>";
    public static final String ICON_BROWSER_EDGE = "<i class=\"fa fa-edge\" aria-hidden=\"true\"></i>";
    public static final String ICON_BROWSER_CHROME = "<i class=\"fa fa-chrome\" aria-hidden=\"true\"></i>";
    public static final String ICON_BROWSER_FIREFOX = "<i class=\"fa fa-firefox\" aria-hidden=\"true\"></i>";
    public static final String ICON_BROWSER_SAFARI = "<i class=\"fa fa-safari\" aria-hidden=\"true\"></i>";
    public static final String ZIP_FOLDER = YES;
    public static final String ZIP_FOLDER_PATH = "reports/extent-reports";
    public static final String ZIP_FOLDER_NAME = "ExtentReports.zip";
    public static final String ZIPPED_EXTENT_REPORTS_FOLDER = EXTENT_REPORT_FOLDER + ".zip";
    public static final String SEND_EMAIL_TO_USERS = YES;
    public static final String FROM = "21110785@student.hcmute.edu.vn";
    public static final String[] TO = {"nhatnam0192123@gmail.com"};
    public static final String SUBJECT = REPORT_TITLE;

    public static String getExtentReportFilePath() {
        if (EXTENT_REPORT_FILE_PATH.isEmpty()) {
            EXTENT_REPORT_FILE_PATH = ReportUtils.createExtentReportPath();
        }
        return EXTENT_REPORT_FILE_PATH;
    }
}