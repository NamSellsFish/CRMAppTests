package org.nam.core.pages;

import org.nam.common.base.Constants;
import org.nam.common.helpers.ExcelHelpers;
import org.nam.core.models.SignIn;
import org.openqa.selenium.By;

import java.util.Hashtable;

import static org.nam.common.helpers.WebUI.*;

public class SignInPage {
    private String pageUrl = "/signin";
    private String pageTitle = "Sign in | RISE CRM | Anh Tester Demo";

    public By inputEmail = By.xpath("//input[@id='email']");
    public By inputPassword = By.xpath("//input[@id='password']");
    public By buttonSignIn = By.xpath("//*[@id='signin-form']/button");

    public SignInPage() {
    }

    public DashboardPage signIn(Hashtable<String, String> data) {
        openWebsite(Constants.URL_CRM);
        verifyContains(getCurrentUrl(), pageUrl, "The url of sign in page not match.");
        verifyEquals(getPageTitle(), pageTitle, "The title of sign in page not match.");
        clearText(inputEmail);
        clearText(inputPassword);
        setText(inputEmail, data.get(SignIn.getEmail()));
        setText(inputPassword, data.get(SignIn.getPassword()));
        clickElement(buttonSignIn);
        waitForPageLoaded();
        verifyContains(getCurrentUrl(), "/dashboard", "Sign in failed. Can not redirect to Dashboard page.");

        return new DashboardPage();
    }

    public DashboardPage signInWithAdminRole() {
        ExcelHelpers excelHelpers = new ExcelHelpers();
        excelHelpers.setExcelFile(Constants.SIGN_IN_EXCEL_DATA_FILE_PATH, "SignIn");
        openWebsite(Constants.URL_CRM);
        verifyContains(getCurrentUrl(), pageUrl, "The url of sign in page not match.");
        verifyEquals(getPageTitle(), pageTitle, "The title of sign in page not match.");
        clearText(inputEmail);
        clearText(inputPassword);
        setText(inputEmail, excelHelpers.getCellData(1, SignIn.getEmail()));
        setText(inputPassword, excelHelpers.getCellData(1, SignIn.getPassword()));
        clickElement(buttonSignIn);
        waitForPageLoaded();
        verifyContains(getCurrentUrl(), "/dashboard", "Sign in failed. Can not redirect to Dashboard page.");

        return new DashboardPage();

    }
}
