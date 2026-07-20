package io.github.fernandouchoa.logitrack.tests;

import io.github.fernandouchoa.logitrack.base.BaseTest;
import io.github.fernandouchoa.logitrack.config.ConfigManager;
import io.github.fernandouchoa.logitrack.data.TestDataFactory;
import io.github.fernandouchoa.logitrack.models.TripData;
import io.github.fernandouchoa.logitrack.models.VehicleData;
import io.github.fernandouchoa.logitrack.pages.DashboardPage;
import io.github.fernandouchoa.logitrack.pages.TripsPage;
import io.github.fernandouchoa.logitrack.pages.VehiclesPage;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TripRegistrationTests extends BaseTest {

    @Test
    @Tag("e2e")
    @DisplayName("Cadastrar viagem e validar exibição na tabela")
    void shouldRegisterTripAndDisplayItInTable() {
        Assumptions.assumeTrue(
                ConfigManager.hasCredentials(),
                "Informe email e password no comando Maven."
        );

        VehicleData vehicle = TestDataFactory.validVehicle();
        TripData trip = TestDataFactory.validTrip(
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

        TripsPage tripsPage = dashboard
                .sidebar()
                .accessTrips();

        page.waitForURL("**/viagens");
        page.waitForTimeout(800);

        assertTrue(
                tripsPage.isLoaded(),
                "A página de viagens não foi carregada. URL atual: "
                        + page.url()
        );

        tripsPage.createTrip(trip);

        page.waitForTimeout(1500);

        assertTrue(
                page.locator(
                        "[role='dialog']:visible"
                ).count() == 0,
                "O formulário de viagem permaneceu aberto."
        );

        tripsPage.search(trip.origin());

        page.waitForTimeout(800);

        assertAll(
                () -> assertTrue(
                        tripsPage.containsTrip(
                                vehicle.plate()
                        ),
                        "A viagem não foi encontrada pela placa: "
                                + vehicle.plate()
                ),
                () -> assertTrue(
                        tripsPage.containsTrip(
                                trip.origin()
                        ),
                        "A origem não apareceu na tabela: "
                                + trip.origin()
                ),
                () -> assertTrue(
                        tripsPage.containsTrip(
                                trip.destination()
                        ),
                        "O destino não apareceu na tabela: "
                                + trip.destination()
                )
        );

        System.out.println(
                "Viagem cadastrada e validada com sucesso: "
                        + vehicle.plate()
                        + " | "
                        + trip.origin()
                        + " -> "
                        + trip.destination()
        );
    }
}
