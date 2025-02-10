package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entitites.*;
import ticket.booking.utilities.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.time.LocalDateTime;

/* Get user data from local DB JSON using Jackson Object Mapper */
public class UserBookingService {
    private User user;
    private List<User> userList;

    private ObjectMapper ObjectMapper = new ObjectMapper();
    private static final String USERS_PATH = Paths.get("localDB", "users.json").toString();
    public UserBookingService (User user) throws IOException {
        this.user = user;
        File users = new File(USERS_PATH);
        userList = ObjectMapper.readValue(users, new TypeReference<List<User>> () {});
    }

/* Check if the current user is already logged in / exists in the database */
    public Boolean loginUser() {
        Optional<User> foundUser = userList.stream()
                .filter(user1 -> user1.getName().equalsIgnoreCase(user.getName())
                        && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashPassword()))
                .findFirst();
        return foundUser.isPresent();
    }

/* signup function */
    public Boolean signUp(User newUser) {
        try {
            userList.add(newUser);
            saveUserListToFile();
            return Boolean.TRUE;
        } catch (IOException exception) {
            return Boolean.FALSE;
        }
    }

/* add new users to file */
    private void saveUserListToFile() throws IOException {
        File usersFile = new File(USERS_PATH);
        ObjectMapper.writeValue(usersFile, userList);
    }

/* get user bookings */
    public void fetchBooking() {
        user.printTickets();
    }

/* take ticket ID, if date of travel after current date -> cancel booking */
    public boolean cancelBooking (String ticketID) {

        List<Ticket> tkt_b = user.getTicketsBooked();
        Iterator<Ticket> iterator = tkt_b.iterator();

        while (iterator.hasNext()) {
            Ticket t = iterator.next();
            if (t.getDateOfTravel().isAfter(LocalDateTime.now()) && t.getTicketId().equalsIgnoreCase(ticketID)) {
                iterator.remove();
                System.out.println("Booking Cancelled!");
                try {
                    saveUserListToFile(); // Persist changes
                    return true;
                } catch (IOException e) {
                    System.out.println("Unable to save changes: " + e.getMessage());
                    return false; // Handle failure
                }
            }
        }

        System.out.println("No such upcoming bookings exist");
        return false;
    }
}
