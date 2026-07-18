package io.github.fernandouchoa.logitrack.tests;

import io.github.fernandouchoa.logitrack.base.BaseTest;
import io.github.fernandouchoa.logitrack.config.ConfigManager;
import io.github.fernandouchoa.logitrack.pages.DashboardPage;
import io.github.fernandouchoa.logitrack.pages.LoginPage;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("LogiTrack Pro")
@Feature("Autenticacao")
class LoginTests extends BaseTest {

    @Test
    @Tag("smoke")
    @Severity(SeverityLevel.BLOCKER)
    @DisplayName("CT-LOGIN-001 - Login com credenciais validas")
    void shouldLoginWithValidCredentials() {
        Assumptions.assumeTrue(
                ConfigManager.hasCredentials(),
                "Configure LOGITRACK_EMAIL e LOGITRACK_PASSWORD."
        );

        DashboardPage dashboard = loginPage()
                .open()
                .loginAs(
                        ConfigManager.getEmail(),
                        ConfigManager.getPassword()
                );

        assertTrue(
                dashboard.isLoaded(),
                "O usuario deveria acessar a area autenticada. URL: "
                        + page.url()
        );
    }

    @Test
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("CT-LOGIN-002 - Rejeitar credenciais invalidas")
    void shouldRejectInvalidCredentials() {
        LoginPage loginPage = loginPage()
                .open()
                .fillEmail("usuario.inexistente@teste.com")
                .fillPassword("SenhaInvalida@123")
                .submit();

        assertFalse(
                loginPage.isAuthenticated(),
                "Credenciais invalidas nao podem autenticar. URL: "
                        + page.url()
        );
    }

    @Test
    @DisplayName("CT-LOGIN-003 - Validar campos obrigatorios")
    void shouldValidateRequiredFields() {
        LoginPage loginPage = loginPage().open().submit();

        boolean emailRequired =
                !loginPage.getEmailValidationMessage().isBlank();

        boolean passwordRequired =
                !loginPage.getPasswordValidationMessage().isBlank();

        assertTrue(
                emailRequired || passwordRequired,
                "Os campos obrigatorios deveriam ser validados."
        );
    }

    @Test
    @DisplayName("CT-LOGIN-004 - Rejeitar email invalido")
    void shouldRejectInvalidEmailFormat() {
        LoginPage loginPage = loginPage()
                .open()
                .fillEmail("email-invalido")
                .fillPassword("Teste@123")
                .submit();

        assertFalse(
                loginPage.getEmailValidationMessage().isBlank(),
                "O formato invalido do email deveria ser informado."
        );
    }
}