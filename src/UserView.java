import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserView extends JFrame {
    private User user;
    private DefaultListModel<String> feedModel;

    public UserView(User user) {
        this.user = user;
        setTitle("User View - " + user.getId());
        setSize(400, 300);

        feedModel = new DefaultListModel<>();
        JList<String> newsFeedList = new JList<>(feedModel);
        JScrollPane newsFeedView = new JScrollPane(newsFeedList);

        JPanel controlPanel = new JPanel();
        JTextField followUserIdField = new JTextField(15);
        JButton followButton = new JButton("Follow");
        followButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userIdToFollow = followUserIdField.getText();
                // logic to follow a user by ID, assuming a getUserById method exists
                User userToFollow = getUserById(userIdToFollow); // implement this method
                if (userToFollow != null) {
                    user.follow(userToFollow);
                }
            }
        });

        JTextField tweetField = new JTextField(20);
        JButton tweetButton = new JButton("Post Tweet");
        tweetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = tweetField.getText();
                if (!message.isEmpty()) {
                    user.postMessage(message);
                    feedModel.addElement(message);
                }
            }
        });

        controlPanel.add(new JLabel("Follow User ID:"));
        controlPanel.add(followUserIdField);
        controlPanel.add(followButton);
        controlPanel.add(new JLabel("Post Tweet:"));
        controlPanel.add(tweetField);
        controlPanel.add(tweetButton);

        add(newsFeedView, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.SOUTH);

        updateNewsFeed();
    }

    private void updateNewsFeed() {
        feedModel.clear();
        for (String message : user.getNewsFeed()) {
            feedModel.addElement(message);
        }
    }

    // Helper method to retrieve user by ID, this should be implemented accordingly
    private User getUserById(String userId) {
        // Implement the logic to retrieve user by ID
        return null;
    }
}

