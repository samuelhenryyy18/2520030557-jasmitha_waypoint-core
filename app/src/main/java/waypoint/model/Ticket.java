
package waypoint.model;

import java.time.Instant;
import java.util.Objects;

/**
 * ENTITY. Invariants:
 * I1 entry is always present
 * I2 exit is null, or not before entry
 * I3 fare is null, or not negative
 * I4 fare exists only if exit exists
 * I5 a closed ticket never reopens
 */
public final class Ticket {

    private final TicketId id;
    private final SpotId spotId;
    private final Instant entry;

    private Instant exit;
    private Money fare;

    private Ticket(TicketId id, SpotId spotId, Instant entry) {
        this.id = Objects.requireNonNull(id, "Ticket ID cannot be null");
        this.spotId = Objects.requireNonNull(spotId, "Spot ID cannot be null");
        this.entry = Objects.requireNonNull(entry, "Entry time cannot be null");
    }

    public static Ticket open(TicketId id, SpotId spotId, Instant entry) {
        return new Ticket(id, spotId, entry);
    }

    public boolean isOpen() {
        return exit == null;
    }

    public Money close(Instant at, FarePolicy policy) {
        if (!isOpen()) {
            throw new TicketAlreadyClosed();
        }

        Objects.requireNonNull(at, "Exit time cannot be null");
        Objects.requireNonNull(policy, "Fare policy cannot be null");

        if (at.isBefore(entry)) {
            throw new IllegalArgumentException("Exit cannot be before entry");
        }

        Money calculatedFare = Objects.requireNonNull(
            policy.fareFor(entry, at, spotId),
            "Fare cannot be null"
        );

        if (calculatedFare.isNegative()) {
            throw new IllegalArgumentException("Fare cannot be negative");
        }

        this.exit = at;
        this.fare = calculatedFare;

        return fare;
    }

    public Money fare() {
        return fare;
    }
}
