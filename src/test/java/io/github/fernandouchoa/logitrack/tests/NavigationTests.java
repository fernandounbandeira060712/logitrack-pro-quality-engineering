package io.github.fernandouchoa.logitrack.tests;

import io.github.fernandouchoa.logitrack.base.BaseTest;
import io.github.fernandouchoa.logitrack.config.ConfigManager;
import io.github.fernandouchoa.logitrack.pages.DashboardPage;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class NavigationTests extends BaseTest {

    @Test
    @DisplayName("CT-NAV-001 - Deve acessar a área autenticada")
    void shouldAccessAuthenticatedArea() {
        DashboardPage dashboard = authenticate();

        assertTrue(
            dashboard.isLoaded(),
            "O dashboard não foi carregado após a autenticação."
        );
    }

    @Test
    @DisplayName("CT-NAV-002 - Deve navegar para veículos")
    void shouldNavigateToVehicles() {
        assertTrue(
            authenticate()
                .sidebar()
                .accessVehicles()
                .isLoaded(),
            "A página de veículos não foi carregada pelo menu lateral."
        );
    }

    private DashboardPage authenticate() {
        Assumptions.assumeTrue(
            ConfigManager.hasCredentials(),
            "Credenciais obrigatórias não foram informadas."
        );

        return loginPage()
            .open()
            .loginAs(
                ConfigManager.getEmail(),
                ConfigManager.getPassword()
            );
    }
}
