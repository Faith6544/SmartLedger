package gui;

import commands.CommandHandler;
import database.ChatMessageDAO;
import database.TransactionDAO;
import model.ChatMessage;
import model.Transaction;
import model.TransactionType;
import model.User;
import parser.MessageParser;
import parser.ParseResult;
import dashboard.DashboardServer;

import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
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

    private boolean dashboardStarted = false;

    public ChatWindow(User user) {
        this.currentUser = user;
        this.parser = new MessageParser();
        this.commandHandler = new CommandHandler();
        this.transactionDAO = new TransactionDAO();
        this.chatMessageDAO = new ChatMessageDAO();

        setTitle("SmartLedger - " + user.getUsername());
        setSize(550, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        startDashboard();
        buildUI();

        appendSystem("Welcome to SmartLedger, " + user.getUsername() + "!");
        appendSystem("Type your transactions naturally, or type \"help\" to see commands.");
        if (dashboardStarted) {
            appendSystem("Dashboard: http://localhost:8080/dashboard/" + user.getDashboardToken());
        } else {
            appendSystem("Dashboard link unavailable right now - port 8080 may already be in use by another SmartLedger window. Close it and restart to get your dashboard link.");
        }
        appendSystem("--------------------------------------------");
    }

    private void buildUI() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(new Color(30, 30, 30));

        // Header
        JPanel header = new JPanel(new FlowLayout(FlowLayout.LEFT));
        header.setBackground(new Color(25, 25, 25));
        header.setBorder(BorderFactory.createEmptyBorder(8, 15, 8, 15));
        JLabel headerLabel = new JLabel("SmartLedger - " + currentUser.getUsername());
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
        sendBtn.setBorderPainted(false);
        sendBtn.setOpaque(true);
        sendBtn.setFont(new Font("SansSerif", Font.BOLD, 13));
        sendBtn.setPreferredSize(new Dimension(80, 35));

        inputPanel.add(inputField, BorderLayout.CENTER);
        inputPanel.add(sendBtn, BorderLayout.EAST);
        mainPanel.add(inputPanel, BorderLayout.SOUTH);

        inputField.addActionListener(e -> processInput());
        sendBtn.addActionListener(e -> processInput());

        add(mainPanel);
    }

    private void processInput() {
        String text = inputField.getText().trim();
        if (text.isEmpty()) return;
        inputField.setText("");
        appendUser(text);

        // Check if it's a command
        if (parser.isCommand(text)) {
            String response = commandHandler.handle(text, currentUser.getId(), currentUser.getDashboardToken());
            appendSystem(response);
            chatMessageDAO.save(new ChatMessage(currentUser.getId(), text, false));
            return;
        }

        // Parse the message
        ParseResult result = parser.parse(text);

        if (!result.isTransaction()) {
            // Not a transaction - save as chat
            chatMessageDAO.save(new ChatMessage(currentUser.getId(), text, false));
            String[] casualReplies = {"Got it.", "Noted!", "Alright.", "Okay, noted.", "Gotcha."};
            appendSystem(casualReplies[(int) (Math.random() * casualReplies.length)]);
            return;
        }

        // LOW confidence - ask user to pick category
        if (result.getConfidence() == ParseResult.Confidence.LOW) {
            showCategoryPicker(text, result);
            return;
        }

        // HIGH confidence - show confirmation
        showConfirmation(text, result);
    }

    private void showConfirmation(String rawText, ParseResult result) {
        String message = String.format(
            "Confirm Transaction\n\nCategory: %s\nAmount: N%,.2f\nDescription: %s%s\n\nIs this correct?",
            result.getType(),
            result.getAmount(),
            rawText,
            result.getCounterparty() != null ? "\nWho: " + result.getCounterparty() : ""
        );

        String[] options = {"Confirm", "Change Category", "Cancel"};
        int choice = JOptionPane.showOptionDialog(this, message, "Confirm Transaction",
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

        if (choice == 0) {
            // Confirm - save as is
            saveTransaction(result, rawText);
        } else if (choice == 1) {
            // Change category
            showCategoryPicker(rawText, result);
        } else {
            // Cancel
            chatMessageDAO.save(new ChatMessage(currentUser.getId(), rawText, false));
            appendSystem("Transaction cancelled.");
        }
    }

    private void showCategoryPicker(String rawText, ParseResult result) {
        TransactionType[] types = TransactionType.values();
        String[] typeNames = new String[types.length];
        for (int i = 0; i < types.length; i++) typeNames[i] = types[i].name();

        String message = String.format("Amount: N%,.2f\nMessage: %s\n\nSelect the correct category:",
            result.getAmount(), rawText);

        String picked = (String) JOptionPane.showInputDialog(this, message, "Select Category",
            JOptionPane.QUESTION_MESSAGE, null, typeNames,
            result.getType() != null ? result.getType().name() : typeNames[0]);

        if (picked != null) {
            result.setType(TransactionType.valueOf(picked));
            result.setConfidence(ParseResult.Confidence.HIGH);
            saveTransaction(result, rawText);
        } else {
            chatMessageDAO.save(new ChatMessage(currentUser.getId(), rawText, false));
            appendSystem("Transaction cancelled.");
        }
    }

    private void saveTransaction(ParseResult result, String rawText) {
        Transaction txn = new Transaction(currentUser.getId(), result.getType(),
            result.getAmount(), rawText, result.getCounterparty());
        transactionDAO.save(txn);
        chatMessageDAO.save(new ChatMessage(currentUser.getId(), rawText, true));

        String confirm = String.format("Recorded %s: N%,.2f", result.getType(), result.getAmount());
        if (result.getCounterparty() != null) confirm += " (" + result.getCounterparty() + ")";
        appendSystem(confirm);
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
            chatPane.setCaretPosition(doc.getLength());
        } catch (BadLocationException e) { e.printStackTrace(); }
    }

    private void startDashboard() {
        try {
            dashboardServer = new DashboardServer(8080);
            dashboardServer.start();
            dashboardStarted = true;
        } catch (Exception e) {
            System.out.println("Dashboard server failed to start: " + e.getMessage());
            dashboardStarted = false;
        }
    }
}