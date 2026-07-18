package io.github.fernandouchoa.logitrack.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.github.fernandouchoa.logitrack.pages.LoginPage;

public final class HeaderComponent {

    private final Page page;

    public HeaderComponent(Page page) {
        this.page = page;
    }

    public boolean isLogoutAvailable() {
        return logoutControl().count() > 0
                && logoutControl().first().isVisible();
    }

    public LoginPage logout() {
        logoutControl().first().click();
        return new LoginPage(page);
    }

    public String getAuthenticatedUserText() {
        Locator user = page.locator(
                "[data-testid='user-name'], " +
                "[class*='user-name'], header [class*='user']"
        ).first();

        return user.isVisible() ? user.innerText().trim() : "";
    }

    private Locator logoutControl() {
        return page.locator("button, a")
                .filter(new Locator.FilterOptions().setHasText("Sair"));
    }
}