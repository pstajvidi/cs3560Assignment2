import java.util.HashMap;
import java.util.Map;

public class UserDatabase {
    private static Map<String, User> users = new HashMap<>();

    public static void addUser(User user) {
        users.put(user.getId(), user);
    }

    public static User getUserById(String userId) {
        return users.get(userId);
    }
}


