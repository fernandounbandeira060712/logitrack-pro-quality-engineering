package io.github.fernandouchoa.logitrack.tests;

import io.github.fernandouchoa.logitrack.base.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccessibilityTests extends BaseTest {

    @Test
    @DisplayName("CT-A11Y-001 - Imagens devem possuir atributo alt")
    void imagesShouldHaveAlternativeText() {
        loginPage().open();

        int imagesWithoutAlt = page.locator(
                "img:not([alt])"
        ).count();

        assertEquals(
                0,
                imagesWithoutAlt,
                "Existem imagens sem atributo alt."
        );
    }

    @Test
    @DisplayName("CT-A11Y-002 - BotÃµes devem possuir nome acessÃ­vel")
    void buttonsShouldHaveAccessibleNames() {
        loginPage().open();

        int unnamedButtons = page.locator(
                "button:not([aria-label]):empty"
        ).count();

        assertEquals(
                0,
                unnamedButtons,
                "Existem botÃµes sem nome acessÃ­vel."
        );
    }
}