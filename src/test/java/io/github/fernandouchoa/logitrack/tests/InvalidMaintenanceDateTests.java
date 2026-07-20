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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InvalidMaintenanceDateTests extends BaseTest {

    @Test
    @Tag("business-rule")
    @DisplayName("Impedir manutenção com data final anterior à inicial")
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

        assertTrue(
                vehiclesPage.isLoaded(),
                "A página de veículos não foi carregada."
        );

        vehiclesPage.createVehicle(vehicle);

        page.waitForTimeout(1500);

        MaintenancePage maintenancePage =
                dashboard.sidebar().accessMaintenance();

        page.waitForURL("**/manutencao");
        page.waitForTimeout(800);

        assertTrue(
                maintenancePage.isLoaded(),
                "A página de manutenção não foi carregada. URL atual: "
                        + page.url()
        );

        maintenancePage
                .clickNewMaintenance()
                .fillMaintenanceForm(invalidMaintenance)
                .save();

        page.waitForTimeout(1500);

        maintenancePage.searchByVehicle(
                vehicle.plate()
        );

        page.waitForTimeout(800);

        boolean invalidMaintenanceWasCreated =
                maintenancePage.containsMaintenance(
                        vehicle.plate()
                );

        System.out.println(
                "Manutenção inválida encontrada na tabela: "
                        + invalidMaintenanceWasCreated
        );

        assertFalse(
                invalidMaintenanceWasCreated,
                "Defeito identificado: o sistema permitiu agendar "
                        + "uma manutenção com data final anterior "
                        + "à data inicial para o veículo "
                        + vehicle.plate()
        );

        System.out.println(
                "Regra de datas da manutenção validada com sucesso."
        );
    }
}
