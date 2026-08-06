package gui;

import database.UserDAO;
import model.User;
import javax.swing.*;
import java.awt.*;

public class LoginWindow extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;
    private JTextField bizField;
    private JLabel statusLabel;
    private UserDAO userDAO = new UserDAO();

    public LoginWindow() {
        setTitle("SmartLedger - Login");
        setSize(400, 420);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 40, 25, 40));
        panel.setBackground(new Color(30, 30, 30));

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
        panel.add(Box.createVerticalStrut(20));

        panel.add(makeLabel("Username"));
        panel.add(Box.createVerticalStrut(4));
        usernameField = new JTextField(20);
        usernameField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        usernameField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(usernameField);
        panel.add(Box.createVerticalStrut(12));

        panel.add(makeLabel("Password"));
        panel.add(Box.createVerticalStrut(4));
        passwordField = new JPasswordField(20);
        passwordField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        passwordField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(passwordField);
        panel.add(Box.createVerticalStrut(12));

        panel.add(makeLabel("Business Name (optional)"));
        panel.add(Box.createVerticalStrut(4));
        bizField = new JTextField(20);
        bizField.setMaximumSize(new Dimension(Integer.MAX_VALUE, 32));
        bizField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        panel.add(bizField);
        panel.add(Box.createVerticalStrut(18));

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        buttonPanel.setBackground(new Color(30, 30, 30));
        JButton loginBtn = makeButton("Login", new Color(76, 175, 80));
        loginBtn.addActionListener(e -> login());
        JButton signupBtn = makeButton("Sign Up", new Color(33, 150, 243));
        signupBtn.addActionListener(e -> signup());
        buttonPanel.add(loginBtn);
        buttonPanel.add(signupBtn);
        panel.add(buttonPanel);
        panel.add(Box.createVerticalStrut(12));

        statusLabel = new JLabel(" ");
        statusLabel.setForeground(new Color(244, 67, 54));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        statusLabel.setFont(new Font("SansSerif", Font.PLAIN, 12));
        panel.add(statusLabel);

        passwordField.addActionListener(e -> login());
        add(panel);
    }

    private JLabel makeLabel(String text) { JLabel l = new JLabel(text); l.setForeground(Color.WHITE); l.setAlignmentX(Component.LEFT_ALIGNMENT); return l; }
    private JButton makeButton(String text, Color bg) {
        JButton btn = new JButton(text); btn.setFont(new Font("SansSerif", Font.BOLD, 13)); btn.setForeground(Color.WHITE);
        btn.setBackground(bg); btn.setFocusPainted(false); btn.setBorderPainted(false); btn.setOpaque(true);
        btn.setPreferredSize(new Dimension(120, 36)); btn.setCursor(new Cursor(Cursor.HAND_CURSOR)); return btn;
    }

    private void login() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        if (username.isEmpty() || password.isEmpty()) { statusLabel.setText("Please enter both username and password."); return; }
        User user = userDAO.login(username, password);
        if (user != null) { dispose(); new ChatWindow(user).setVisible(true); }
        else { statusLabel.setText("Invalid username or password."); }
    }

    private void signup() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword());
        String biz = bizField.getText().trim();
        if (username.isEmpty() || password.isEmpty()) { statusLabel.setText("Please enter both username and password."); return; }
        if (password.length() < 4) { statusLabel.setText("Password must be at least 4 characters."); return; }
        User user = new User(username, password, biz);
        if (userDAO.createUser(user)) { statusLabel.setForeground(new Color(76, 175, 80)); statusLabel.setText("Account created! You can now log in."); }
        else { statusLabel.setText("Username already taken. Try another."); }
    }
}
