package ticket.booking.entitites;

import java.util.Date;

public class Ticket {
    private String ticketId;
    private String userId;
    private String source;
    private String destination;
    private Date dateOfTravel;
    private Train train;                                  // Train class is in same package hence can be accessed without importing

    public Ticket(String ticketId, String userId, String source, String destination, Date dateOfTravel, Train train) {
        this.ticketId = ticketId;
        this.userId = userId;
        this.source = source;
        this.destination = destination;
        this.dateOfTravel = dateOfTravel;
        this.train = train;
    }

//    Getter
    public String getTicketId() {
        return ticketId;
    }

    public String getUserId() {
        return userId;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public Date getDateOfTravel() {
        return dateOfTravel;
    }

//    Setter
    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public void setDateOfTravel(Date dateOfTravel) {
        this.dateOfTravel = dateOfTravel;
    }

    public void setTrain(Train train) {
        this.train = train;
    }

    public Train getTrain() {
        return train;
    }

    //invoked from the User class to get info about a particular ticket
    public String getTicketInfo () {
        return String.format("User ID: %s  Ticket ID: %s Ticket Source: %s   Ticket Destination: %s   Date Of Travel: %Tr",
                this.userId,
                this.ticketId,
                this.source,
                this.destination,
                this.dateOfTravel);
    }
}
