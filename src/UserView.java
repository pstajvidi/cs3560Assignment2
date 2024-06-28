import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.*;
import java.util.*;

public class UserView extends JFrame {
    private User user;
    private DefaultListModel<String> followingModel;
    private DefaultListModel<String> feedModel;
    private JLabel lastUpdateTimeLabel;


    public UserView(User user) {
        this.user = user;
        setTitle("User View - " + user.getId());
        setSize(600, 500);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 1, 10, 10));

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
        currentFollowingPanel.add(new JLabel("Current Following"), BorderLayout.NORTH);
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
        newsFeedPanel.add(new JLabel("News Feed"), BorderLayout.NORTH);
        newsFeedPanel.add(newsFeedView, BorderLayout.CENTER);

        mainPanel.add(newsFeedPanel);

        // Creation time section
        JPanel creationTimePanel = new JPanel();
        creationTimePanel.setLayout(new BorderLayout());

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String creationTimeString = sdf.format(new Date(user.getCreationTime()));

        creationTimePanel.add(new JLabel("Creation Time: " + creationTimeString), BorderLayout.CENTER);
        
        mainPanel.add(creationTimePanel); 

        // Last update time section
        JPanel lastUpdateTimePanel = new JPanel();
        lastUpdateTimePanel.setLayout(new BorderLayout());

        lastUpdateTimeLabel = new JLabel();
        updateLastUpdateTime();

        lastUpdateTimePanel.add(lastUpdateTimeLabel, BorderLayout.CENTER);

        mainPanel.add(lastUpdateTimePanel);

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
        updateLastUpdateTime();
    }

    private void updateLastUpdateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String lastUpdateTimeString = sdf.format(new Date(user.getLastUpdateTime()));
        lastUpdateTimeLabel.setText("Last Update Time: " + lastUpdateTimeString);
    }
}


