package io.github.fernandouchoa.logitrack.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.LoadState;
import io.github.fernandouchoa.logitrack.config.ConfigManager;

public abstract class BasePage {

    protected final Page page;

    protected BasePage(Page page) {
        this.page = page;
    }

    protected void navigate(String route) {
        page.navigate(ConfigManager.getBaseUrl() + route);
        page.waitForLoadState(LoadState.DOMCONTENTLOADED);
    }

    protected void fill(Locator locator, String value) {
        locator.fill(value == null ? "" : value);
    }

    protected void click(Locator locator) {
        locator.click();
    }

    public String currentUrl() {
        return page.url();
    }
}