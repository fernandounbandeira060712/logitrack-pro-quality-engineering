package io.github.fernandouchoa.logitrack.data;

import io.github.fernandouchoa.logitrack.models.MaintenanceData;
import io.github.fernandouchoa.logitrack.models.TripData;
import io.github.fernandouchoa.logitrack.models.UserData;
import io.github.fernandouchoa.logitrack.models.VehicleData;
import io.github.fernandouchoa.logitrack.utils.DateUtils;

import java.util.concurrent.ThreadLocalRandom;
import java.util.UUID;

public final class TestDataFactory {

    private TestDataFactory() {
    }

    public static UserData validUser() {
        String id = UUID.randomUUID()
                .toString()
                .substring(0, 8);

        return new UserData(
                "Usuario Automacao",
                "qa." + id + "@teste.com",
                "Teste@123"
        );
    }

    public static VehicleData validVehicle() {
        int firstDigit = ThreadLocalRandom.current()
                .nextInt(0, 10);

        int finalDigits = ThreadLocalRandom.current()
                .nextInt(0, 100);

        String plate = String.format(
                "QAT%dA%02d",
                firstDigit,
                finalDigits
        );

        return new VehicleData(
                plate,
                "Modelo QA Automatizado",
                "Leve",
                "2024"
        );
    }

    public static TripData validTrip(String plate) {
        return new TripData(
                plate,
                "Joao Pessoa",
                "Natal",
                DateUtils.isoDaysFromNow(1),
                DateUtils.isoDaysFromNow(2),
                "220.5"
        );
    }

    public static MaintenanceData validMaintenance(
            String plate
    ) {
        return new MaintenanceData(
                plate,
                "Revisao preventiva",
                DateUtils.isoDaysFromNow(3),
                DateUtils.isoDaysFromNow(4),
                "350.00"
        );
    }
}