package io.github.fernandouchoa.logitrack.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public final class ConfirmationModalComponent {

    private final Locator dialog;

    public ConfirmationModalComponent(Page page) {
        dialog = page.locator("[role='dialog'], .modal").first();
    }

    public boolean isDisplayed() {
        return dialog.isVisible();
    }

    public String getMessage() {
        return isDisplayed() ? dialog.innerText().trim() : "";
    }

    public void confirm() {
        dialog.locator("button")
                .filter(new Locator.FilterOptions().setHasText("Confirmar"))
                .first()
                .click();
    }

    public void cancel() {
        dialog.locator("button")
                .filter(new Locator.FilterOptions().setHasText("Cancelar"))
                .first()
                .click();
    }
}