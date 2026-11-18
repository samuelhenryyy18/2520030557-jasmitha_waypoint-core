
package waypoint.model;

record TicketId(String value) {
    public TicketId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Ticket ID cannot be null or blank");
        }
    }
}

record SpotId(String value) {
    public SpotId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Spot ID cannot be null or blank");
        }
    }
}

record VehicleId(String value) {
    public VehicleId {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Vehicle ID cannot be null or blank");
        }
    }
}
