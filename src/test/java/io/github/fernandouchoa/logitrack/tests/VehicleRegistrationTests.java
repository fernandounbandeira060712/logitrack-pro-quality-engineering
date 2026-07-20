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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

class VehicleRegistrationTests extends BaseTest {

    @Test
    @Tag("e2e")
    @DisplayName("Cadastrar veículo e validar exibição na tabela")
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
                "A página de veículos não foi carregada."
        );

        vehiclesPage.createVehicle(vehicle);

        page.waitForTimeout(1500);

        assertTrue(
                page.locator("[role='dialog']:visible").count() == 0,
                "O formulário permaneceu aberto após tentar criar o veículo."
        );

        vehiclesPage.searchByPlate(vehicle.plate());

        page.waitForTimeout(800);

        assertAll(
                () -> assertTrue(
                        vehiclesPage.containsVehicle(vehicle.plate()),
                        "O veículo não foi encontrado pela placa: "
                                + vehicle.plate()
                ),
                () -> assertTrue(
                        vehiclesPage.containsVehicle(vehicle.model()),
                        "O modelo do veículo não apareceu na tabela: "
                                + vehicle.model()
                )
        );

        System.out.println(
                "Veículo cadastrado e validado com sucesso: "
                        + vehicle.plate()
                        + " | "
                        + vehicle.model()
        );
    }
}
