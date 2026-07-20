package io.github.fernandouchoa.logitrack.e2e;

import io.github.fernandouchoa.logitrack.base.BaseTest;
import io.github.fernandouchoa.logitrack.config.ConfigManager;
import io.github.fernandouchoa.logitrack.data.TestDataFactory;
import io.github.fernandouchoa.logitrack.models.MaintenanceData;
import io.github.fernandouchoa.logitrack.models.TripData;
import io.github.fernandouchoa.logitrack.models.VehicleData;
import io.github.fernandouchoa.logitrack.pages.DashboardPage;
import io.github.fernandouchoa.logitrack.pages.MaintenancePage;
import io.github.fernandouchoa.logitrack.pages.TripsPage;
import io.github.fernandouchoa.logitrack.pages.VehiclesPage;
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

import static io.github.fernandouchoa.logitrack.utils.MessageAssertions.assertContainsAll;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertTrue;

@Epic("LogiTrack Pro")
@Feature("Gerenciamento de frota")
class FleetManagementE2ETests extends BaseTest {

    @Test
    @Tag("e2e")
    @Tag("critical")
    @Story("Fluxo integrado entre veículos, manutenções e viagens")
    @Severity(SeverityLevel.CRITICAL)
    @DisplayName("E2E-001 - Gerenciamento completo da frota")
    @Description("""
            Valida o fluxo integrado da aplicação:
            autenticação, cadastro de veículo, agendamento de manutenção,
            cadastro de viagem e consulta dos registros nas respectivas tabelas.
            """)
    void shouldCompleteFleetManagementFlow() {
        Assumptions.assumeTrue(
                ConfigManager.hasCredentials(),
                "Informe email e password no comando Maven."
        );

        VehicleData vehicle = TestDataFactory.validVehicle();

        MaintenanceData maintenance =
                TestDataFactory.validMaintenance(
                        vehicle.plate()
                );

        TripData trip = TestDataFactory.validTrip(
                vehicle.plate()
        );

        DashboardPage dashboard = loginPage()
                .open()
                .loginAs(
                        ConfigManager.getEmail(),
                        ConfigManager.getPassword()
                );

        assertTrue(
                dashboard.isLoaded(),
                "O Dashboard não foi carregado após o login."
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

        vehiclesPage.searchByPlate(vehicle.plate());
        page.waitForTimeout(800);

        assertAll(
                "Validação do veículo",
                () -> assertContainsAll(
                        vehicleMessage,
                        "veiculo",
                        "sucesso"
                ),
                () -> assertTrue(
                        vehiclesPage.containsVehicle(
                                vehicle.plate()
                        ),
                        "O veículo não foi encontrado pela placa: "
                                + vehicle.plate()
                ),
                () -> assertTrue(
                        vehiclesPage.containsVehicle(
                                vehicle.model()
                        ),
                        "O modelo não apareceu na tabela: "
                                + vehicle.model()
                )
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

        maintenancePage.schedule(maintenance);

        String maintenanceMessage =
                maintenancePage.getFeedbackMessage();

        maintenancePage.searchByVehicle(
                vehicle.plate()
        );

        page.waitForTimeout(800);

        assertAll(
                "Validação da manutenção",
                () -> assertContainsAll(
                        maintenanceMessage,
                        "manutencao",
                        "sucesso"
                ),
                () -> assertTrue(
                        maintenancePage.containsMaintenance(
                                vehicle.plate()
                        ),
                        "A manutenção não foi encontrada pela placa: "
                                + vehicle.plate()
                ),
                () -> assertTrue(
                        maintenancePage.containsMaintenance(
                                maintenance.serviceType()
                        ),
                        "O serviço não apareceu na tabela: "
                                + maintenance.serviceType()
                )
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

        tripsPage.createTrip(trip);

        String tripMessage =
                tripsPage.getFeedbackMessage();

        tripsPage.search(trip.origin());
        page.waitForTimeout(800);

        assertAll(
                "Validação da viagem",
                () -> assertContainsAll(
                        tripMessage,
                        "viagem",
                        "sucesso"
                ),
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
                "Fluxo E2E concluído com sucesso: "
                        + vehicle.plate()
                        + " | "
                        + maintenance.serviceType()
                        + " | "
                        + trip.origin()
                        + " -> "
                        + trip.destination()
        );
    }
}
