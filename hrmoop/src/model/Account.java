package model;

import java.util.Date;

/**
 * Account class representing user accounts in the HR management system
 */
public class Account {
    private int userId;
    private String username;
    private String password;
    private String role;
    private String status;
    private Date createdDate;
    private Date lastLogin;
    
    /**
     * Default constructor
     */
    public Account() {
    }

    /**
     * Parameterized constructor
     * 
     * @param userId      Unique identifier for the account
     * @param username    Username for login
     * @param password    Password for login
     * @param role        Role of the account (e.g., "admin", "manager", "employee")
     * @param status      Account status (e.g., "active", "inactive", "locked")
     * @param createdDate Date when the account was created
     * @param lastLogin   Date of last login
     */
    public Account(int userId, String username, String password, String role, 
                  String status, Date createdDate, Date lastLogin) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.role = role;
        this.status = status;
        this.createdDate = createdDate;
        this.lastLogin = lastLogin;
    }

    // Getters and Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public String toString() {
        return "Account{" +
                "userId=" + userId +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
