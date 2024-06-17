public class Message {
    private static int messageCount = 0;
    private String content;

    public Message(String content) {
        this.content = content;
        messageCount++;
    }

    public String getContent() {
        return content;
    }

    public static int getMessageCount() {
        return messageCount;
    }
}
