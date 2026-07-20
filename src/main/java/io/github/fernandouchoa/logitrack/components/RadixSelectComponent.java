package io.github.fernandouchoa.logitrack.components;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public final class RadixSelectComponent {

    private final Page page;

    public RadixSelectComponent(Page page) {
        this.page = page;
    }

    public void selectByText(
            Locator trigger,
            String optionText
    ) {
        trigger.click();

        Locator option = page.locator(
                "[role='option']:visible"
        ).filter(
                new Locator.FilterOptions()
                        .setHasText(optionText)
        ).first();

        if (option.count() == 0) {
            throw new IllegalStateException(
                    "Opcao nao encontrada: " + optionText
                            + ". Opcoes visiveis: "
                            + page.locator(
                                    "[role='option']:visible"
                            ).allInnerTexts()
            );
        }

        option.click();
    }

    public void selectFirst(Locator trigger) {
        trigger.click();

        Locator option = page.locator(
                "[role='option']:visible"
        ).first();

        if (option.count() == 0) {
            throw new IllegalStateException(
                    "Nenhuma opcao visivel foi encontrada."
            );
        }

        option.click();
    }
}