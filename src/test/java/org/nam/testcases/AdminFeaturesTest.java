package org.nam.testcases;

import org.nam.common.managers.DataProviderManager;
import org.nam.core.pages.DashboardPage;
import org.nam.core.pages.InvoicePage;
import org.nam.core.pages.SignInPage;
import org.testng.annotations.Test;

import java.util.Hashtable;

public class AdminFeaturesTest extends BaseTest {

    SignInPage signInPage;
    DashboardPage dashboardPage;
    InvoicePage invoicePage;


    public AdminFeaturesTest() {
        signInPage = new SignInPage();
    }

    @Test(priority = 1, description = "TC03_testAddInvoice", dataProvider = "getInvoiceDataHashTable", dataProviderClass = DataProviderManager.class)
    public void testAddClient(Hashtable<String, String> data) {
        dashboardPage = signInPage.signInWithAdminRole();
        invoicePage = dashboardPage.openInvoicePage();
        invoicePage.openYearlyTabPage();
        invoicePage.addInvoice(data);
    }
}
