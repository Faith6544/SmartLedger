package gui;

import model.User;
import database.UserDAO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class LoginWindow extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField businessNameField;
    private UserDAO userDAO;

    public LoginWindow() {
        userDAO = new UserDAO();
        
        setTitle("SmartLedger - Login");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel with gradient background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                GradientPaint gp = new GradientPaint(0, 0, new Color(15, 15, 30), 
                                                     0, getHeight(), new Color(30, 30, 60));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 0, 5, 0);

        // Logo/Title
        JLabel titleLabel = new JLabel("📊 SmartLedger", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 28));
        titleLabel.setForeground(new Color(76, 175, 80));
        gbc.insets = new Insets(0, 0, 20, 0);
        mainPanel.add(titleLabel, gbc);

        JLabel subtitleLabel = new JLabel("Record-keeping made simple", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        subtitleLabel.setForeground(new Color(180, 180, 180));
        gbc.insets = new Insets(0, 0, 30, 0);
        mainPanel.add(subtitleLabel, gbc);

        // Username
        gbc.insets = new Insets(5, 0, 2, 0);
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        usernameLabel.setForeground(new Color(200, 200, 200));
        mainPanel.add(usernameLabel, gbc);

        usernameField = new JTextField(20);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField.setBackground(new Color(45, 45, 65));
        usernameField.setForeground(Color.WHITE);
        usernameField.setCaretColor(Color.WHITE);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 80)),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        usernameField.addActionListener(e -> passwordField.requestFocus());
        gbc.insets = new Insets(0, 0, 15, 0);
        mainPanel.add(usernameField, gbc);

        // Password
        gbc.insets = new Insets(5, 0, 2, 0);
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        passwordLabel.setForeground(new Color(200, 200, 200));
        mainPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBackground(new Color(45, 45, 65));
        passwordField.setForeground(Color.WHITE);
        passwordField.setCaretColor(Color.WHITE);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 80)),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        passwordField.addActionListener(e -> login());
        gbc.insets = new Insets(0, 0, 15, 0);
        mainPanel.add(passwordField, gbc);

        // Business Name (optional - for signup)
        gbc.insets = new Insets(5, 0, 2, 0);
        JLabel businessLabel = new JLabel("Business Name (optional)");
        businessLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        businessLabel.setForeground(new Color(200, 200, 200));
        mainPanel.add(businessLabel, gbc);

        businessNameField = new JTextField(20);
        businessNameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        businessNameField.setBackground(new Color(45, 45, 65));
        businessNameField.setForeground(Color.WHITE);
        businessNameField.setCaretColor(Color.WHITE);
        businessNameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 80)),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        gbc.insets = new Insets(0, 0, 20, 0);
        mainPanel.add(businessNameField, gbc);

        // Buttons panel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setOpaque(false);

        JButton loginBtn = new JButton("Login");
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginBtn.setBackground(new Color(76, 175, 80));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setBorderPainted(false);
        loginBtn.setOpaque(true);
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        loginBtn.addActionListener(e -> login());

        JButton signupBtn = new JButton("Sign Up");
        signupBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        signupBtn.setBackground(new Color(60, 60, 80));
        signupBtn.setForeground(Color.WHITE);
        signupBtn.setFocusPainted(false);
        signupBtn.setBorderPainted(false);
        signupBtn.setOpaque(true);
        signupBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        signupBtn.addActionListener(e -> signup());

        buttonPanel.add(loginBtn);
        buttonPanel.add(signupBtn);
        
        gbc.insets = new Insets(10, 0, 10, 0);
        mainPanel.add(buttonPanel, gbc);

        // Forgot password / help link
        JLabel helpLabel = new JLabel("Default: username 'testuser', password 'password'");
        helpLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        helpLabel.setForeground(new Color(120, 120, 140));
        helpLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.insets = new Insets(10, 0, 0, 0);
        mainPanel.add(helpLabel, gbc);

        add(mainPanel);
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // For testing: create a default test user if it doesn't exist
        User testUser = userDAO.findByUsername(username);
        if (testUser == null) {
            // Create test user if not exists
            testUser = new User(username, username + "-token-123", "password");
            userDAO.save(testUser);
            JOptionPane.showMessageDialog(this, 
                "Test user created! Use password: 'password'", 
                "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        // Check password (for testing, accept any password for testuser)
        if (username.equals("testuser") || password.equals(testUser.getPassword())) {
            JOptionPane.showMessageDialog(this, "✅ Login successful!", 
                "Welcome", JOptionPane.INFORMATION_MESSAGE);
            openChatWindow(testUser);
        } else {
            JOptionPane.showMessageDialog(this, "❌ Invalid username or password.", 
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void signup() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String businessName = businessNameField.getText().trim();

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter username and password", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (userDAO.findByUsername(username) != null) {
            JOptionPane.showMessageDialog(this, "❌ Username already exists!", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User newUser = new User(username, username + "-" + System.currentTimeMillis(), password);
        userDAO.save(newUser);
        
        JOptionPane.showMessageDialog(this, "✅ User registered successfully!\n" +
            "Username: " + username + "\nToken: " + newUser.getDashboardToken(), 
            "Success", JOptionPane.INFORMATION_MESSAGE);
        
        openChatWindow(newUser);
    }

    private void openChatWindow(User user) {
        dispose(); // Close login window
        ChatWindow chatWindow = new ChatWindow(user);
        chatWindow.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                e.printStackTrace();
            }
            new LoginWindow().setVisible(true);
        });
    }
}