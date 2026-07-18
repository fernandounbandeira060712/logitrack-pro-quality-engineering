package io.github.fernandouchoa.logitrack.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.github.fernandouchoa.logitrack.components.HeaderComponent;
import io.github.fernandouchoa.logitrack.components.SidebarComponent;
import io.github.fernandouchoa.logitrack.utils.Routes;

public final class DashboardPage extends BasePage {

    private final SidebarComponent sidebar;
    private final HeaderComponent header;

    public DashboardPage(Page page) {
        super(page);
        sidebar = new SidebarComponent(page);
        header = new HeaderComponent(page);
    }

    public DashboardPage open() {
        navigate(Routes.DASHBOARD);
        return this;
    }

    public boolean isLoaded() {
        return !page.url().contains(Routes.LOGIN)
                && page.locator("body").isVisible();
    }

    public String getHeading() {
        Locator heading = page.locator("h1, h2").first();
        return heading.isVisible() ? heading.innerText().trim() : "";
    }

    public int getVisibleCardCount() {
        return page.locator(
                "[class*='card']:visible, [data-testid*='card']:visible"
        ).count();
    }

    public SidebarComponent sidebar() {
        return sidebar;
    }

    public HeaderComponent header() {
        return header;
    }
}