import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserView extends JFrame {
    private User user;
    private DefaultListModel<String> followingModel;
    private DefaultListModel<String> feedModel;

    public UserView(User user) {
        this.user = user;
        setTitle("User View - " + user.getId());
        setSize(400, 500);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(5, 1, 10, 10));

        // Following section
        JPanel followingPanel = new JPanel();
        followingPanel.setLayout(new BorderLayout());

        JTextField followUserIdField = new JTextField();
        JButton followButton = new JButton("Follow User");
        followButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userIdToFollow = followUserIdField.getText();
                User userToFollow = UserDatabase.getUserById(userIdToFollow);
                if (userToFollow != null) {
                    user.follow(userToFollow);
                    followingModel.addElement(userToFollow.getId());
                }
            }
        });

        followingPanel.add(new JLabel("User Id"), BorderLayout.NORTH);
        followingPanel.add(followUserIdField, BorderLayout.CENTER);
        followingPanel.add(followButton, BorderLayout.EAST);

        followingModel = new DefaultListModel<>();
        JList<String> followingList = new JList<>(followingModel);
        JScrollPane followingView = new JScrollPane(followingList);

        JPanel currentFollowingPanel = new JPanel();
        currentFollowingPanel.setLayout(new BorderLayout());
        currentFollowingPanel.add(new JLabel("List View (Current Following)"), BorderLayout.NORTH);
        currentFollowingPanel.add(followingView, BorderLayout.CENTER);

        mainPanel.add(followingPanel);
        mainPanel.add(currentFollowingPanel);

        // Tweet section
        JPanel tweetPanel = new JPanel();
        tweetPanel.setLayout(new BorderLayout());

        JTextField tweetField = new JTextField();
        JButton tweetButton = new JButton("Post Tweet");
        tweetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = tweetField.getText();
                if (!message.isEmpty()) {
                    user.postMessage(message);
                    feedModel.addElement(user.getId() + ": " + message);
                }
            }
        });

        tweetPanel.add(new JLabel("Tweet Message"), BorderLayout.NORTH);
        tweetPanel.add(tweetField, BorderLayout.CENTER);
        tweetPanel.add(tweetButton, BorderLayout.EAST);

        mainPanel.add(tweetPanel);

        // News feed section
        feedModel = new DefaultListModel<>();
        JList<String> newsFeedList = new JList<>(feedModel);
        JScrollPane newsFeedView = new JScrollPane(newsFeedList);

        JPanel newsFeedPanel = new JPanel();
        newsFeedPanel.setLayout(new BorderLayout());
        newsFeedPanel.add(new JLabel("List View (News Feed)"), BorderLayout.NORTH);
        newsFeedPanel.add(newsFeedView, BorderLayout.CENTER);

        mainPanel.add(newsFeedPanel);

        add(mainPanel, BorderLayout.CENTER);

        updateFollowingList();
        updateNewsFeed();
    }

    private void updateFollowingList() {
        followingModel.clear();
        for (User u : user.getFollowing()) {
            followingModel.addElement(u.getId());
        }
    }

    private void updateNewsFeed() {
        feedModel.clear();
        for (String message : user.getNewsFeed()) {
            feedModel.addElement(message);
        }
    }
}


