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

import static io.github.fernandouchoa.logitrack.utils.MessageAssertions.assertContainsAll;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

class DuplicateVehiclePlateTests extends BaseTest {

    @Test
    @Tag("business-rule")
    @DisplayName("Impedir veículos com a mesma placa")
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

        vehiclesPage.createVehicle(original);

        // Consome o toast do primeiro cadastro.
        vehiclesPage.getFeedbackMessage();

        vehiclesPage
                .clickNewVehicle()
                .fillVehicleForm(duplicate)
                .save();

        String validationMessage =
                vehiclesPage.getFeedbackMessage();

        if (page.locator(
                "[role='dialog']:visible"
        ).count() > 0) {
            vehiclesPage.cancel();
        }

        vehiclesPage.searchByPlate(original.plate());
        page.waitForTimeout(800);

        Locator rowsWithPlate = page.locator("tbody tr")
                .filter(
                        new Locator.FilterOptions()
                                .setHasText(original.plate())
                );

        assertAll(
                () -> assertContainsAll(
                        validationMessage,
                        "veiculo",
                        "with placa",
                        original.plate(),
                        "already exists"
                ),
                () -> assertEquals(
                        1,
                        rowsWithPlate.count(),
                        "Defeito identificado: o sistema permitiu "
                                + "mais de um veículo com a placa "
                                + original.plate()
                )
        );

        System.out.println(
                "Mensagem validada: "
                        + validationMessage
        );

        System.out.println(
                "Regra de placa única validada com sucesso: "
                        + original.plate()
        );
    }
}
