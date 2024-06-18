import java.util.List;

public abstract class UserComponent extends Observable {
    public void add(UserComponent userComponent) {
        throw new UnsupportedOperationException();
    }

    public void remove(UserComponent userComponent) {
        throw new UnsupportedOperationException();
    }

    public UserComponent getChild(int i) {
        throw new UnsupportedOperationException();
    }

    public String getId() {
        throw new UnsupportedOperationException();
    }

    public List<User> getFollowers() {
        throw new UnsupportedOperationException();
    }

    public List<User> getFollowing() {
        throw new UnsupportedOperationException();
    }

    public List<String> getNewsFeed() {
        throw new UnsupportedOperationException();
    }

    public void follow(UserComponent userComponent) {
        throw new UnsupportedOperationException();
    }

    public void postMessage(String message) {
        throw new UnsupportedOperationException();
    }

    public void accept(Visitor visitor) {
        throw new UnsupportedOperationException();
    }

    public static int getUserCount() {
        throw new UnsupportedOperationException();
    }

    public static int getGroupCount() {
        throw new UnsupportedOperationException();
    }

    @Override
    public String toString() {
        throw new UnsupportedOperationException();
    }
}

