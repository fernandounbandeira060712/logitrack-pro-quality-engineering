package io.github.fernandouchoa.logitrack.tests;

import io.github.fernandouchoa.logitrack.base.BaseTest;
import io.github.fernandouchoa.logitrack.config.ConfigManager;
import io.github.fernandouchoa.logitrack.data.TestDataFactory;
import io.github.fernandouchoa.logitrack.models.MaintenanceData;
import io.github.fernandouchoa.logitrack.models.VehicleData;
import io.github.fernandouchoa.logitrack.pages.DashboardPage;
import io.github.fernandouchoa.logitrack.pages.MaintenancePage;
import io.github.fernandouchoa.logitrack.pages.VehiclesPage;
import io.github.fernandouchoa.logitrack.utils.DateUtils;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.github.fernandouchoa.logitrack.utils.MessageAssertions.assertContainsAll;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;

class InvalidMaintenanceDateTests extends BaseTest {

    @Test
    @Tag("business-rule")
    @DisplayName("Impedir manutenÃ§Ã£o com data final anterior Ã  inicial")
    void shouldNotAllowMaintenanceWithEndDateBeforeStartDate() {
        Assumptions.assumeTrue(
                ConfigManager.hasCredentials(),
                "Informe email e password no comando Maven."
        );

        VehicleData vehicle = TestDataFactory.validVehicle();

        MaintenanceData invalidMaintenance =
                new MaintenanceData(
                        vehicle.plate(),
                        "Manutencao com datas invalidas",
                        DateUtils.isoDaysFromNow(5),
                        DateUtils.isoDaysFromNow(4),
                        "350.00"
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
        vehiclesPage.getFeedbackMessage();

        MaintenancePage maintenancePage =
                dashboard.sidebar().accessMaintenance();

        page.waitForURL("**/manutencao");

        maintenancePage
                .clickNewMaintenance()
                .fillMaintenanceForm(invalidMaintenance)
                .save();

        String validationMessage =
                maintenancePage.getFeedbackMessage();

        if (page.locator(
                "[role='dialog']:visible"
        ).count() > 0) {
            maintenancePage.cancel();
        }

        maintenancePage.searchByVehicle(
                vehicle.plate()
        );
        page.waitForTimeout(800);

        boolean invalidMaintenanceWasCreated =
                maintenancePage.containsMaintenance(
                        vehicle.plate()
                );

        assertAll(
                () -> assertContainsAll(
                        validationMessage,
                        "data de finalizacao",
                        "nao pode ser anterior",
                        "data de inicio"
                ),
                () -> assertFalse(
                        invalidMaintenanceWasCreated,
                        "O sistema cadastrou a manutenÃ§Ã£o "
                                + "com datas invÃ¡lidas."
                )
        );

        System.out.println(
                "Mensagem validada: "
                        + validationMessage
        );
    }
}