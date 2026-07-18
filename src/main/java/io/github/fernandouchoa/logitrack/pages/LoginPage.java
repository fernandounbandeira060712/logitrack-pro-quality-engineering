package io.github.fernandouchoa.logitrack.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.github.fernandouchoa.logitrack.utils.Routes;

public final class LoginPage extends BasePage {

    private final Locator emailInput;
    private final Locator passwordInput;
    private final Locator loginButton;
    private final Locator registerLink;

    public LoginPage(Page page) {
        super(page);

        emailInput = page.locator("input[type='email']").first();
        passwordInput = page.locator("input[type='password']").first();

        loginButton = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Entrar")
        );

        registerLink = page.getByText("Registre-se agora.");
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

        page.waitForTimeout(4000);

        return new DashboardPage(page);
    }

    public LoginPage submit() {
        click(loginButton);
        page.waitForTimeout(2000);
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
        return !page.url().contains(Routes.LOGIN);
    }

    public String getEmailValidationMessage() {
        Object message = emailInput.evaluate(
                "element => element.validationMessage"
        );

        return message == null ? "" : message.toString();
    }

    public String getPasswordValidationMessage() {
        Object message = passwordInput.evaluate(
                "element => element.validationMessage"
        );

        return message == null ? "" : message.toString();
    }
}