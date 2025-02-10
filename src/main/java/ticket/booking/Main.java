package ticket.booking;

import ticket.booking.entitites.Ticket;
import ticket.booking.entitites.User;
import ticket.booking.services.UserBookingService;
import ticket.booking.utilities.UserServiceUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.UUID;

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
        } catch (IOException e){
            System.out.println("Some error with loading file");
            System.out.println(e.getMessage());
            return;
        }

        while (option != 7) {
            System.out.println("Choose an option");
            option = sc.nextInt();
            switch (option) {
                case 1:
                    System.out.println("Enter name");
                    String name = sc.next();
                    System.out.println("Enter password");
                    String pass = sc.next();
                    User newUser = new User(name, pass, UserServiceUtil.hashPassword(pass), new ArrayList<Ticket>(), UUID.randomUUID().toString());
                    if(userBookingService.signUp(newUser)) {
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
                    if(userBookingService.loginUser()) {
                        System.out.println("Log-in Successful!");
                    } else {
                        System.out.println("User not found!");
                        System.out.println("Username or Password might be incorrect");
                    }
                    return;
            }
        }
    }
}