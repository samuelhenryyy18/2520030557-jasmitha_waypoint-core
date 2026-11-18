
package waypoint.model;

/**
 * ENTITY. Identity = id.
 * Invariant: a spot cannot be assigned while occupied.
 */
public final class Spot {

    private final SpotId id;
    private VehicleId occupant; // the ONLY mutable field

    // Constructor validating id
    public Spot(SpotId id) {
        if (id == null) {
            throw new IllegalArgumentException("Spot ID cannot be null");
        }
        this.id = id;
    }

    // Assign a vehicle only if the spot is free
    public boolean tryAssign(VehicleId v) {
        if (v == null) {
            throw new IllegalArgumentException("Vehicle ID cannot be null");
        }

        if (occupant != null) {
            return false;
        }

        occupant = v;
        return true;
    }

    // Release the spot if the given vehicle occupies it
    public void release(VehicleId v) {
        if (occupant != null && occupant.equals(v)) {
            occupant = null;
        }
    }

    // Check whether the spot is free
    public boolean isFree() {
        return occupant == null;
    }

    // Equality based only on Spot ID
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Spot other)) {
            return false;
        }

        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
