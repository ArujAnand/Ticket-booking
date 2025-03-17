package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entities.*;
import ticket.booking.utilities.UserServiceUtil;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.time.LocalDateTime;

public class UserBookingService {
    private User user;
    private List<User> userList;

    private ObjectMapper ObjectMapper = new ObjectMapper();

    private static final String USERS_PATH = "C:\\Users\\Dell\\Documents\\Java\\Projects\\IRCTC\\src\\main\\java\\ticket\\booking\\localDB\\users.json";

    /**
     * Default Constructor which
     * loads the users in the memory from DB on object creation of type UserBookingService
     * @throws IOException If an error occurs in {@link #loadUsers()} function while loading the users stored in the DB
     */
    public UserBookingService() throws IOException{
        this.userList = loadUsers();

/*
        to check data being read from JSON
        String jsonString = ObjectMapper.writeValueAsString(userList);
        System.out.println("Serialized JSON:\n" + jsonString);
*/
    }

    /**
     * Parameterised Constructor
     * Takes parameter of type {@code User} and sets it to current user and initialises userlist from the
     * database
     * @param user The user set to current user.
     * @throws IOException If an error occurs while fetching the user list from the database.
     */
    public UserBookingService (User user) throws IOException {
        this.user = user;
        //might be useless; check later
        File users = new File(USERS_PATH);
        this.userList = loadUsers();
    }

    /**
     * load users from the DB
     * @return List<User></User> The list of class users - essentially a list of users stored in the DB
     * @throws IOException If an error occurs while fetching the user list from the database.
     */
    public List<User> loadUsers() throws IOException{
        File users = new File(USERS_PATH);

//        System.out.println("Users loaded using function loadUsers");

        ObjectMapper.registerModule(new JavaTimeModule()); //Register module to handle LocalDateTime
        return ObjectMapper.readValue(users, new TypeReference<List<User>> () {});
    }


/* Check if the current user is already logged in or exists in the database */
    public Boolean loginUser() {
        Optional<User> foundUser = userList.stream()
                .filter(user1 -> user1.getName().equalsIgnoreCase(user.getName())
                        && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashPassword()))
                .findFirst();

        if(foundUser.isPresent()) {
            //assign current user to user that was found in the DB
            this.user = foundUser.get();
            return Boolean.TRUE;
        }
        return Boolean.FALSE;
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

    /**
     * saves user list to JSON file
     * @throws IOException If an error occurs while saving user list to file
     */
    private void saveUserListToFile() throws IOException {
        File usersFile = new File(USERS_PATH);
        ObjectMapper.registerModule(new JavaTimeModule());
        ObjectMapper.writeValue(usersFile, userList);
    }

    /**
     * Prints user bookings by calling {@link ticket.booking.entities.User#printTickets()} function of the User class
     */
    public void fetchBooking() {
        user.printTickets();
//        System.out.println(user.getTicketsBooked().size());
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

    /**
     * @param source source station
     * @param destination destination station
     * @return List of trains going from source to destination
     */
    public List<Train> getTrains(String source, String destination) {
        try {
            TrainService trainService = new TrainService(); //loads train list in memory
            return trainService.searchTrains(source, destination); //actual search begins
        } catch (IOException e) {
            System.out.println("Unable to load trains : " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Makes seat booking by checking seat availability and propagates those changes to DB
     * @param seatNo seat selected
     * @param trainNo train number of the train selected
     * @return Returns TRUE if selected seat is not booked, else return FALSE
     */
    public boolean isValidSelection (int seatNo, int trainNo, String source, String destination, Train selected) {
        try {
            TrainService bookSeats = new TrainService();
            if (bookSeats.bookSeat(seatNo, trainNo)) {
                Ticket newTicket = new Ticket(UUID.randomUUID().toString(), user.getUserId(), source, destination, LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES), selected);
                try {
                    saveUserTicket(newTicket);
                } catch (Exception e) {
                    System.out.println("Unable to update user booked tickets" + e.getMessage());
                    return false;
                }
                return true;
            } else {
                return false;
            }
        } catch (IOException e) {
            System.out.println("Unable to save train seat details : " + e.getMessage());
            return Boolean.FALSE;
        }
    }

    /**
     * Finds and updates the user's tickets
     * @param newTicket new ticket booked by the user
     */
    private void saveUserTicket(Ticket newTicket) throws IOException {
        user.getTicketsBooked().add(newTicket);
        saveUserListToFile();
        /*
        * 1. Find user in the userlist
        * 2. Add newTicket to the ticketlist
        * 3. Call saverUserListToFile() which updates the users
        * */
    }
}
