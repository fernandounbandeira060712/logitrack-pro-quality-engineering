package io.github.fernandouchoa.logitrack.models;

public record MaintenanceData(
        String vehiclePlate,
        String serviceType,
        String startDate,
        String endDate,
        String estimatedCost
) {
}