package model;

public class User {
    private int id;
    private String username;
    private String dashboardToken;
    private String password;
    private String businessName;
    private String email;

    public User() {}

    public User(int id, String username, String dashboardToken) {
        this.id = id;
        this.username = username;
        this.dashboardToken = dashboardToken;
    }

    public User(String username, String dashboardToken, String password) {
        this.username = username;
        this.dashboardToken = dashboardToken;
        this.password = password;
    }

    public User(int id, String username, String dashboardToken, String password) {
        this.id = id;
        this.username = username;
        this.dashboardToken = dashboardToken;
        this.password = password;
    }

    public User(String username, String dashboardToken, String password, String businessName) {
        this.username = username;
        this.dashboardToken = dashboardToken;
        this.password = password;
        this.businessName = businessName;
    }

    // Getters and Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getDashboardToken() { return dashboardToken; }
    public void setDashboardToken(String dashboardToken) { this.dashboardToken = dashboardToken; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getBusinessName() { return businessName; }
    public void setBusinessName(String businessName) { this.businessName = businessName; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return "User{id=" + id + ", username='" + username + "'}";
    }
}