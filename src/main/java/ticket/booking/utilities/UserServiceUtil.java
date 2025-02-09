package ticket.booking.utilities;

import org.mindrot.jbcrypt.BCrypt;

public class UserServiceUtil {
/*    as these are helper functions that perform common tasks, making them static saves
      the overhead of object creation and makes them easier to access
 */

    public static String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    public static boolean checkPassword(String plainPassword, String hashedPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
