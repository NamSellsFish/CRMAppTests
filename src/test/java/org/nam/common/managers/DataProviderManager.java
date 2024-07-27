package org.nam.common.managers;

import lombok.extern.slf4j.Slf4j;
import org.nam.common.base.Constants;
import org.nam.common.helpers.ExcelHelpers;
import org.nam.common.helpers.FileHelpers;
import org.testng.annotations.DataProvider;

import java.util.Arrays;

@Slf4j
public final class DataProviderManager {
    private DataProviderManager() {
        super();
    }

    @DataProvider(name = "getSignInDataHashTable", parallel = true)
    public static Object[][] getSignInData() {
        ExcelHelpers excelHelpers = new ExcelHelpers();
        Object[][] data = excelHelpers.getDataHashTable(FileHelpers.getCurrentDir() + Constants.SIGN_IN_EXCEL_DATA_FILE_PATH, "SignIn", 1, 4);
        log.info("getSignInData: " + Arrays.deepToString(data));
        return data;
    }

    @DataProvider(name = "getInvoiceDataHashTable", parallel = true)
    public static Object[][] getTaskData() {
        ExcelHelpers excelHelpers = new ExcelHelpers();
        Object[][] data = excelHelpers.getDataHashTable(FileHelpers.getCurrentDir() + Constants.ADD_INVOICE_EXCEL_DATA_FILE_PATH, "InvoiceInfo", 1, 4);
        log.info("getInvoiceData: {}", Arrays.deepToString(data));
        return data;
    }

}
