package io.github.fernandouchoa.logitrack.models;

public record MaintenanceData(
        String vehiclePlate,
        String maintenanceType,
        String scheduledDate,
        String description,
        String cost
) {
}
