import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminControlPanel extends JFrame {
    private static AdminControlPanel instance = null;
    private JTree tree;
    private DefaultTreeModel treeModel;
    private DefaultMutableTreeNode rootNode;

    private AdminControlPanel() {
        setTitle("Admin Control Panel");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        rootNode = new DefaultMutableTreeNode("Root");
        treeModel = new DefaultTreeModel(rootNode);
        tree = new JTree(treeModel);
        JScrollPane treeView = new JScrollPane(tree);

        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(6, 2, 10, 10));
        
        JTextField userIdField = new JTextField();
        JButton addUserButton = new JButton("Add User");
        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String userId = userIdField.getText();
                if (!userId.isEmpty()) {
                    User newUser = new User(userId);
                    UserDatabase.addUser(newUser);
                    DefaultMutableTreeNode userNode = new DefaultMutableTreeNode(newUser);
                    rootNode.add(userNode);
                    treeModel.reload();
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
                    DefaultMutableTreeNode groupNode = new DefaultMutableTreeNode(newGroup);
                    rootNode.add(groupNode);
                    treeModel.reload();
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
                int messageCount = Message.getMessageCount();
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
                        }
                    }
                });
                double positivePercentage = visitor.getPositivePercentage();
                JOptionPane.showMessageDialog(null, "Positive Messages Percentage: " + positivePercentage + "%");
            }
        });

        controlPanel.add(new JLabel("TextArea - User Id"));
        controlPanel.add(userIdField);
        controlPanel.add(addUserButton);
        controlPanel.add(new JLabel("TextArea - Group Id"));
        controlPanel.add(groupIdField);
        controlPanel.add(addGroupButton);
        controlPanel.add(openUserViewButton);
        controlPanel.add(showUserTotalButton);
        controlPanel.add(showGroupTotalButton);
        controlPanel.add(showMessageTotalButton);
        controlPanel.add(showPositivePercentageButton);
        
        add(treeView, BorderLayout.CENTER);
        add(controlPanel, BorderLayout.EAST);
    }

    public static AdminControlPanel getInstance() {
        if (instance == null) {
            instance = new AdminControlPanel();
        }
        return instance;
    }
}

