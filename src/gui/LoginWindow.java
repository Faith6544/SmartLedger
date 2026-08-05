package gui;

import database.UserDAO;
import model.User;
import javax.swing.*;
import java.awt.*;

public class LoginWindow extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JLabel statusLabel;
    private UserDAO userDAO = new UserDAO();

    public LoginWindow() {
        setTitle("SmartLedger — Login");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(30, 40, 30, 40));
        panel.setBackground(new Color(30, 30, 30));

        // Title
        JLabel title = new JLabel("SmartLedger");
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        title.setForeground(new Color(76, 175, 80));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(title);

        JLabel subtitle = new JLabel("Record-keeping made simple");
        subtitle.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subtitle.setForeground(Color.GRAY);
        subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(subtitle);

        panel.add(Box.createVerticalStrut(25));

        // Username
        JLabel userLabel = new JLabel("Username");
        userLabel.setForeground(Color.WHITE);
        userLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(userLabel);
        panel.add(Box.createVerticalStrut(5));

        usernameField = new JTextField(20);
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(usernameField);

        panel.add(Box.createVerticalStrut(15));

        // Password
        JLabel passLabel = new JLabel("Password");
        passLabel.setForeground(Color.WHITE);
        passLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        panel.add(passLabel);
        panel.add(Box.createVerticalStrut(5));

        passwordField = new JPasswordField(20);
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 35));
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(passwordField);

        panel.add(Box.createVerticalStrut(20));

        // Buttons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        buttonPanel.setBackground(new Color(30, 30, 30));

        JButton loginBtn = new JButton("Login");
        loginBtn.setBackground(new Color(76, 175, 80));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        loginBtn.addActionListener(e -> login());

        JButton signupBtn = new JButton("Sign Up");
        signupBtn.setBackground(new Color(33, 150, 243));
        signupBtn.setForeground(Color.WHITE);
        signupBtn.setFocusPainted(false);
        signupBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        signupBtn.addActionListener(e -> signup());

        buttonPanel.add(loginBtn);
        buttonPanel.add(signupBtn);
        panel.add(buttonPanel);

        panel.add(Box.createVerticalStrut(15));

        // Status label
        statusLabel = new JLabel(" ");
        statusLabel.setForeground(new Color(244, 67, 54));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        panel.add(statusLabel);

        // Enter key triggers login
        passwordField.addActionListener(e -> login());

        add(panel);
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please enter both username and password.");
            return;
        }

        User user = userDAO.login(username, password);
        if (user != null) {
            dispose(); // Close login window
            new ChatWindow(user).setVisible(true);
        } else {
            statusLabel.setText("Invalid username or password.");
        }
    }

    private void signup() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            statusLabel.setText("Please enter both username and password.");
            return;
        }

        if (password.length() < 4) {
            statusLabel.setText("Password must be at least 4 characters.");
            return;
        }

        User user = new User(username, password);
        if (userDAO.createUser(user)) {
            statusLabel.setForeground(new Color(76, 175, 80));
            statusLabel.setText("Account created! You can now log in.");
        } else {
            statusLabel.setText("Username already taken. Try another.");
        }
    }
}
