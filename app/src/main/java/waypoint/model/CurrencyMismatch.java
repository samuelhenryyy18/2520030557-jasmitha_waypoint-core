
package waypoint.model;

public class CurrencyMismatch extends IllegalArgumentException {
    public CurrencyMismatch() {
        super("Currency mismatch");
    }
}
