package io.github.fernandouchoa.logitrack.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public final class ToastComponent {

    private final Locator toast;

    public ToastComponent(Page page) {
        toast = page.locator(
                "[role='alert'], [data-sonner-toast], " +
                ".Toastify__toast, .toast, [class*='alert']"
        ).first();
    }

    public boolean isDisplayed() {
        return toast.isVisible();
    }

    public String getMessage() {
        return isDisplayed() ? toast.innerText().trim() : "";
    }

    public boolean containsMessage(String expected) {
        return getMessage().toLowerCase()
                .contains(expected.toLowerCase());
    }
}