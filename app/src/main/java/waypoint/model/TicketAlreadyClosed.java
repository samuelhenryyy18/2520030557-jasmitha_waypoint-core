
package waypoint.model;

public class TicketAlreadyClosed extends IllegalStateException {

    public TicketAlreadyClosed() {
        super("Ticket is already closed");
    }
}
