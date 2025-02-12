package ticket.booking;

import ticket.booking.entities.Ticket;
import ticket.booking.entities.Train;
import ticket.booking.entities.User;
import ticket.booking.services.UserBookingService;
import ticket.booking.utilities.UserServiceUtil;

import java.io.IOException;
import java.util.*;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.

        System.out.println("Hello and welcome!");
        System.out.println("Running Train Booking System");

        Scanner sc = new Scanner(System.in);
        int option = 0;

        UserBookingService userBookingService;
        try {
            userBookingService = new UserBookingService();
        } catch (IOException e) {
            System.out.println("Some error with loading file");
            System.out.println(e.getMessage());
            return;
        }

        while (option != 7) {
            System.out.println("Choose an option");
            System.out.println("1. Sign Up");
            System.out.println("2. Login");
            System.out.println("3. Fetch Bookings");
            System.out.println("4. Search Trains");
            System.out.println("5. Book a seat");
            System.out.println("6. Cancel my Booking");
            System.out.println("7. Exit the App");

            option = sc.nextInt();
            switch (option) {
                case 1:
                    System.out.println("Enter name");
                    String name = sc.next();
                    System.out.println("Enter password");
                    String pass = sc.next();
                    User newUser = new User(name, pass, UserServiceUtil.hashPassword(pass), new ArrayList<Ticket>(), UUID.randomUUID().toString());
                    if (userBookingService.signUp(newUser)) {
                        System.out.println("Sign-Up Successful!");
                    } else {
                        System.out.println("Unable to Signup!");
                    }
                    break;
                case 2:
                    System.out.println("Enter name to log-in");
                    name = sc.next();
                    System.out.println("Enter password to log-in");
                    pass = sc.next();
                    User toLogin = new User(name, pass, UserServiceUtil.hashPassword(pass), new ArrayList<Ticket>(), UUID.randomUUID().toString());
                    try {
                        userBookingService = new UserBookingService(toLogin);
                    } catch (IOException e) {
                        System.out.println("Error " + e.getMessage());
                    }
                    if (userBookingService.loginUser()) {
                        System.out.println("Log-in Successful!");
                    } else {
                        System.out.println("User not found!");
                        System.out.println("Username or Password might be incorrect");
                    }
                    break;
                case 3:
                    try {
                        userBookingService.fetchBooking();
                    } catch (NullPointerException e) {
                        System.out.println("Please login first");
                        break;
                    }
                    break;
                case 4:
                    System.out.println("Enter your source station");
                    String source = sc.next();
                    System.out.println("Enter your destination station");
                    String destination = sc.next();
                    List<Train> trains = userBookingService.getTrains(source, destination);
                    int index = 1;
                    for (Train t: trains) {
                        System.out.println(index + " Train id : " + t.getTrainId());
//                        for (Map.entry<String, String> entry: t.getStationTimes().entrySet()) {
//                            System.out.println("station " + entry.getKey() + " time: " + entry.getValue());
//                        }
                    }

                    System.out.println("Select a train by typing 1,2,3...");
                    Train trainSelectedForBooking = trains.get(sc.nextInt());
                    break;
                default:
                    System.out.println("Invalid Option");
            }
        }
    }
}
      