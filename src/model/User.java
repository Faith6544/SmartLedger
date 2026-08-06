package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.util.UUID;

public class User {
    private int id;
    private String username;
    private String passwordHash;
    private String dashboardToken;
    private String businessName;
    private Timestamp createdAt;

    public User(String username, String password, String businessName) {
        this.username = username;
        this.passwordHash = hashPassword(password);
        this.dashboardToken = UUID.randomUUID().toString().replace("-", "");
        this.businessName = businessName;
    }

    public User(String username, String password) {
        this(username, password, null);
    }

    public User(int id, String username, String passwordHash, String dashboardToken, String businessName, Timestamp createdAt) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.dashboardToken = dashboardToken;
        this.businessName = businessName;
        this.createdAt = createdAt;
    }

    public static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (NoSuchAlgorithmException e) { throw new RuntimeException("SHA-256 not available", e); }
    }

    public boolean checkPassword(String password) { return this.passwordHash.equals(hashPassword(password)); }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public String getDashboardToken() { return dashboardToken; }
    public String getBusinessName() { return businessName != null ? businessName : ""; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setId(int id) { this.id = id; }
}
