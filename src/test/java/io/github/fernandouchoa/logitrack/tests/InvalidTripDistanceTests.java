package io.github.fernandouchoa.logitrack.tests;

import com.microsoft.playwright.Locator;
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
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("LogiTrack Pro")
@Feature("Viagens")
class InvalidTripDistanceTests extends BaseTest {

    private static final String REPOSITORY_ISSUES_URL =
            "https://github.com/fernandounbandeira060712/"
                    + "logitrack-pro-quality-engineering/issues/";

    @Test
    @Tag("business-rule")
    @Tag("negative")
    @Tag("known-defect")
    @Story("Validação da quilometragem da viagem")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("CT-VIA-004 - Impedir viagem com quilometragem negativa")
    @Description("""
            Reproduz o BUG-TRIP-002: a aplicação permite persistir
            uma viagem com quilometragem negativa.
            """)
    void shouldNotAllowTripWithNegativeDistance() {
        requireKnownDefectsEnabled(
                "BUG-TRIP-002",
                6
        );

        validateInvalidDistance(
                "-1",
                "km negativo",
                "BUG-TRIP-002"
        );
    }

    @Test
    @Tag("business-rule")
    @Tag("boundary")
    @Tag("known-defect")
    @Story("Validação da quilometragem da viagem")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("CT-VIA-005 - Impedir viagem com quilometragem igual a zero")
    @Description("""
            Reproduz o BUG-TRIP-003: a aplicação permite persistir
            uma viagem com quilometragem igual a zero.
            """)
    void shouldNotAllowTripWithZeroDistance() {
        requireKnownDefectsEnabled(
                "BUG-TRIP-003",
                7
        );

        validateInvalidDistance(
                "0",
                "km zero",
                "BUG-TRIP-003"
        );
    }

    private void requireKnownDefectsEnabled(
            String defectId,
            int issueNumber
    ) {
        Assumptions.assumeTrue(
                ConfigManager.hasCredentials(),
                "Informe email e password no comando Maven."
        );

        Allure.link(
                defectId,
                REPOSITORY_ISSUES_URL + issueNumber
        );

        Assumptions.assumeTrue(
                Boolean.parseBoolean(
                        System.getProperty(
                                "runKnownDefects",
                                "false"
                        )
                ),
                defectId
                        + " é um defeito conhecido. "
                        + "Execute com -DrunKnownDefects=true "
                        + "ou use -RunKnownDefects no script."
        );
    }

    private void validateInvalidDistance(
            String distance,
            String scenario,
            String defectId
    ) {
        VehicleData vehicle =
                TestDataFactory.validVehicle();

        String uniqueOrigin =
                "Origem " + scenario + " " + vehicle.plate();

        String uniqueDestination =
                "Destino " + scenario + " " + vehicle.plate();

        TripData invalidTrip = new TripData(
                vehicle.plate(),
                uniqueOrigin,
                uniqueDestination,
                DateUtils.isoDaysFromNow(1),
                DateUtils.isoDaysFromNow(2),
                distance
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

        String vehicleMessage =
                vehiclesPage.getFeedbackMessage();

        assertTrue(
                vehicleMessage
                        .toLowerCase(Locale.ROOT)
                        .contains("sucesso"),
                "O veículo de apoio não foi cadastrado. "
                        + "Mensagem: " + vehicleMessage
        );

        TripsPage tripsPage = dashboard
                .sidebar()
                .accessTrips();

        page.waitForURL("**/viagens");

        assertTrue(
                tripsPage.isLoaded(),
                "A página de viagens não foi carregada. URL atual: "
                        + page.url()
        );

        tripsPage
                .clickNewTrip()
                .fillTripForm(invalidTrip);

        Locator dialog = page.locator(
                "[role='dialog']:visible"
        ).first();

        Locator distanceInput = dialog.locator(
                "#kmPercorrida"
        ).first();

        String minimumValue =
                distanceInput.getAttribute("min");

        boolean browserMarkedInputInvalid =
                (Boolean) distanceInput.evaluate(
                        "element => !element.checkValidity()"
                );

        tripsPage.save();
        page.waitForTimeout(1500);

        boolean dialogRemainedOpen = page.locator(
                "[role='dialog']:visible"
        ).count() > 0;

        String nativeValidationMessage = "";

        if (dialogRemainedOpen
                && distanceInput.count() > 0) {
            Object validationMessage =
                    distanceInput.evaluate(
                            "element => element.validationMessage"
                    );

            if (validationMessage != null) {
                nativeValidationMessage =
                        validationMessage.toString();
            }
        }

        String toastMessage =
                tripsPage.getFeedbackMessage();

        boolean toastLooksLikeValidation =
                containsValidationTerm(toastMessage);

        if (dialogRemainedOpen) {
            tripsPage.cancel();
        }

        tripsPage.search(uniqueOrigin);
        page.waitForTimeout(800);

        boolean invalidTripWasCreated =
                tripsPage.containsTrip(uniqueOrigin);

        boolean validationWasPresented =
                browserMarkedInputInvalid
                        || dialogRemainedOpen
                        || !nativeValidationMessage.isBlank()
                        || toastLooksLikeValidation;

        assertAll(
                "Regra de quilometragem da viagem",
                () -> assertFalse(
                        invalidTripWasCreated,
                        "Defeito identificado: o sistema permitiu "
                                + "cadastrar viagem com quilometragem "
                                + distance
                                + " para o veículo "
                                + vehicle.plate()
                                + ". Consulte "
                                + defectId
                                + "."
                ),
                () -> assertTrue(
                        validationWasPresented,
                        "Nenhum feedback de validação foi identificado "
                                + "para a quilometragem "
                                + distance
                                + ". Toast exibido: "
                                + (toastMessage.isBlank()
                                ? "nenhum"
                                : toastMessage)
                )
        );

        System.out.println(
                "Regra de quilometragem validada: "
                        + distance
                        + " | "
                        + vehicle.plate()
        );

        System.out.println(
                "Atributo min do campo: "
                        + (minimumValue == null
                        ? "não informado"
                        : minimumValue)
        );

        System.out.println(
                "Mensagem nativa: "
                        + (nativeValidationMessage.isBlank()
                        ? "não exibida"
                        : nativeValidationMessage)
        );

        System.out.println(
                "Toast: "
                        + (toastMessage.isBlank()
                        ? "não exibido"
                        : toastMessage)
        );
    }

    private boolean containsValidationTerm(
            String message
    ) {
        if (message == null || message.isBlank()) {
            return false;
        }

        String normalized = message
                .toLowerCase(Locale.ROOT);

        return normalized.contains("validation")
                || normalized.contains("validacao")
                || normalized.contains("validação")
                || normalized.contains("inval")
                || normalized.contains("erro")
                || normalized.contains("maior")
                || normalized.contains("zero")
                || normalized.contains("negativ");
    }
}
