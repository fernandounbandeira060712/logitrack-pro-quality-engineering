package io.github.fernandouchoa.logitrack.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.github.fernandouchoa.logitrack.utils.Routes;

public final class LoginPage extends BasePage {

    private final Locator emailInput;
    private final Locator passwordInput;
    private final Locator loginButton;
    private final Locator registerLink;

    public LoginPage(Page page) {
        super(page);

        emailInput = page.locator("#email");
        passwordInput = page.locator("#password");
        loginButton = page.locator(
                "button[type='submit']"
        );
        registerLink = page.locator(
                "a[href='/register']"
        );
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

    public DashboardPage loginAs(
            String email,
            String password
    ) {
        fillEmail(email);
        fillPassword(password);
        click(loginButton);

        page.waitForURL("**/dashboard");

        return new DashboardPage(page);
    }

    public LoginPage submit() {
        click(loginButton);
        page.waitForTimeout(500);
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
        return page.url().contains("/dashboard");
    }

    public String getEmailValidationMessage() {
        return validationMessage(emailInput);
    }

    public String getPasswordValidationMessage() {
        return validationMessage(passwordInput);
    }

    private String validationMessage(Locator field) {
        Object message = field.evaluate(
                "element => element.validationMessage"
        );

        return message == null ? "" : message.toString();
    }
}