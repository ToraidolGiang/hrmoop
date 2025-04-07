package model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Data Access Object for Account entity
 * Handles database operations related to accounts
 */
public class AccountDAO {
    private Connection connection;
    
    /**
     * Constructor with database connection
     * @param connection Database connection
     */
    public AccountDAO(Connection connection) {
        this.connection = connection;
    }
    
    /**
     * Insert a new account into the database
     * @param account Account object to insert
     * @return true if insertion was successful, false otherwise
     */
    public boolean insertAccount(Account account) {
        String sql = "INSERT INTO accounts (user_id, username, password, role, status, created_date, last_login) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, account.getUserId());
            stmt.setString(2, account.getUsername());
            stmt.setString(3, account.getPassword());
            stmt.setString(4, account.getRole());
            stmt.setString(5, account.getStatus());
            stmt.setTimestamp(6, account.getCreatedDate() != null ? 
                               new java.sql.Timestamp(account.getCreatedDate().getTime()) : null);
            stmt.setTimestamp(7, account.getLastLogin() != null ? 
                               new java.sql.Timestamp(account.getLastLogin().getTime()) : null);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting account: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Update an existing account in the database
     * @param account Account object with updated values
     * @return true if update was successful, false otherwise
     */
    public boolean updateAccount(Account account) {
        String sql = "UPDATE accounts SET username = ?, password = ?, role = ?, status = ?, " +
                     "last_login = ? WHERE user_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, account.getUsername());
            stmt.setString(2, account.getPassword());
            stmt.setString(3, account.getRole());
            stmt.setString(4, account.getStatus());
            stmt.setTimestamp(5, account.getLastLogin() != null ? 
                               new java.sql.Timestamp(account.getLastLogin().getTime()) : null);
            stmt.setInt(6, account.getUserId());
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating account: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Delete an account from the database
     * @param userId ID of the account to delete
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteAccount(int userId) {
        String sql = "DELETE FROM accounts WHERE user_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error deleting account: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get account by user ID
     * @param userId ID of the account to retrieve
     * @return Account object if found, null otherwise
     */
    public Account getAccountByUserId(int userId) {
        String sql = "SELECT * FROM accounts WHERE user_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractAccountFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting account by user ID: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Get account by username
     * @param username Username of the account to retrieve
     * @return Account object if found, null otherwise
     */
    public Account getAccountByUsername(String username) {
        String sql = "SELECT * FROM accounts WHERE username = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractAccountFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error getting account by username: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Get all accounts from the database
     * @return List of all accounts
     */
    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                accounts.add(extractAccountFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error getting all accounts: " + e.getMessage());
        }
        
        return accounts;
    }
    
    /**
     * Check if account exists by username
     * @param username Username to check
     * @return true if account exists, false otherwise
     */
    public boolean accountExistsByUsername(String username) {
        String sql = "SELECT COUNT(*) FROM accounts WHERE username = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Error checking if account exists: " + e.getMessage());
        }
        
        return false;
    }
    
    /**
     * Authenticate a user
     * @param username Username for authentication
     * @param password Password for authentication
     * @return Account object if authentication successful, null otherwise
     */
    public Account authenticate(String username, String password) {
        String sql = "SELECT * FROM accounts WHERE username = ? AND password = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractAccountFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Error authenticating user: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Update the last login time for an account
     * @param userId ID of the account to update
     * @return true if update was successful, false otherwise
     */
    public boolean updateLastLogin(int userId) {
        String sql = "UPDATE accounts SET last_login = ? WHERE user_id = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, new java.sql.Timestamp(new Date().getTime()));
            stmt.setInt(2, userId);
            
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            System.err.println("Error updating last login time: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Extract account data from a ResultSet
     * @param rs ResultSet containing account data
     * @return Account object
     * @throws SQLException if error occurs while extracting data
     */
    private Account extractAccountFromResultSet(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setUserId(rs.getInt("user_id"));
        account.setUsername(rs.getString("username"));
        account.setPassword(rs.getString("password"));
        account.setRole(rs.getString("role"));
        account.setStatus(rs.getString("status"));
        
        java.sql.Timestamp createdDate = rs.getTimestamp("created_date");
        if (createdDate != null) {
            account.setCreatedDate(new Date(createdDate.getTime()));
        }
        
        java.sql.Timestamp lastLogin = rs.getTimestamp("last_login");
        if (lastLogin != null) {
            account.setLastLogin(new Date(lastLogin.getTime()));
        }
        
        return account;
    }
}
