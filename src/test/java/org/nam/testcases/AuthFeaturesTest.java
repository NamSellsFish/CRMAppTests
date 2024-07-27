package org.nam.testcases;

import org.nam.common.annotations.OtherTestInfo;
import org.nam.common.enums.AuthorType;
import org.nam.common.enums.CategoryType;
import org.nam.common.managers.DataProviderManager;
import org.nam.core.pages.DashboardPage;
import org.nam.core.pages.SignInPage;
import org.testng.annotations.Test;

import java.util.Hashtable;

import static org.nam.common.helpers.WebUI.verifyElementPresent;

public class AuthFeaturesTest extends BaseTest {

    SignInPage signInPage;
    DashboardPage dashboardPage;

    public AuthFeaturesTest() {
        signInPage = new SignInPage();
    }

    @Test(priority = 1, description = "TC01_signInWithDataProvider", dataProvider = "getSignInDataHashTable", dataProviderClass = DataProviderManager.class)
    @OtherTestInfo(author = {AuthorType.Nam, AuthorType.Hanksenberg}, category = {CategoryType.REGRESSION})
    public void signInWithDataProvider(Hashtable<String, String> data) {
        signInPage.signIn(data);
    }

    ;

    @Test(priority = 2, description = "TC02_signInWithAdminRole")
    public void signInWithAdminRole() {
        dashboardPage = signInPage.signInWithAdminRole();
        verifyElementPresent(dashboardPage.menuDashboard, 5, "The menu Dashboard does not exist.");
    }

    ;


}
