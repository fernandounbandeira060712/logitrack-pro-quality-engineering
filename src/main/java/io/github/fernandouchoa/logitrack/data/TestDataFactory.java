package io.github.fernandouchoa.logitrack.data;

import io.github.fernandouchoa.logitrack.models.MaintenanceData;
import io.github.fernandouchoa.logitrack.models.TripData;
import io.github.fernandouchoa.logitrack.models.VehicleData;
import io.github.fernandouchoa.logitrack.utils.DateUtils;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public final class TestDataFactory {

    private static final AtomicLong SEQUENCE =
            new AtomicLong();

    private TestDataFactory() {
    }

    public static VehicleData validVehicle() {
        return new VehicleData(
                uniqueVehiclePlate(),
                "Modelo QA Automatizado",
                "Leve",
                "2024"
        );
    }

    public static MaintenanceData validMaintenance(
            String vehiclePlate
    ) {
        return new MaintenanceData(
                vehiclePlate,
                "Revisao preventiva",
                DateUtils.isoDaysFromNow(3),
                DateUtils.isoDaysFromNow(4),
                "350.00"
        );
    }

    public static TripData validTrip(
            String vehiclePlate
    ) {
        return new TripData(
                vehiclePlate,
                "Joao Pessoa",
                "Natal",
                DateUtils.isoDaysFromNow(1),
                DateUtils.isoDaysFromNow(2),
                "220.5"
        );
    }

    /**
     * Gera uma placa no padrão Mercosul ABC1D23.
     *
     * A versão anterior utilizava sempre o prefixo QAT,
     * deixando apenas 26.000 combinações possíveis.
     * Como o ambiente do desafio é compartilhado e mantém
     * dados de execuções anteriores, colisões podiam ocorrer.
     *
     * A nova estratégia mantém o prefixo Q para facilitar a
     * identificação dos dados de QA, mas utiliza duas letras,
     * um dígito, outra letra e dois números variáveis.
     * Isso disponibiliza mais de 17 milhões de combinações.
     */
    private static String uniqueVehiclePlate() {
        long value =
                UUID.randomUUID().getMostSignificantBits()
                        ^ UUID.randomUUID().getLeastSignificantBits()
                        ^ System.nanoTime()
                        ^ SEQUENCE.incrementAndGet();

        char secondLetter = letterFrom(value);
        value = Long.rotateRight(value, 9);

        char thirdLetter = letterFrom(value);
        value = Long.rotateRight(value, 9);

        int digit = numberFrom(value, 10);
        value = Long.rotateRight(value, 9);

        char mercosulLetter = letterFrom(value);
        value = Long.rotateRight(value, 9);

        int finalNumber = numberFrom(value, 100);

        return String.format(
                "Q%c%c%d%c%02d",
                secondLetter,
                thirdLetter,
                digit,
                mercosulLetter,
                finalNumber
        );
    }

    private static char letterFrom(long value) {
        return (char) (
                'A' + Math.floorMod(value, 26)
        );
    }

    private static int numberFrom(
            long value,
            int limit
    ) {
        return (int) Math.floorMod(
                value,
                limit
        );
    }
}
