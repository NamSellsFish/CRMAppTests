package org.nam.core.pages;

import org.openqa.selenium.By;

import java.util.Objects;

import static org.nam.common.helpers.WebUI.clickElement;
import static org.nam.common.helpers.WebUI.waitForElementVisible;

public class DashboardPage {
    public String pageText = "Dashboard";
    public String pageUrl = "/dashboard";

    public By menuSales = By.xpath("//*[@id=\"sidebar-menu\"]/li[8]/a/span");
    public By menuInvoices = By.xpath("//span[normalize-space()='Invoices']");
    public By menuDashboard = By.xpath("//span[normalize-space()='Dashboard']");

    public DashboardPage() {
        super();
    }

    public InvoicePage openInvoicePage() {
        clickElement(menuSales);
        Objects.requireNonNull(waitForElementVisible(menuInvoices)).click();
        return new InvoicePage();
    }


}
