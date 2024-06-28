import java.text.SimpleDateFormat;
import java.util.*;

public class UserGroup extends UserComponent {
    private static int groupCount = 0;
    private String id;
    private List<UserComponent> userComponents;
    private long creationTime;

    public UserGroup(String id) {
        this.id = id;
        this.userComponents = new ArrayList<>();
        this.creationTime=System.currentTimeMillis(); 
        groupCount++;
        printCreationDetails();
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void add(UserComponent userComponent) {
        userComponents.add(userComponent);
    }

    @Override
    public void remove(UserComponent userComponent) {
        userComponents.remove(userComponent);
    }

    @Override
    public UserComponent getChild(int i) {
        return userComponents.get(i);
    }

    public static int getGroupCount() {
        return groupCount;
    }
    public List<UserComponent> getUserComponents() {
        return userComponents;
    }

    @Override
    public void accept(Visitor visitor) {
        for (UserComponent userComponent : userComponents) {
            userComponent.accept(visitor);
        }
    }
    public long getCreationTime() {
        return creationTime;
    }

    @Override
    public String toString() {
        return id;
    }

    private void printCreationDetails() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String creationTimeString = sdf.format(new Date(creationTime));
        System.out.println("Group Created: " + id + ", Creation Time: " + creationTimeString);
    }
}

