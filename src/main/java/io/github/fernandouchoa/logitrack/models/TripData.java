package io.github.fernandouchoa.logitrack.models;

public record TripData(
        String vehiclePlate,
        String origin,
        String destination,
        String departureDate,
        String arrivalDate,
        String distanceKm
) {
}