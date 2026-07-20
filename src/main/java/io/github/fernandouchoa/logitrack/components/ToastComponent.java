package io.github.fernandouchoa.logitrack.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public final class ToastComponent {

    private static final String UNREAD_FRONT_TOAST =
            "[data-sonner-toast][data-front='true']"
                    + ":not([data-qa-consumed='true']):visible";

    private static final String UNREAD_VISIBLE_TOAST =
            "[data-sonner-toast]"
                    + ":not([data-qa-consumed='true']):visible";

    private static final double MESSAGE_TIMEOUT_MS =
            10_000;

    private final Page page;

    public ToastComponent(Page page) {
        this.page = page;
    }

    public String getMessage() {
        long deadline = System.currentTimeMillis()
                + (long) MESSAGE_TIMEOUT_MS;

        while (System.currentTimeMillis() < deadline) {
            Locator toast = findToastWithText(
                    UNREAD_FRONT_TOAST
            );

            if (toast == null) {
                toast = findToastWithText(
                        UNREAD_VISIBLE_TOAST
                );
            }

            if (toast != null) {
                String message = normalize(
                        toast.textContent()
                );

                toast.evaluate(
                        "element => "
                                + "element.setAttribute("
                                + "'data-qa-consumed', 'true'"
                                + ")"
                );

                return message;
            }

            page.waitForTimeout(100);
        }

        throw new IllegalStateException(
                "Nenhuma nova mensagem Sonner com texto "
                        + "foi exibida no topo da pagina."
        );
    }

    public boolean isVisible() {
        return findToastWithText(
                UNREAD_VISIBLE_TOAST
        ) != null;
    }

    private Locator findToastWithText(
            String selector
    ) {
        Locator toasts = page.locator(selector);

        for (int index = 0;
                index < toasts.count();
                index++) {

            Locator toast = toasts.nth(index);
            String message = toast.textContent();

            if (message != null
                    && !message.isBlank()) {
                return toast;
            }
        }

        return null;
    }

    private String normalize(String value) {
        return value
                .trim()
                .replaceAll("\\s+", " ");
    }
}