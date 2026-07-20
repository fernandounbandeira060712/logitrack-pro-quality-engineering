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

import static io.github.fernandouchoa.logitrack.utils.MessageAssertions.assertContainsAll;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MaintenanceSchedulingTests extends BaseTest {

    @Test
    @Tag("e2e")
    @DisplayName("Agendar manutenÃ§Ã£o e validar mensagem e tabela")
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

        vehiclesPage.createVehicle(vehicle);

        // Consome a mensagem do cadastro do veÃ­culo.
        vehiclesPage.getFeedbackMessage();

        MaintenancePage maintenancePage =
                dashboard.sidebar().accessMaintenance();

        page.waitForURL("**/manutencao");

        maintenancePage.schedule(maintenance);

        String successMessage =
                maintenancePage.getFeedbackMessage();

        maintenancePage.searchByVehicle(
                vehicle.plate()
        );
        page.waitForTimeout(800);

        assertAll(
                () -> assertContainsAll(
                        successMessage,
                        "manutencao",
                        "sucesso"
                ),
                () -> assertTrue(
                        maintenancePage.containsMaintenance(
                                vehicle.plate()
                        ),
                        "A manutenÃ§Ã£o nÃ£o foi encontrada pela placa: "
                                + vehicle.plate()
                ),
                () -> assertTrue(
                        maintenancePage.containsMaintenance(
                                maintenance.serviceType()
                        ),
                        "O serviÃ§o nÃ£o apareceu na tabela: "
                                + maintenance.serviceType()
                )
        );

        System.out.println(
                "Mensagem validada: " + successMessage
        );
    }
}