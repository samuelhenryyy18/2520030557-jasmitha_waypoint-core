
package waypoint.model;

import java.util.Objects;
import java.util.Locale;

/**
 * VALUE OBJECT.
 * Validates and normalises vehicle registration numbers.
 */
public record Registration(String value) {

    // Compact constructor
    public Registration {
        Objects.requireNonNull(value, "Registration cannot be null");

        value = value.trim().toUpperCase(Locale.ROOT);

        if (!value.matches("[A-Z]{2}[0-9]{2}[A-Z]{1,2}[0-9]{4}")) {
            throw new IllegalArgumentException("Invalid registration number");
        }
    }
}
