package io.github.fernandouchoa.logitrack.tests;

import io.github.fernandouchoa.logitrack.base.BaseTest;
import io.github.fernandouchoa.logitrack.config.ConfigManager;
import io.github.fernandouchoa.logitrack.pages.DashboardPage;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class NavigationTests extends BaseTest {

    @Test
    @DisplayName("CT-NAV-001 - Deve acessar a Ã¡rea autenticada")
    void shouldAccessAuthenticatedArea() {
        DashboardPage dashboard = authenticate();
        assertTrue(dashboard.isLoaded());
    }

    @Test
    @Disabled("Habilitar apÃ³s confirmar o nome real do menu.")
    @DisplayName("CT-NAV-002 - Deve navegar para veÃ­culos")
    void shouldNavigateToVehicles() {
        assertTrue(authenticate().sidebar().accessVehicles().isLoaded());
    }

    private DashboardPage authenticate() {
        Assumptions.assumeTrue(ConfigManager.hasCredentials());

        return loginPage()
                .open()
                .loginAs(
                        ConfigManager.getEmail(),
                        ConfigManager.getPassword()
                );
    }
}