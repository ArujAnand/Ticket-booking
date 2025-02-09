package ticket.booking.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import ticket.booking.entitites.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

//Get user data from local DB JSON using Jackson Object Mapper
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

//Check if the current user is already logged in / exists in the database
    public Boolean loginUser() {
        Optional <User> foundUser = userList.stream().filter(user1 -> user1.getName().equals(user.getName())).findFirst();
        return foundUser.isPresent();
    }

}
