package io.github.fernandouchoa.logitrack.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.PlaywrightException;
import com.microsoft.playwright.options.WaitForSelectorState;
import io.github.fernandouchoa.logitrack.config.ConfigManager;
import io.github.fernandouchoa.logitrack.utils.Routes;

public final class LoginPage extends BasePage {

    private final Locator emailInput;
    private final Locator passwordInput;
    private final Locator loginButton;
    private final Locator registerLink;
    private final Locator authenticatedNavigation;

    public LoginPage(Page page) {
        super(page);

        emailInput = page.locator("#email");
        passwordInput = page.locator("#password");
        loginButton = page.locator("button[type='submit']");
        registerLink = page.locator("a[href='/register']");

        authenticatedNavigation = page.locator(
            "aside a[href='/dashboard'], " +
            "nav a[href='/dashboard'], " +
            "a[href='/veiculos']"
        ).first();
    }

    public LoginPage open() {
        navigate(Routes.LOGIN);
        return this;
    }

    public LoginPage fillEmail(String email) {
        fill(emailInput, email);
        return this;
    }

    public LoginPage fillPassword(String password) {
        fill(passwordInput, password);
        return this;
    }

    public DashboardPage loginAs(String email, String password) {
        fillEmail(email);
        fillPassword(password);

        click(loginButton);
        waitForAuthenticatedArea();

        return new DashboardPage(page);
    }

    public LoginPage submit() {
        click(loginButton);
        return this;
    }

    public RegisterPage accessRegistration() {
        click(registerLink);
        return new RegisterPage(page);
    }

    public boolean isDisplayed() {
        return emailInput.isVisible()
            && passwordInput.isVisible()
            && loginButton.isVisible();
    }

    public boolean isAuthenticated() {
        return page.url().contains(Routes.DASHBOARD);
    }

    public String getEmailValidationMessage() {
        return validationMessage(emailInput);
    }

    public String getPasswordValidationMessage() {
        return validationMessage(passwordInput);
    }

    private void waitForAuthenticatedArea() {
        try {
            page.waitForCondition(
                () -> page.url().contains(Routes.DASHBOARD),
                new Page.WaitForConditionOptions()
                    .setTimeout(ConfigManager.getTimeout())
            );

            authenticatedNavigation.waitFor(
                new Locator.WaitForOptions()
                    .setState(WaitForSelectorState.VISIBLE)
                    .setTimeout(ConfigManager.getTimeout())
            );
        }
        catch (PlaywrightException exception) {
            throw new IllegalStateException(
                "O login não concluiu a navegação para o dashboard. "
                    + "URL atual: " + page.url()
                    + " | Conteúdo visível: " + visibleBodyText(),
                exception
            );
        }
    }

    private String visibleBodyText() {
        try {
            String text = page.locator("body").innerText().trim();
            return text.length() > 500
                ? text.substring(0, 500) + "..."
                : text;
        }
        catch (Exception exception) {
            return "indisponível";
        }
    }

    private String validationMessage(Locator field) {
        Object message = field.evaluate(
            "element => element.validationMessage"
        );

        return message == null ? "" : message.toString();
    }
}
