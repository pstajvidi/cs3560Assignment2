import java.util.ArrayList;
import java.util.List;

public class User extends UserComponent {
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

    @Override
    public String getId() {
        return id;
    }

    @Override
    public List<User> getFollowers() {
        return followers;
    }

    @Override
    public List<User> getFollowing() {
        return following;
    }

    @Override
    public List<String> getNewsFeed() {
        return newsFeed;
    }

    @Override
    public void follow(UserComponent userComponent) {
        if (userComponent instanceof User) {
            User user = (User) userComponent;
            if (!following.contains(user)) {
                following.add(user);
                user.addFollower(this);
                user.addObserver(this::updateNewsFeed);
            }
        }
    }

    private void addFollower(User user) {
        if (!followers.contains(user)) {
            followers.add(user);
        }
    }

    @Override
    public void postMessage(String message) {
        newsFeed.add(message);
        notifyObservers(message);
    }

    private void updateNewsFeed(String message) {
        if (!newsFeed.contains(message)) {
            newsFeed.add(message);
        }
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public static int getUserCount() {
        return userCount;
    }

    @Override
    public String toString() {
        return id;
    }
}
