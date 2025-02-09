package ticket.booking.entitites;

import java.util.Date;

public class Ticket {
    private String ticketId;
    private String userId;
    private String source;
    private String destination;
    private Date dateOfTravel;
    private Train train;                                  // Train class is in same package hence can be accessed without importing

    public String getTicketInfo () {
        return String.format("Ticket Source: %s   Ticket Destination: %s   Date Of Travel: %Tr",this.source, this.destination, this.dateOfTravel);
    }
}
