package org.nam.core.pages;

import org.nam.common.enums.FailureHandling;
import org.nam.core.models.Invoice;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;

import java.nio.file.Paths;
import java.util.Hashtable;

import static org.nam.common.enums.FailureHandling.CONTINUE_ON_FAILURE;
import static org.nam.common.helpers.WebUI.*;

public class InvoicePage {

    public String pageText = "Invoices";

    By labelOnInvoicePage = By.xpath("//*[@id=\"invoices-tabs\"]/li[1]/h4");
    By tabYearly = By.xpath("//*[@id=\"invoices-tabs\"]/li[3]/a");
    By addInvoiceBtn = By.xpath("//*[@id=\"invoices-tabs\"]/div/div/a[3]");
    By billDateInput = By.id("invoice_bill_date");
    By dueDateInput = By.id("invoice_due_date");
    By selectClient = By.xpath("//div[@id='s2id_invoice_client_id']");
    By inputSearchClient = By.xpath("//*[@id=\"s2id_autogen25_search\"]");

    By selectTax = By.xpath("//div[@id='s2id_autogen18']");
    By inputSearchTax = By.xpath("//*[@id='s2id_autogen19_search']");
    By selectSecondTax = By.xpath("//div[@id='s2id_autogen20']");
    By inputSearchSecondTax = By.xpath("//*[@id='s2id_autogen21_search']");
    By selectTds = By.xpath("//div[@id='s2id_autogen22']");
    By inputSearchTds = By.xpath("//*[@id='s2id_autogen23_search']");
    By recurringCheckbox = By.xpath("//*[@id=\"invoice_recurring\"]");
    By repeatEveryLabel = By.xpath("//*[@id=\"recurring_fields\"]/div[1]/div/label");
    By repeatEveryInput = By.xpath("//*[@id=\"repeat_every\"]");
    By selectRecurringUnit = By.xpath("//div[@id='s2id_repeat_type']");
    By inputRecurringUnit = By.xpath("//*[@id='s2id_autogen17_search']");
    By recurringCycleInput = By.xpath("//*[@id='no_of_cycles']");
    By noteInput = By.xpath("//*[@id=\"invoice_note\"]");
    By uploadFileBtn = By.xpath("//button[contains(text(),'Upload File')]");
    By buttonSaveOnDialog = By.xpath("//*[@id=\"invoices-dropzone\"]/div[2]/button[2]");
    By invoiceDateTxt = By.xpath("//span[contains(text(),'Bill date:')]");
    By clientTxt = By.xpath("//*[@id=\"page-content\"]/div/div[1]/table/tbody/tr[3]/td[3]/strong");
    By fileTxt = By.xpath("//div[@class='mb10 strong']");
    By progressSuccessBar = By.cssSelector("div.progress-bar.progress-bar-success");

    public void openYearlyTabPage() {
        //If you want to continue running, select FailureHandling.CONTINUE_ON_FAILURE
        verifyElementTextEquals(labelOnInvoicePage, pageText, CONTINUE_ON_FAILURE);
        sleep(1);
        clickElement(tabYearly);
        waitForPageLoaded();
        waitForJQueryLoad();
    }

    public void addInvoice(Hashtable<String, String> data) {
        String testDataDirPath = "src/test/resources/test-data/photos";
        String fullPath = Paths.get(System.getProperty("user.dir")).resolve(testDataDirPath).toAbsolutePath().toString();
        clickElement(addInvoiceBtn);
        clearText(billDateInput);
        setText(billDateInput, data.get(Invoice.getBillDate()), Keys.ENTER);
        clearText(dueDateInput);
        setText(dueDateInput, data.get(Invoice.getDueDate()), Keys.ENTER);
        clickElement(selectClient);
        setText(inputSearchClient, data.get(Invoice.getClient()), Keys.ENTER);
        clickElement(selectTax);
        setText(inputSearchTax, data.get(Invoice.getTax()), Keys.ENTER);
        clickElement(selectSecondTax);
        setText(inputSearchSecondTax, data.get(Invoice.getSecondTax()), Keys.ENTER);
        clickElement(selectTds);
        setText(inputSearchTds, data.get(Invoice.getTds()), Keys.ENTER);
        if ("x".equals(data.get(Invoice.getRecurring()))) {
            clickElement(recurringCheckbox);
            waitForElementVisible(repeatEveryLabel);
            clearText(repeatEveryInput);
            setText(repeatEveryInput, data.get(Invoice.getRepeatEvery()));
            clickElement(selectRecurringUnit);
            setText(inputRecurringUnit, data.get(Invoice.getRecurringUnit()), Keys.ENTER);
            setText(recurringCycleInput, data.get(Invoice.getRecurringCycle()));
        }
        setText(noteInput, data.get(Invoice.getNote()));
        uploadFileWithLocalForm(uploadFileBtn, fullPath + data.get(Invoice.getAttachmentPath()));
        sleep(3);
        waitForElementVisible(progressSuccessBar);
        clickElement(buttonSaveOnDialog);
        waitForPageLoaded();
        checkInvoiceDetail(data);
    }

    public void checkInvoiceDetail(Hashtable<String, String> data) {
        verifyElementTextContains(invoiceDateTxt, "Bill date: " + data.get(Invoice.getBillDate()), FailureHandling.CONTINUE_ON_FAILURE);
        verifyElementTextContains(invoiceDateTxt, "Due date: " + data.get(Invoice.getDueDate()), FailureHandling.CONTINUE_ON_FAILURE);
        verifyElementTextEquals(clientTxt, data.get(Invoice.getClient()), FailureHandling.CONTINUE_ON_FAILURE);
        verifyElementTextEquals(fileTxt, "Files", FailureHandling.CONTINUE_ON_FAILURE);
    }

}
