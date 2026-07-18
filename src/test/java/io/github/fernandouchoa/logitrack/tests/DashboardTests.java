package io.github.fernandouchoa.logitrack.tests;

import io.github.fernandouchoa.logitrack.base.BaseTest;
import io.github.fernandouchoa.logitrack.config.ConfigManager;
import io.github.fernandouchoa.logitrack.pages.DashboardPage;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

class DashboardTests extends BaseTest {

    @Test
    @DisplayName("CT-DASH-001 - Deve carregar o dashboard")
    void shouldLoadDashboard() {
        Assumptions.assumeTrue(ConfigManager.hasCredentials());

        DashboardPage dashboard = loginPage()
                .open()
                .loginAs(
                        ConfigManager.getEmail(),
                        ConfigManager.getPassword()
                );

        assertTrue(dashboard.isLoaded());
    }
}