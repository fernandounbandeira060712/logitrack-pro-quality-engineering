package io.github.fernandouchoa.logitrack.tests;

import io.github.fernandouchoa.logitrack.base.BaseTest;
import io.github.fernandouchoa.logitrack.config.ConfigManager;
import io.github.fernandouchoa.logitrack.pages.DashboardPage;

import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;

class ApplicationMappingTests extends BaseTest {

    @Test
    @DisplayName("DISCOVERY-001 - Mapear links da área autenticada")
    void shouldMapAuthenticatedNavigationLinks() {
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

        List<String> links = dashboard
            .sidebar()
            .getVisibleNavigationItems();

        System.out.println("URL autenticada: " + page.url());
        System.out.println("Título: " + page.title());
        System.out.println("Menu identificado: " + links);

        assertFalse(
            links.isEmpty(),
            "Nenhum item de navegação foi identificado."
        );
    }
}
