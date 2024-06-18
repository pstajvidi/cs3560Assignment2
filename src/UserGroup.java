import java.util.ArrayList;
import java.util.List;

public class UserGroup extends UserComponent {
    private static int groupCount = 0;
    private String id;
    private List<UserComponent> userComponents;

    public UserGroup(String id) {
        this.id = id;
        this.userComponents = new ArrayList<>();
        groupCount++;
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

    @Override
    public void accept(Visitor visitor) {
        for (UserComponent userComponent : userComponents) {
            userComponent.accept(visitor);
        }
    }

    @Override
    public String toString() {
        return id;
    }
}

