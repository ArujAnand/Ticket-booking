package ticket.booking.entitites;

import java.util.List;

public class User {
    private String name;
    private String password;
    private String hashPassword;
    private List<Ticket> ticketsBooked;
    private String userId;

    public User(String name, String password, String hashPassword, List<Ticket> ticketsBooked, String userId) {
        this.name = name;
        this.password = password;
        this.hashPassword = hashPassword;
        this.ticketsBooked = ticketsBooked;
        this.userId = userId;
    }

    public User() {}

    //getters for various fields
    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public List<Ticket> getTicketsBooked() {
        return this.ticketsBooked;
    }

//    Get details about the tickets booked by the user
    public void printTickets() {
        for (Ticket ticket: ticketsBooked) {
            System.out.println(ticket.getTicketInfo());
        }
    }

    //setters for various fields
    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public void setTicketsBooked(List<Ticket> ticketsBooked) {
        this.ticketsBooked = ticketsBooked;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
