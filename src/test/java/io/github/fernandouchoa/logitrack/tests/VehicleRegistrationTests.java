package io.github.fernandouchoa.logitrack.tests;

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
import static org.junit.jupiter.api.Assertions.assertTrue;

class VehicleRegistrationTests extends BaseTest {

    @Test
    @Tag("e2e")
    @DisplayName("Cadastrar veÃ­culo e validar mensagem e tabela")
    void shouldRegisterVehicleAndDisplayItInTable() {
        Assumptions.assumeTrue(
                ConfigManager.hasCredentials(),
                "Informe email e password no comando Maven."
        );

        VehicleData vehicle = TestDataFactory.validVehicle();

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
                "A pÃ¡gina de veÃ­culos nÃ£o foi carregada."
        );

        vehiclesPage.createVehicle(vehicle);

        String successMessage =
                vehiclesPage.getFeedbackMessage();

        vehiclesPage.searchByPlate(vehicle.plate());
        page.waitForTimeout(800);

        assertAll(
                () -> assertContainsAll(
                        successMessage,
                        "veiculo",
                        "sucesso"
                ),
                () -> assertTrue(
                        vehiclesPage.containsVehicle(
                                vehicle.plate()
                        ),
                        "O veÃ­culo nÃ£o foi encontrado pela placa: "
                                + vehicle.plate()
                ),
                () -> assertTrue(
                        vehiclesPage.containsVehicle(
                                vehicle.model()
                        ),
                        "O modelo nÃ£o apareceu na tabela: "
                                + vehicle.model()
                )
        );

        System.out.println(
                "Mensagem validada: " + successMessage
        );
    }
}