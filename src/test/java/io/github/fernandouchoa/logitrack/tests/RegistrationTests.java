package io.github.fernandouchoa.logitrack.tests;

import io.github.fernandouchoa.logitrack.base.BaseTest;
import io.github.fernandouchoa.logitrack.pages.RegisterPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RegistrationTests extends BaseTest {

    @Test
    @DisplayName("CT-CAD-001 - Exibir formulÃ¡rio de cadastro")
    void shouldDisplayRegistrationForm() {
        RegisterPage registerPage = new RegisterPage(page).open();

        assertTrue(registerPage.isDisplayed());
    }

    @Test
    @DisplayName("CT-CAD-002 - Rejeitar senha menor que oito caracteres")
    void shouldRejectShortPassword() {
        RegisterPage registerPage = new RegisterPage(page)
                .open()
                .fillName("UsuÃ¡rio Teste")
                .fillEmail("qa.teste@teste.com")
                .fillPassword("1234567")
                .submit();

        assertFalse(
                registerPage.getPasswordValidationMessage().isBlank()
        );
    }
}