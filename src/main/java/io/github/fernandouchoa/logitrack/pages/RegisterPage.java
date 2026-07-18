package io.github.fernandouchoa.logitrack.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.options.AriaRole;
import io.github.fernandouchoa.logitrack.utils.Routes;

public final class RegisterPage extends BasePage {

    private final Locator nameInput;
    private final Locator emailInput;
    private final Locator passwordInput;
    private final Locator createAccountButton;

    public RegisterPage(Page page) {
        super(page);

        nameInput = page.locator("input").nth(0);
        emailInput = page.locator("input[type='email']").first();
        passwordInput = page.locator("input[type='password']").first();

        createAccountButton = page.getByRole(
                AriaRole.BUTTON,
                new Page.GetByRoleOptions().setName("Criar conta")
        );
    }

    public RegisterPage open() {
        navigate(Routes.REGISTER);
        return this;
    }

    public RegisterPage fillName(String name) {
        fill(nameInput, name);
        return this;
    }

    public RegisterPage fillEmail(String email) {
        fill(emailInput, email);
        return this;
    }

    public RegisterPage fillPassword(String password) {
        fill(passwordInput, password);
        return this;
    }

    public RegisterPage submit() {
        click(createAccountButton);
        return this;
    }

    public boolean isDisplayed() {
        return nameInput.isVisible()
                && emailInput.isVisible()
                && passwordInput.isVisible()
                && createAccountButton.isVisible();
    }

    public String getPasswordValidationMessage() {
        Object message = passwordInput.evaluate(
                "element => element.validationMessage"
        );

        return message == null ? "" : message.toString();
    }
}