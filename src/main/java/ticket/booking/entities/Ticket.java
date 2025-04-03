package ticket.booking.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;

public class Ticket {
    private String ticketId;
    private String userId;
    private String source;
    private String destination;
    private LocalDateTime dateOfTravel;
    private int trainNo;
    private int seatBooked;

    public Ticket(String ticketId, String userId, String source, String destination, LocalDateTime dateOfTravel, int trainNo, int seatBooked) {
        this.ticketId = ticketId;
        this.userId = userId;
        this.source = source;
        this.destination = destination;
        this.dateOfTravel = dateOfTravel;
        this.trainNo = trainNo;
        this.seatBooked = seatBooked;
    }

    public Ticket() {}

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

    public LocalDateTime getDateOfTravel() {
        return dateOfTravel;
    }

    public int getTrainNo() {
        return trainNo;
    }

    public int getSeatBooked() {
        return seatBooked;
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

    public void setDateOfTravel(LocalDateTime dateOfTravel) {
        this.dateOfTravel = dateOfTravel;
    }

    public void setTrainNo(int trainNo) {
        this.trainNo = trainNo;
    }

    public void setSeatBooked(int seatBooked) {
        this.seatBooked = seatBooked;
    }

    /**
     * Invoked from the User class to get info about a particular ticket that has been booked by the user
     * @return String that contains various ticket details
     */
    @JsonIgnore
    public String getTicketInfo () {
        return String.format("User ID: %s  Ticket ID: %s Ticket Source: %s   Ticket Destination: %s   Date Of Travel: %Tr",
                this.userId,
                this.ticketId,
                this.source,
                this.destination,
                this.dateOfTravel);
    }
}
