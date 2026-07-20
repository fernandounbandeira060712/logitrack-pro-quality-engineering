package io.github.fernandouchoa.logitrack.tests;

import io.github.fernandouchoa.logitrack.base.BaseTest;
import io.github.fernandouchoa.logitrack.config.ConfigManager;
import io.github.fernandouchoa.logitrack.data.TestDataFactory;
import io.github.fernandouchoa.logitrack.models.MaintenanceData;
import io.github.fernandouchoa.logitrack.models.VehicleData;
import io.github.fernandouchoa.logitrack.pages.DashboardPage;
import io.github.fernandouchoa.logitrack.pages.MaintenancePage;
import io.github.fernandouchoa.logitrack.pages.VehiclesPage;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MaintenanceSchedulingTests extends BaseTest {

    @Test
    @Tag("e2e")
    @DisplayName("Agendar manutenção e validar exibição na tabela")
    void shouldScheduleMaintenanceAndDisplayItInTable() {
        Assumptions.assumeTrue(
                ConfigManager.hasCredentials(),
                "Informe email e password no comando Maven."
        );

        VehicleData vehicle = TestDataFactory.validVehicle();

        MaintenanceData maintenance =
                TestDataFactory.validMaintenance(
                        vehicle.plate()
                );

        DashboardPage dashboard = loginPage()
                .open()
                .loginAs(
                        ConfigManager.getEmail(),
                        ConfigManager.getPassword()
                );

        VehiclesPage vehiclesPage = dashboard
                .sidebar()
                .accessVehicles();

        assertTrue(
                vehiclesPage.isLoaded(),
                "A página de veículos não foi carregada."
        );

        vehiclesPage.createVehicle(vehicle);

        page.waitForTimeout(1500);

        assertTrue(
                page.locator(
                        "[role='dialog']:visible"
                ).count() == 0,
                "O formulário de veículo permaneceu aberto."
        );

        MaintenancePage maintenancePage =
                dashboard.sidebar().accessMaintenance();

        page.waitForURL("**/manutencao");
        page.waitForTimeout(800);

        assertTrue(
                maintenancePage.isLoaded(),
                "A página de manutenção não foi carregada. URL atual: "
                        + page.url()
        );

        maintenancePage.schedule(maintenance);

        page.waitForTimeout(1500);

        assertTrue(
                page.locator(
                        "[role='dialog']:visible"
                ).count() == 0,
                "O formulário de manutenção permaneceu aberto."
        );

        maintenancePage.searchByVehicle(
                vehicle.plate()
        );

        page.waitForTimeout(800);

        assertAll(
                () -> assertTrue(
                        maintenancePage.containsMaintenance(
                                vehicle.plate()
                        ),
                        "A manutenção não foi encontrada pela placa: "
                                + vehicle.plate()
                ),
                () -> assertTrue(
                        maintenancePage.containsMaintenance(
                                maintenance.serviceType()
                        ),
                        "O serviço não apareceu na tabela: "
                                + maintenance.serviceType()
                )
        );

        System.out.println(
                "Manutenção agendada e validada com sucesso: "
                        + vehicle.plate()
                        + " | "
                        + maintenance.serviceType()
        );
    }
}
