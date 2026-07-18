package io.github.fernandouchoa.logitrack.pages;

import com.microsoft.playwright.Page;
import io.github.fernandouchoa.logitrack.utils.Routes;

public final class DashboardPage extends BasePage {

    public DashboardPage(Page page) {
        super(page);
    }

    public boolean isLoaded() {
        return !page.url().contains(Routes.LOGIN);
    }
}