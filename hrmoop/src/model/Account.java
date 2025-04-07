package model;

import java.util.Date;

/**
 * Account class representing user accounts in the HR management system
 */
public class Account {
    private int userId;
    private String username;
    private String password;
    private String access_permissions;
    private Date createdDate;
    private Date lastLogin;
    
    /**
     * Default constructor
     */
    public Account() {
    }

    public Account(int userId, String username, String password, String access_permissions, 
                   Date createdDate, Date lastLogin) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.access_permissions = access_permissions;
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

    public String getAccess_permissions() {
        return access_permissions;
    }

    public void setAccess_permissions(String access_permissions) {
        this.access_permissions = access_permissions;
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
                ", access_permissions='" + access_permissions + '\'' +
                '}';
    }
}
