package io.github.fernandouchoa.logitrack.tests;

import com.microsoft.playwright.Locator;
import io.github.fernandouchoa.logitrack.base.BaseTest;
import io.github.fernandouchoa.logitrack.config.ConfigManager;
import io.github.fernandouchoa.logitrack.data.TestDataFactory;
import io.github.fernandouchoa.logitrack.models.MaintenanceData;
import io.github.fernandouchoa.logitrack.models.VehicleData;
import io.github.fernandouchoa.logitrack.pages.DashboardPage;
import io.github.fernandouchoa.logitrack.pages.MaintenancePage;
import io.github.fernandouchoa.logitrack.pages.VehiclesPage;
import io.github.fernandouchoa.logitrack.utils.DateUtils;
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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("LogiTrack Pro")
@Feature("Manutenções")
class NegativeMaintenanceCostTests extends BaseTest {

    @Test
    @Tag("business-rule")
    @Tag("negative")
    @Story("Validação financeira da manutenção")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("CT-MAN-004 - Impedir manutenção com custo negativo")
    @Description("""
            Valida que uma manutenção com custo estimado negativo
            não seja persistida e que o formulário apresente algum
            mecanismo de bloqueio ou feedback para o usuário.
            """)
    void shouldNotAllowMaintenanceWithNegativeCost() {
        Assumptions.assumeTrue(
                ConfigManager.hasCredentials(),
                "Informe email e password no comando Maven."
        );

        VehicleData vehicle = TestDataFactory.validVehicle();

        String serviceType =
                "Custo negativo " + vehicle.plate();

        MaintenanceData invalidMaintenance =
                new MaintenanceData(
                        vehicle.plate(),
                        serviceType,
                        DateUtils.isoDaysFromNow(3),
                        DateUtils.isoDaysFromNow(4),
                        "-1.00"
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
                vehicleMessage.toLowerCase().contains("sucesso"),
                "O veículo de apoio não foi cadastrado corretamente. "
                        + "Mensagem: " + vehicleMessage
        );

        MaintenancePage maintenancePage = dashboard
                .sidebar()
                .accessMaintenance();

        page.waitForURL("**/manutencao");

        assertTrue(
                maintenancePage.isLoaded(),
                "A página de manutenção não foi carregada. URL atual: "
                        + page.url()
        );

        maintenancePage
                .clickNewMaintenance()
                .fillMaintenanceForm(invalidMaintenance);

        Locator dialog = page.locator(
                "[role='dialog']:visible"
        ).first();

        Locator costInput = dialog.locator(
                "#custoEstimado"
        ).first();

        String minimumValue =
                costInput.getAttribute("min");

        boolean browserMarkedInputInvalid =
                (Boolean) costInput.evaluate(
                        "element => !element.checkValidity()"
                );

        maintenancePage.save();
        page.waitForTimeout(1500);

        boolean dialogRemainedOpen = page.locator(
                "[role='dialog']:visible"
        ).count() > 0;

        String nativeValidationMessage = "";

        if (dialogRemainedOpen
                && costInput.count() > 0) {
            Object validationMessage =
                    costInput.evaluate(
                            "element => element.validationMessage"
                    );

            if (validationMessage != null) {
                nativeValidationMessage =
                        validationMessage.toString();
            }
        }

        String toastMessage = readVisibleToast();

        if (dialogRemainedOpen) {
            maintenancePage.cancel();
        }

        maintenancePage.searchByVehicle(
                vehicle.plate()
        );

        page.waitForTimeout(800);

        boolean invalidMaintenanceWasCreated =
                maintenancePage.containsMaintenance(
                        serviceType
                );

        boolean validationWasPresented =
                browserMarkedInputInvalid
                        || dialogRemainedOpen
                        || !nativeValidationMessage.isBlank()
                        || !toastMessage.isBlank();

        assertAll(
                "Regra de custo estimado",
                () -> assertFalse(
                        invalidMaintenanceWasCreated,
                        "Defeito identificado: o sistema permitiu "
                                + "agendar manutenção com custo negativo "
                                + "para o veículo " + vehicle.plate()
                ),
                () -> assertTrue(
                        validationWasPresented,
                        "O cadastro não foi persistido, mas nenhum "
                                + "feedback ou bloqueio de validação "
                                + "foi identificado para o custo negativo."
                )
        );

        System.out.println(
                "Regra de custo negativo validada: "
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

    private String readVisibleToast() {
        Locator toasts = page.locator(
                "[data-sonner-toast]:visible"
        );

        if (toasts.count() == 0) {
            return "";
        }

        String message = toasts.last()
                .textContent();

        if (message == null) {
            return "";
        }

        return message
                .trim()
                .replaceAll("\\s+", " ");
    }
}
