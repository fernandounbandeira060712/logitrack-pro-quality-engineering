package io.github.fernandouchoa.logitrack.models;

public record VehicleData(
        String plate,
        String model,
        String brand,
        String year,
        String status
) {
}
