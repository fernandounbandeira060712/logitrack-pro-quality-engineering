package io.github.fernandouchoa.logitrack.data;

import io.github.fernandouchoa.logitrack.models.MaintenanceData;
import io.github.fernandouchoa.logitrack.models.TripData;
import io.github.fernandouchoa.logitrack.models.UserData;
import io.github.fernandouchoa.logitrack.models.VehicleData;
import io.github.fernandouchoa.logitrack.utils.DateUtils;

import java.util.Locale;
import java.util.UUID;

public final class TestDataFactory {

    private TestDataFactory() {
    }

    public static UserData validUser() {
        String id = UUID.randomUUID().toString().substring(0, 8);

        return new UserData(
                "UsuÃ¡rio AutomaÃ§Ã£o",
                "qa." + id + "@teste.com",
                "Teste@123"
        );
    }

    public static VehicleData validVehicle() {
        String id = UUID.randomUUID()
                .toString()
                .replace("-", "")
                .substring(0, 7)
                .toUpperCase(Locale.ROOT);

        return new VehicleData(
                "QA" + id,
                "Cargo",
                "Volkswagen",
                "2024",
                "Ativo"
        );
    }

    public static TripData validTrip(String plate) {
        return new TripData(
                "JoÃ£o Pessoa/PB",
                "Natal/RN",
                DateUtils.daysFromNow(1),
                DateUtils.daysFromNow(2),
                plate,
                "Motorista AutomaÃ§Ã£o"
        );
    }

    public static MaintenanceData validMaintenance(String plate) {
        return new MaintenanceData(
                plate,
                "Preventiva",
                DateUtils.daysFromNow(3),
                "RevisÃ£o preventiva criada pela automaÃ§Ã£o",
                "350,00"
        );
    }
}