package ticket.booking.entities;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private String password;
    private String hashPassword;
    private String userId;
    private List<Ticket> ticketsBooked = new ArrayList<>();

    public User(String name, String password, String hashPassword, List<Ticket> ticketsBooked, String userId) {
        this.name = name;
        this.password = password;
        this.hashPassword = hashPassword;
        this.ticketsBooked = (ticketsBooked == null) ? new ArrayList<>() : ticketsBooked;
        this.userId = userId;
    }

    public User() {}

    /* getters for various fields */
    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public List<Ticket> getTicketsBooked() {
        return this.ticketsBooked;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public String getUserId() {
        return userId;
    }

    /**
     * Setter function for name field of User class, used by Jackson Object Mapper
     * @param name Name of the user registering
     */
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

    /**
     * Prints the details of user's if bookings exist otherwise prints a message
     */
    public void printTickets() {
        if (ticketsBooked.isEmpty()) {
            System.out.println("No bookings exist");
            return;
        }
        System.out.println("Fetching your bookings....");
        for (Ticket ticket: ticketsBooked) {
            System.out.println(ticket.getTicketInfo());
        }
    }
}
