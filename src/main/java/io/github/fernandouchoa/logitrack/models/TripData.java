package io.github.fernandouchoa.logitrack.models;

public record TripData(
        String origin,
        String destination,
        String departureDate,
        String expectedArrivalDate,
        String vehiclePlate,
        String driver
) {
}