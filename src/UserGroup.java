import java.util.ArrayList;
import java.util.List;

public class UserGroup {
    private static int groupCount = 0;
    private String id;
    private List<User> users;
    private List<UserGroup> subGroups;

    public UserGroup(String id) {
        this.id = id;
        this.users = new ArrayList<>();
        this.subGroups = new ArrayList<>();
        groupCount++;
    }

    public String getId() {
        return id;
    }

    public void addUser(User user) {
        if (!users.contains(user)) {
            users.add(user);
        }
    }

    public void addSubGroup(UserGroup group) {
        if (!subGroups.contains(group)) {
            subGroups.add(group);
        }
    }

    public static int getGroupCount() {
        return groupCount;
    }
}

