import java.util.ArrayList;
import java.util.List;


public class User extends Observable {
    private static int userCount = 0;
    private String id;
    private List<User> followers;
    private List<User> following;
    private List<String> newsFeed;

    public User(String id) {
        this.id = id;
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
        this.newsFeed = new ArrayList<>();
        userCount++;
    }

    public String getId() {
        return id;
    }

    public List<String> getNewsFeed() {
        return newsFeed;
    }

    public void follow(User user) {
        if (!following.contains(user)) {
            following.add(user);
            user.addFollower(this);
        }
    }

    public void addFollower(User user) {
        if (!followers.contains(user)) {
            followers.add(user);
        }
    }

    public void postMessage(String message) {
        newsFeed.add(message);
        notifyObservers(message);
    }

    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public static int getUserCount() {
        return userCount;
    }
}
