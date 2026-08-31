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

        setTitle("SmartLedger");
        setSize(450, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        // Main panel - slightly imperfect background
        JPanel mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Random-looking gradient, not perfectly smooth
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
                // Slightly off-center gradient
                GradientPaint gp = new GradientPaint(10, 20, new Color(20, 18, 35),
                                                      getWidth() - 10, getHeight() - 20, new Color(35, 30, 55));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());

                // Some subtle random dots (human touch)
                g2d.setColor(new Color(60, 55, 80, 30));
                for (int i = 0; i < 30; i++) {
                    int x = (int)(Math.random() * getWidth());
                    int y = (int)(Math.random() * getHeight());
                    g2d.fillOval(x, y, 2, 2);
                }
            }
        };
        mainPanel.setLayout(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(35, 35, 35, 35));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(4, 0, 4, 0);

        // Logo - slightly off-center
        JLabel titleLabel = new JLabel("SmartLedger", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 30));
        titleLabel.setForeground(new Color(100, 210, 120));
        gbc.insets = new Insets(0, 0, 2, 0);
        mainPanel.add(titleLabel, gbc);

        JLabel subtitleLabel = new JLabel("for small traders", SwingConstants.CENTER);
        subtitleLabel.setFont(new Font("Arial", Font.ITALIC, 13));
        subtitleLabel.setForeground(new Color(170, 170, 180));
        gbc.insets = new Insets(0, 0, 35, 0);
        mainPanel.add(subtitleLabel, gbc);

        // Username - slightly different spacing
        gbc.insets = new Insets(8, 0, 2, 0);
        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        usernameLabel.setForeground(new Color(190, 190, 200));
        mainPanel.add(usernameLabel, gbc);

        usernameField = new JTextField();
        usernameField.setFont(new Font("Arial", Font.PLAIN, 14));
        usernameField.setBackground(new Color(50, 48, 68));
        usernameField.setForeground(Color.WHITE);
        usernameField.setCaretColor(Color.WHITE);
        usernameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 68, 88)),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        gbc.insets = new Insets(0, 0, 12, 0);
        mainPanel.add(usernameField, gbc);

        // Password
        gbc.insets = new Insets(8, 0, 2, 0);
        JLabel passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 12));
        passwordLabel.setForeground(new Color(190, 190, 200));
        mainPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.PLAIN, 14));
        passwordField.setBackground(new Color(50, 48, 68));
        passwordField.setForeground(Color.WHITE);
        passwordField.setCaretColor(Color.WHITE);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 68, 88)),
            BorderFactory.createEmptyBorder(10, 12, 10, 12)
        ));
        gbc.insets = new Insets(0, 0, 12, 0);
        mainPanel.add(passwordField, gbc);

        // Business Name - optional
        gbc.insets = new Insets(8, 0, 2, 0);
        JLabel businessLabel = new JLabel("Business name (optional)");
        businessLabel.setFont(new Font("Arial", Font.PLAIN, 11));
        businessLabel.setForeground(new Color(160, 160, 175));
        mainPanel.add(businessLabel, gbc);

        businessNameField = new JTextField();
        businessNameField.setFont(new Font("Arial", Font.PLAIN, 13));
        businessNameField.setBackground(new Color(50, 48, 68));
        businessNameField.setForeground(Color.WHITE);
        businessNameField.setCaretColor(Color.WHITE);
        businessNameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 68, 88)),
            BorderFactory.createEmptyBorder(9, 12, 9, 12)
        ));
        gbc.insets = new Insets(0, 0, 25, 0);
        mainPanel.add(businessNameField, gbc);

        // Buttons - not perfectly aligned
        JPanel buttonPanel = new JPanel(new GridLayout(1, 2, 12, 0));
        buttonPanel.setOpaque(false);

        loginBtn = new JButton("Login");
        loginBtn.setFont(new Font("Arial", Font.BOLD, 14));
        loginBtn.setBackground(new Color(70, 180, 90));
        loginBtn.setForeground(Color.WHITE);
        loginBtn.setFocusPainted(false);
        loginBtn.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));
        loginBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        signupBtn = new JButton("Sign up");
        signupBtn.setFont(new Font("Arial", Font.BOLD, 13));
        signupBtn.setBackground(new Color(65, 65, 85));
        signupBtn.setForeground(Color.WHITE);
        signupBtn.setFocusPainted(false);
        signupBtn.setBorder(BorderFactory.createEmptyBorder(12, 0, 12, 0));
        signupBtn.setCursor(new Cursor(Cursor.HAND_CURSOR));

        buttonPanel.add(loginBtn);
        buttonPanel.add(signupBtn);

        gbc.insets = new Insets(5, 0, 8, 0);
        mainPanel.add(buttonPanel, gbc);

        // Button listeners
        loginBtn.addActionListener(e -> login());
        signupBtn.addActionListener(e -> signup());
        usernameField.addActionListener(e -> login());
        passwordField.addActionListener(e -> login());

        add(mainPanel);
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Please enter both username and password",
                "Hold on",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        // Real login against the database - no demo bypass, no silent fallback.
        // Any real error (DB down, bad connection) shows an actual error instead of
        // quietly logging you in as a fake account.
        try {
            User user = userDAO.login(username, password);
            if (user == null) {
                JOptionPane.showMessageDialog(this,
                    "Invalid username or password.",
                    "Login failed",
                    JOptionPane.ERROR_MESSAGE);
                return;
            }
            openChatWindow(user);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Couldn't reach the database: " + e.getMessage() +
                "\n\nMake sure MySQL is running and DB_PASS is set correctly.",
                "Connection error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void signup() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String businessName = businessNameField.getText().trim();

        if (username.isEmpty() || password.length() < 4) {
            JOptionPane.showMessageDialog(this,
                "Username required. Password must be at least 4 characters.",
                "Hold on",
                JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            User user = new User(username, password, businessName);
            if (userDAO.createUser(user)) {
                JOptionPane.showMessageDialog(this,
                    "Account created!\nUsername: " + username +
                    "\nBusiness: " + (businessName.isEmpty() ? "not set" : businessName),
                    "All set!",
                    JOptionPane.INFORMATION_MESSAGE);
                openChatWindow(user);
            } else {
                JOptionPane.showMessageDialog(this,
                    "That username is already taken. Try another.",
                    "Signup failed",
                    JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this,
                "Couldn't reach the database: " + e.getMessage() +
                "\n\nMake sure MySQL is running and DB_PASS is set correctly.",
                "Connection error",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openChatWindow(User user) {
        dispose();
        ChatWindow chatWindow = new ChatWindow(user);
        chatWindow.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception e) {
                // Use default
            }
            new LoginWindow().setVisible(true);
        });
    }
}