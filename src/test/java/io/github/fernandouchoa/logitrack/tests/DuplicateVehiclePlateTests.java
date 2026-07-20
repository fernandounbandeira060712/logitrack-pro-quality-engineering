package io.github.fernandouchoa.logitrack.tests;

import com.microsoft.playwright.Locator;
import io.github.fernandouchoa.logitrack.base.BaseTest;
import io.github.fernandouchoa.logitrack.config.ConfigManager;
import io.github.fernandouchoa.logitrack.data.TestDataFactory;
import io.github.fernandouchoa.logitrack.models.VehicleData;
import io.github.fernandouchoa.logitrack.pages.DashboardPage;
import io.github.fernandouchoa.logitrack.pages.VehiclesPage;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class DuplicateVehiclePlateTests extends BaseTest {

    @Test
    @Tag("business-rule")
    @DisplayName("Impedir cadastro de veículos com a mesma placa")
    void shouldNotAllowDuplicateVehiclePlate() {
        Assumptions.assumeTrue(
                ConfigManager.hasCredentials(),
                "Informe email e password no comando Maven."
        );

        VehicleData original = TestDataFactory.validVehicle();

        VehicleData duplicate = new VehicleData(
                original.plate(),
                "Modelo QA Duplicado",
                original.type(),
                original.year()
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

        vehiclesPage.createVehicle(original);

        page.waitForTimeout(1500);

        vehiclesPage.searchByPlate(original.plate());

        page.waitForTimeout(800);

        Locator rowsWithPlate = page.locator("tbody tr")
                .filter(
                        new Locator.FilterOptions()
                                .setHasText(original.plate())
                );

        assertEquals(
                1,
                rowsWithPlate.count(),
                "O veículo original não foi encontrado de forma única."
        );

        vehiclesPage
                .clickNewVehicle()
                .fillVehicleForm(duplicate)
                .save();

        page.waitForTimeout(1500);

        int duplicateCount = rowsWithPlate.count();

        System.out.println(
                "Quantidade de veículos encontrados com a placa "
                        + original.plate()
                        + ": "
                        + duplicateCount
        );

        assertEquals(
                1,
                duplicateCount,
                "Defeito identificado: o sistema permitiu cadastrar "
                        + "mais de um veículo com a mesma placa "
                        + original.plate()
        );

        System.out.println(
                "Regra de placa única validada com sucesso: "
                        + original.plate()
        );
    }
}
