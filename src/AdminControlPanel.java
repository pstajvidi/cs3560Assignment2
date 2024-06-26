import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

public class AdminControlPanel extends JFrame {
    private static AdminControlPanel instance = null;
    private JTree tree;
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode rootNode;

    //private constrocture to no let other instantiations
    private AdminControlPanel() {
        setTitle("Admin Control Panel");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        rootNode = new DefaultMutableTreeNode("Root");
        treeModel = new DefaultTreeModel(rootNode);
        tree = new JTree(treeModel);
        JScrollPane treeView = new JScrollPane(tree);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(5, 3, 15, 15));
        
        JTextField userIdField = new JTextField();
        JButton addUserButton = new JButton("Add User");
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = userIdField.getText();
                if (!userId.isEmpty()) {
                    User newUser = new User(userId);
                    UserDatabase.addUser(newUser);
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                    if (selectedNode == null || selectedNode == rootNode|| selectedNode.getUserObject() instanceof UserGroup) {
                        if (selectedNode == null) {
                            selectedNode = rootNode;
                        }
                        selectedNode.add(new DefaultMutableTreeNode(newUser));
                        treeModel.reload();
                    } else {
                        JOptionPane.showMessageDialog(null, "You can only add users to a group or the root.");
                    }
                }
            }
        });
        
        
        JTextField groupIdField = new JTextField();
        JButton addGroupButton = new JButton("Add Group");
        addGroupButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String groupId = groupIdField.getText();
                if (!groupId.isEmpty()) {
                    UserGroup newGroup = new UserGroup(groupId);
                    DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                    if (selectedNode == null || selectedNode==rootNode|| selectedNode.getUserObject() instanceof UserGroup) {
                        if (selectedNode == null) {
                            selectedNode = rootNode;
                        }
                        selectedNode.add(new DefaultMutableTreeNode(newGroup));
                        treeModel.reload();
                    } else {
                        JOptionPane.showMessageDialog(null, "You can only add groups to a group or the root.");
                    }
                }
            }
        });


        JButton openUserViewButton = new JButton("Open User View");
        openUserViewButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DefaultMutableTreeNode selectedNode = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
                if (selectedNode != null && selectedNode.getUserObject() instanceof User) {
                    User selectedUser = (User) selectedNode.getUserObject();
                    new UserView(selectedUser).setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "You can only open the view for a user.");
                }
            }
        });


        JButton showUserTotalButton = new JButton("Show User Total");
        showUserTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int userCount = User.getUserCount();
                JOptionPane.showMessageDialog(null, "Total Users: " + userCount);
            }
        });

        JButton showGroupTotalButton = new JButton("Show Group Total");
        showGroupTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int groupCount = UserGroup.getGroupCount();
                JOptionPane.showMessageDialog(null, "Total Groups: " + groupCount);
            }
        });

        JButton showMessageTotalButton = new JButton("Show Messages Total");
        showMessageTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int messageCount = User.getMessageCount();
                JOptionPane.showMessageDialog(null, "Total Messages: " + messageCount);
            }
        });

        JButton showPositivePercentageButton = new JButton("Show Positive Percentage");
        showPositivePercentageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PositiveWordsVisitor visitor = new PositiveWordsVisitor();
                rootNode.preorderEnumeration().asIterator().forEachRemaining(node -> {
                    if (node instanceof DefaultMutableTreeNode) {
                        Object obj = ((DefaultMutableTreeNode) node).getUserObject();
                        if (obj instanceof User) {
                            ((User) obj).accept(visitor);
                        } else if (obj instanceof UserGroup) {
                            ((UserGroup) obj).accept(visitor);
                        }
                    }
                });
                double positivePercentage = visitor.getPositivePercentage();
                JOptionPane.showMessageDialog(null, "Positive Messages Percentage: " + positivePercentage + "%");
            }
        });

        JButton validateIdsButton = new JButton("Validate IDs");
        validateIdsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateIds();
            }
        });

        
        JButton showLastUpdateUserButton = new JButton("Show Last Update User");
        showLastUpdateUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showLastUpdateUser();
            }
        });

        controlPanel.add(new JLabel("User Id"));
        controlPanel.add(userIdField);
        controlPanel.add(addUserButton);
        controlPanel.add(new JLabel("Group Id"));
        controlPanel.add(groupIdField);
        controlPanel.add(addGroupButton);
        controlPanel.add(openUserViewButton);
        controlPanel.add(showUserTotalButton);
        controlPanel.add(showGroupTotalButton);
        controlPanel.add(showMessageTotalButton);
        controlPanel.add(showPositivePercentageButton);
        controlPanel.add(validateIdsButton);
        controlPanel.add(showLastUpdateUserButton);
        
        add(treeView, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.EAST);
    }

    //public method that instaticats AdminControlPanel once
    public static AdminControlPanel getInstance() {
        if (instance == null) {
            
            instance = new AdminControlPanel();
        }
        return instance;
    }

   // Method to validate user and group IDs
   private void validateIds() {
    Set<String> ids = new HashSet<>();
    StringBuilder invalidIds = new StringBuilder();

    rootNode.preorderEnumeration().asIterator().forEachRemaining(node -> {
        if (node instanceof DefaultMutableTreeNode) {
            Object obj = ((DefaultMutableTreeNode) node).getUserObject();
            if (obj instanceof User) {
                User user = (User) obj;
                String userId = user.getId();
                if (ids.contains(userId) || userId.contains(" ")) {
                    invalidIds.append("User ID: ").append(userId).append("\n");
                } else {
                    ids.add(userId);
                }
            } else if (obj instanceof UserGroup) {
                UserGroup group = (UserGroup) obj;
                String groupId = group.getId();
                if (ids.contains(groupId) || groupId.contains(" ")) {
                    invalidIds.append("Group ID: ").append(groupId).append("\n");
                } else {
                    ids.add(groupId);
                }
            }
        }
    });

    if (invalidIds.length() > 0) {
        JOptionPane.showMessageDialog(this, "Invalid IDs:\n" + invalidIds.toString(), "Validation Error", JOptionPane.ERROR_MESSAGE);
    } else {
        JOptionPane.showMessageDialog(this, "All IDs are valid.", "Validation Successful", JOptionPane.INFORMATION_MESSAGE);
    }
}

    // Method to show the user who made the last update
    private void showLastUpdateUser() {
        User lastUpdateUser = User.getLastUpdateUser();
        if (lastUpdateUser != null) {
            JOptionPane.showMessageDialog(this, "User with Last Update: " + lastUpdateUser.getId(), "Last Update User", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No users have posted updates.", "Last Update User", JOptionPane.INFORMATION_MESSAGE);
        }
    }
}
