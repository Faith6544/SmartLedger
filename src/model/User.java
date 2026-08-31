package model;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
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

    // Stored as "salt:hash" (both hex). Salt makes two users with the same
    // password get different hashes, and stops precomputed rainbow-table lookups.
    public static String hashPassword(String password) {
        byte[] saltBytes = new byte[16];
        new SecureRandom().nextBytes(saltBytes);
        String salt = toHex(saltBytes);
        return salt + ":" + hashWithSalt(password, salt);
    }

    private static String hashWithSalt(String password, String salt) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            md.update(salt.getBytes());
            byte[] hash = md.digest(password.getBytes());
            return toHex(hash);
        } catch (NoSuchAlgorithmException e) { throw new RuntimeException("SHA-256 not available", e); }
    }

    private static String toHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) sb.append(String.format("%02x", b));
        return sb.toString();
    }

    public boolean checkPassword(String password) {
        // Old accounts (created before salting) stored a bare hash with no "salt:" prefix.
        // Keep them logging in without forcing a password reset.
        if (!this.passwordHash.contains(":")) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA-256");
                byte[] hash = md.digest(password.getBytes());
                return this.passwordHash.equals(toHex(hash));
            } catch (NoSuchAlgorithmException e) { return false; }
        }
        String[] parts = this.passwordHash.split(":", 2);
        String salt = parts[0];
        String storedHash = parts[1];
        return storedHash.equals(hashWithSalt(password, salt));
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPasswordHash() { return passwordHash; }
    public String getDashboardToken() { return dashboardToken; }
    public String getBusinessName() { return businessName != null ? businessName : ""; }
    public Timestamp getCreatedAt() { return createdAt; }
    public void setId(int id) { this.id = id; }
}