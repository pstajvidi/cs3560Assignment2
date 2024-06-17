import java.util.Arrays;
import java.util.List;

public class PositiveWordsVisitor implements Visitor {
    private List<String> positiveWords = Arrays.asList("good", "great", "excellent", "awesome", "fantastic");
    private int positiveCount = 0;
    private int totalCount = 0;

    @Override
    public void visit(User user) {
        for (String message : user.getNewsFeed()) {
            totalCount++;
            for (String word : positiveWords) {
                if (message.contains(word)) {
                    positiveCount++;
                    break;
                }
            }
        }
    }

    public double getPositivePercentage() {
        if (totalCount == 0) {
            return 0;
        }
        return (positiveCount / (double) totalCount) * 100;
    }
}

