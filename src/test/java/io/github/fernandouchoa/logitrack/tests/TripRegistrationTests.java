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

import static io.github.fernandouchoa.logitrack.utils.MessageAssertions.assertContainsAll;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TripRegistrationTests extends BaseTest {

    @Test
    @Tag("e2e")
    @DisplayName("Cadastrar viagem e validar mensagem e tabela")
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

        vehiclesPage.createVehicle(vehicle);

        // Consome a mensagem do cadastro do veÃ­culo.
        vehiclesPage.getFeedbackMessage();

        TripsPage tripsPage = dashboard
                .sidebar()
                .accessTrips();

        page.waitForURL("**/viagens");

        tripsPage.createTrip(trip);

        String successMessage =
                tripsPage.getFeedbackMessage();

        tripsPage.search(trip.origin());
        page.waitForTimeout(800);

        assertAll(
                () -> assertContainsAll(
                        successMessage,
                        "viagem",
                        "sucesso"
                ),
                () -> assertTrue(
                        tripsPage.containsTrip(
                                vehicle.plate()
                        ),
                        "A viagem nÃ£o foi encontrada pela placa: "
                                + vehicle.plate()
                ),
                () -> assertTrue(
                        tripsPage.containsTrip(
                                trip.origin()
                        ),
                        "A origem nÃ£o apareceu na tabela: "
                                + trip.origin()
                ),
                () -> assertTrue(
                        tripsPage.containsTrip(
                                trip.destination()
                        ),
                        "O destino nÃ£o apareceu na tabela: "
                                + trip.destination()
                )
        );

        System.out.println(
                "Mensagem validada: " + successMessage
        );
    }
}