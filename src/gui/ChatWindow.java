package gui;

import commands.CommandHandler;
import database.ChatMessageDAO;
import database.TransactionDAO;
import model.ChatMessage;
import model.Transaction;
import model.User;
import parser.MessageParser;
import dashboard.DashboardServer;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatWindow extends JFrame {

    private JTextPane chatPane;
    private JTextField inputField;
    private StyledDocument doc;
    private User currentUser;
    private MessageParser parser;
    private CommandHandler commandHandler;
    private TransactionDAO transactionDAO;
    private ChatMessageDAO chatMessageDAO;
    private DashboardServer dashboardServer;

    public ChatWindow(User user) {
        this.currentUser = user;
        this.parser = new MessageParser();
        this.commandHandler = new CommandHandler();
        this.transactionDAO = new TransactionDAO();
        this.chatMessageDAO = new ChatMessageDAO();

        setTitle("SmartLedger — " + user.getUsername());
        setSize(550, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Start dashboard server
        startDashboard();

        // Build UI
        buildUI();

        // Welcome message
        appendSystem("Welcome to SmartLedger, " + user.getUsername() + "! 🎉");
        appendSystem("Type your transactions naturally, or type \"help\" to see commands.");
        appendSystem("Your dashboard: http://localhost:8080/dashboard/" + user.getDashboardToken());
        appendSystem("─────────────────────────────────────");
    }

    private void buildUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(30, 30, 30));

        // Header
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(new Color(25, 25, 25));
        header.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        JLabel headerLabel = new JLabel("SmartLedger — " + currentUser.getUsername());
        headerLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        headerLabel.setForeground(new Color(76, 175, 80));
        header.add(headerLabel);
        mainPanel.add(header, BorderLayout.NORTH);

        // Chat area
        chatPane = new JTextPane();
        chatPane.setEditable(false);
        chatPane.setBackground(new Color(30, 30, 30));
        chatPane.setFont(new Font("SansSerif", Font.PLAIN, 14));
        doc = chatPane.getStyledDocument();

        JScrollPane scrollPane = new JScrollPane(chatPane);
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Input panel
        JPanel inputPanel = new JPanel(new BorderLayout(8, 0));
        inputPanel.setBackground(new Color(40, 40, 40));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        inputField = new JTextField();
        inputField.setFont(new Font("SansSerif", Font.PLAIN, 14));
        inputField.setBackground(new Color(55, 55, 55));
        inputField.setForeground(Color.WHITE);
        inputField.setCaretColor(Color.WHITE);
        inputField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(70, 70, 70)),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));

        JButton sendBtn = new JButton("Send");
        sendBtn.setBackground(new Color(76, 175, 80));
        sendBtn.setForeground(Color.WHITE);
        sendBtn.setFocusPainted(false);
        sendBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        sendBtn.setPreferredSize(new Dimension(70, 35));

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendBtn, BorderLayout.EAST);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        // Send action
        ActionListener sendAction = e -> processInput();
        inputField.addActionListener(sendAction);
        sendBtn.addActionListener(sendAction);

        add(mainPanel);
    }

    private void processInput() {
        String text = inputField.getText().trim();
        if (text.isEmpty()) return;

        inputField.setText("");

        // Show user message
        appendUser(text);

        // Check if it's a command
        if (parser.isCommand(text)) {
            String response = commandHandler.handle(text, currentUser.getId(), currentUser.getDashboardToken());
            appendSystem(response);

            // Save as chat message (not a transaction)
            chatMessageDAO.save(new ChatMessage(currentUser.getId(), text, false));
            return;
        }

        // Try to parse as a transaction
        Transaction txn = parser.parse(text, currentUser.getId());

        if (txn != null) {
            // Save the transaction
            transactionDAO.save(txn);

            // Save the chat message (marked as transaction)
            chatMessageDAO.save(new ChatMessage(currentUser.getId(), text, true));

            // Confirm
            String confirm = String.format("✅ Recorded %s: ₦%,.2f", txn.getType(), txn.getAmount());
            if (txn.getCounterparty() != null) {
                confirm += " (" + txn.getCounterparty() + ")";
            }
            appendSystem(confirm);

        } else {
            // Not a transaction — save as chat message
            chatMessageDAO.save(new ChatMessage(currentUser.getId(), text, false));
            appendSystem("💬 Got it. (Not recorded as a transaction)");
        }
    }

    private void appendUser(String text) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        appendColored("You [" + time + "]: ", new Color(100, 181, 246));
        appendColored(text + "\n", Color.WHITE);
    }

    private void appendSystem(String text) {
        String time = LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm"));
        appendColored("SmartLedger [" + time + "]: ", new Color(76, 175, 80));
        appendColored(text + "\n\n", new Color(200, 200, 200));
    }

    private void appendColored(String text, Color color) {
        SimpleAttributeSet attrs = new SimpleAttributeSet();
        StyleConstants.setForeground(attrs, color);
        StyleConstants.setFontFamily(attrs, "SansSerif");
        StyleConstants.setFontSize(attrs, 14);
        try {
            doc.insertString(doc.getLength(), text, attrs);
            // Auto-scroll to bottom
            chatPane.setCaretPosition(doc.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }

    private void startDashboard() {
        try {
            dashboardServer = new DashboardServer(8080);
            dashboardServer.start();
        } catch (Exception e) {
            System.out.println("Dashboard server failed to start: " + e.getMessage());
        }
    }
}
