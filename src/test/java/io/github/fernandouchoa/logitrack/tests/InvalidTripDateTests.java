package io.github.fernandouchoa.logitrack.tests;

import io.github.fernandouchoa.logitrack.base.BaseTest;
import io.github.fernandouchoa.logitrack.config.ConfigManager;
import io.github.fernandouchoa.logitrack.data.TestDataFactory;
import io.github.fernandouchoa.logitrack.models.TripData;
import io.github.fernandouchoa.logitrack.models.VehicleData;
import io.github.fernandouchoa.logitrack.pages.DashboardPage;
import io.github.fernandouchoa.logitrack.pages.TripsPage;
import io.github.fernandouchoa.logitrack.pages.VehiclesPage;
import io.github.fernandouchoa.logitrack.utils.DateUtils;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.github.fernandouchoa.logitrack.utils.MessageAssertions.assertContainsAll;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;

class InvalidTripDateTests extends BaseTest {

    @Test
    @Tag("business-rule")
    @Tag("known-defect")
    @DisplayName("Impedir viagem com chegada anterior Ã  saÃ­da")
    void shouldNotAllowTripWithArrivalBeforeDeparture() {
        Assumptions.assumeTrue(
                ConfigManager.hasCredentials(),
                "Informe email e password no comando Maven."
        );

        Assumptions.assumeTrue(
                Boolean.parseBoolean(
                        System.getProperty(
                                "runKnownDefects",
                                "false"
                        )
                ),
                "BUG-TRIP-001 conhecido. Execute com "
                        + "-DrunKnownDefects=true para reproduzir."
        );

        Allure.link(
                "BUG-TRIP-001",
                "https://github.com/"
                        + "fernandounbandeira060712/"
                        + "logitrack-pro-quality-engineering/"
                        + "issues/1"
        );

        VehicleData vehicle =
                TestDataFactory.validVehicle();

        String uniqueOrigin =
                "Origem " + vehicle.plate();

        String uniqueDestination =
                "Destino " + vehicle.plate();

        TripData invalidTrip = new TripData(
                vehicle.plate(),
                uniqueOrigin,
                uniqueDestination,
                DateUtils.isoDaysFromNow(5),
                DateUtils.isoDaysFromNow(4),
                "220.5"
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

        TripsPage tripsPage = dashboard
                .sidebar()
                .accessTrips();

        page.waitForURL("**/viagens");

        tripsPage
                .clickNewTrip()
                .fillTripForm(invalidTrip)
                .save();

        String validationMessage =
                tripsPage.getFeedbackMessage();

        if (page.locator(
                "[role='dialog']:visible"
        ).count() > 0) {
            tripsPage.cancel();
        }

        tripsPage.search(uniqueOrigin);
        page.waitForTimeout(800);

        boolean invalidTripWasCreated =
                tripsPage.containsTrip(
                        uniqueOrigin
                );

        assertAll(
                () -> assertContainsAll(
                        validationMessage,
                        "data de chegada",
                        "nao pode ser anterior",
                        "data de saida"
                ),
                () -> assertFalse(
                        invalidTripWasCreated,
                        "Defeito identificado: a viagem "
                                + "com datas invalidas foi cadastrada. "
                                + "Consulte BUG-TRIP-001."
                )
        );
    }
}