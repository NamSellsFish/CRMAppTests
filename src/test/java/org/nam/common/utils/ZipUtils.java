package org.nam.common.utils;

import lombok.extern.slf4j.Slf4j;
import org.nam.common.base.Constants;
import org.zeroturnaround.zip.ZipUtil;

import java.io.File;

@Slf4j
public class ZipUtils {

    private ZipUtils() {
        super();
    }

    /* Make Zip file of Extent Reports in Project Root folder */
    public static void zipReportFolder() {
        if (Constants.ZIP_FOLDER.toLowerCase().trim().equals(Constants.YES)) {
            if ((Constants.ZIP_FOLDER_PATH != null || !Constants.ZIP_FOLDER_PATH.isEmpty()) && (Constants.ZIP_FOLDER_NAME != null || !Constants.ZIP_FOLDER_NAME.isEmpty())) {
                ZipUtil.pack(new File(Constants.ZIP_FOLDER_PATH), new File(Constants.ZIP_FOLDER_NAME));
                log.info("Zipped " + Constants.ZIPPED_EXTENT_REPORTS_FOLDER + " successfully !!");
            } else {
                ZipUtil.pack(new File(Constants.EXTENT_REPORT_FOLDER_PATH), new File(Constants.ZIPPED_EXTENT_REPORTS_FOLDER));
                log.info("Zipped " + Constants.ZIPPED_EXTENT_REPORTS_FOLDER + " successfully !!");
            }
        }
    }
}
