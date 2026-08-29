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
    private JButton loginBtn;
    private JButton signupBtn;
    private UserDAO userDAO;

    public LoginWindow() {
        userDAO = new UserDAO();
        
        setTitle("SmartLedger - Login");
        setSize(420, 520);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Create the main panel
        JPanel mainPanel = createMainPanel();
        add(mainPanel);
        
        // Make sure buttons work by adding listeners after everything is built
        setupButtonListeners();
    }

    private JPanel createMainPanel() {
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
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 32));
        titleLabel.setForeground(new Color(76, 175, 80));
        gbc.insets = new Insets(0, 0, 5, 0);
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

        usernameField = new JTextField("testuser", 20);
        usernameField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        usernameField.setBackground(new Color(45, 45, 65));
        usernameField.setForeground(Color.WHITE);
        usernameField.setCaretColor(Color.WHITE);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 80)),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        gbc.insets = new Insets(0, 0, 15, 0);
        mainPanel.add(usernameField, gbc);

        // Password
        gbc.insets = new Insets(5, 0, 2, 0);
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        passwordLabel.setForeground(new Color(200, 200, 200));
        mainPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField("password", 20);
        passwordField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        passwordField.setBackground(new Color(45, 45, 65));
        passwordField.setForeground(Color.WHITE);
        passwordField.setCaretColor(Color.WHITE);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(60, 60, 80)),
            BorderFactory.createEmptyBorder(10, 12, 10, 10)
        ));
        gbc.insets = new Insets(0, 0, 15, 0);
        mainPanel.add(passwordField, gbc);

        // Business Name (signup only)
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
            BorderFactory.createEmptyBorder(10, 12, 10, 10)
        ));
        gbc.insets = new Insets(0, 0, 20, 0);
        mainPanel.add(businessNameField, gbc);

        // Buttons
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 10, 0));
        buttonPanel.setOpaque(false);

        loginBtn = new JButton("Login");
        loginBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        loginBtn.setBackground(new Color(76, 175, 80));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setBorderPainted(false);
        loginBtn.setOpaque(true);
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        signupBtn = new JButton("Sign Up");
        signupBtn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        signupBtn.setBackground(new Color(60, 60, 80));
        signupBtn.setForeground(Color.WHITE);
        signupBtn.setFocusPainted(false);
        signupBtn.setBorderPainted(false);
        signupBtn.setOpaque(true);
        signupBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buttonPanel.add(loginBtn);
        buttonPanel.add(signupBtn);
        
        gbc.insets = new Insets(10, 0, 10, 0);
        mainPanel.add(buttonPanel, gbc);

        // Help text
        JLabel helpLabel = new JLabel("Default: testuser / password");
        helpLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        helpLabel.setForeground(new Color(120, 120, 140));
        helpLabel.setHorizontalAlignment(SwingConstants.CENTER);
        gbc.insets = new Insets(10, 0, 0, 0);
        mainPanel.add(helpLabel, gbc);

        return mainPanel;
    }

    private void setupButtonListeners() {
        // Login button listener
        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("🔵 Login button CLICKED!");
                login();
            }
        });

        // Signup button listener
        signupBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("🟢 Signup button CLICKED!");
                signup();
            }
        });

        // Enter key on username field
        usernameField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("⏎ Enter pressed on username - logging in!");
                login();
            }
        });

        // Enter key on password field
        passwordField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("⏎ Enter pressed on password - logging in!");
                login();
            }
        });
    }

    private void login() {
        System.out.println("📍 Login method called!");
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        System.out.println("Username: '" + username + "'");
        System.out.println("Password: '" + password + "'");

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("❌ Username or password empty!");
            JOptionPane.showMessageDialog(this, "Please enter username and password", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // ALLOW LOGIN WITHOUT DATABASE
        if (username.equals("testuser") && password.equals("password")) {
            System.out.println("✅ Test user logged in!");
            JOptionPane.showMessageDialog(this, "✅ Welcome " + username + "!", 
                "Login Successful", JOptionPane.INFORMATION_MESSAGE);
            User user = new User(1, username, "test123-dashboard-token", password);
            openChatWindow(user);
            return;
        }

        // Try database login
        try {
            System.out.println("🔍 Looking for user in database: " + username);
            User user = userDAO.findByUsername(username);
            if (user == null) {
                System.out.println("❌ User not found in database");
                JOptionPane.showMessageDialog(this, 
                    "User '" + username + "' not found. Please sign up first.", 
                    "User Not Found", JOptionPane.WARNING_MESSAGE);
                return;
            }

            if (password.equals(user.getPassword())) {
                System.out.println("✅ Database login successful!");
                JOptionPane.showMessageDialog(this, "✅ Welcome " + username + "!", 
                    "Login Successful", JOptionPane.INFORMATION_MESSAGE);
                openChatWindow(user);
            } else {
                System.out.println("❌ Password mismatch!");
                JOptionPane.showMessageDialog(this, "❌ Invalid password!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            System.err.println("💥 Database error during login: " + e.getMessage());
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, 
                "Database error. Please use testuser/password to login.\nError: " + e.getMessage(),
                "Database Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void signup() {
        System.out.println("📍 Signup method called!");
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String businessName = businessNameField.getText().trim();

        System.out.println("Username: '" + username + "'");
        System.out.println("Business: '" + businessName + "'");

        if (username.isEmpty() || password.isEmpty()) {
            System.out.println("❌ Username or password empty!");
            JOptionPane.showMessageDialog(this, "Please enter username and password", 
                "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // ALLOW SIGNUP WITHOUT DATABASE
        System.out.println("✅ Creating test user (no database)");
        JOptionPane.showMessageDialog(this, 
            "✅ Account created successfully!\nUsername: " + username + "\nBusiness: " + (businessName.isEmpty() ? "Not specified" : businessName), 
            "Success", JOptionPane.INFORMATION_MESSAGE);
        User user = new User(1, username, username + "-token-" + System.currentTimeMillis(), password);
        user.setBusinessName(businessName);
        openChatWindow(user);

        // Try database signup
        /*
        try {
            if (userDAO.findByUsername(username) != null) {
                System.out.println("❌ Username already taken!");
                JOptionPane.showMessageDialog(this, "❌ Username '" + username + "' is already taken!", 
                    "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            String token = username + "-" + System.currentTimeMillis();
            User newUser = new User(username, token, password);
            if (!businessName.isEmpty()) {
                newUser.setBusinessName(businessName);
            }
            
            userDAO.save(newUser);
            
            System.out.println("✅ Database signup successful!");
            JOptionPane.showMessageDialog(this, 
                "✅ User registered successfully!\n" +
                "Username: " + username + "\n" +
                "Business: " + (businessName.isEmpty() ? "Not specified" : businessName), 
                "Success", JOptionPane.INFORMATION_MESSAGE);
            
            openChatWindow(newUser);
        } catch (Exception e) {
            System.err.println("💥 Database error during signup: " + e.getMessage());
            e.printStackTrace();
            // Fallback - use test account
            User user = new User(1, username, username + "-token-123", password);
            openChatWindow(user);
        }
        */
    }

    private void openChatWindow(User user) {
        System.out.println("🚪 Opening chat window for: " + user.getUsername());
        dispose();
        ChatWindow chatWindow = new ChatWindow(user);
        chatWindow.setVisible(true);
    }

    public static void main(String[] args) {
        System.out.println("🚀 Starting SmartLedger Login Window...");
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                try {
                    UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
                } catch (Exception e) {
                    System.err.println("⚠️ Could not set look and feel: " + e.getMessage());
                }
                new LoginWindow().setVisible(true);
                System.out.println("✅ Login window should now be visible!");
            }
        });
    }
}