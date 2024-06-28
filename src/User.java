import java.util.ArrayList;
import java.util.List;

public class User extends UserComponent {
    private static int userCount = 0;
    private static int messageCount = 0;
    private static User lastUpdateUser = null;
    private String id;
    private List<User> followers;
    private List<User> following;
    private List<String> newsFeed;
    private long creationTime;
    private long lastUpdateTime;

    public User(String id) {
        this.id = id;
        this.followers = new ArrayList<>();
        this.following = new ArrayList<>();
        this.newsFeed = new ArrayList<>();
        this.creationTime = System.currentTimeMillis();
        this.lastUpdateTime = creationTime;
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
        String formattedMessage = id + ": " + message;
        newsFeed.add(formattedMessage);
        messageCount++;
        lastUpdateTime = System.currentTimeMillis();
        lastUpdateUser = this;
        notifyObservers(formattedMessage);
        updateFollowersLastUpdateTime();
    }


    private void updateNewsFeed(String message) {
        if (!newsFeed.contains(message)) {
            newsFeed.add(message);
            lastUpdateTime = System.currentTimeMillis();
            lastUpdateUser = this;
        }
    }
    private void updateFollowersLastUpdateTime() {
        for (User follower : followers) {
            follower.setLastUpdateTime(System.currentTimeMillis());
            lastUpdateUser = this;
        }
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public static int getUserCount() {
        return userCount;
    }

    public static int getMessageCount() {
        return messageCount;
    }
    public long getCreationTime() {
        return creationTime;
    }

    public long getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(long lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public static User getLastUpdateUser() {
        return lastUpdateUser;
    }


    @Override
    public String toString() {
        return id;
    }
}

