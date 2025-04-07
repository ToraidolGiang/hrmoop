package model;

import model.DatabaseConnection.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance().getConnection();
    }

    public List<Account> getAllAccounts() {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts ORDER BY username";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Account account = extractAccountFromResultSet(rs);
                accounts.add(account);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách tài khoản: " + e.getMessage());
        }

        return accounts;
    }

    public Account getAccountById(int userId) {
        String sql = "SELECT * FROM accounts WHERE user_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractAccountFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy thông tin tài khoản: " + e.getMessage());
        }

        return null;
    }

    public Account getAccountByUsername(String username) {
        String sql = "SELECT * FROM accounts WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractAccountFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy thông tin tài khoản theo tên đăng nhập: " + e.getMessage());
        }

        return null;
    }

    public boolean addAccount(Account account) {
        String sql = "INSERT INTO accounts (user_id, username, password, access_permissions, created_date, last_login) " +
                     "VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, account.getUserId());
            stmt.setString(2, account.getUsername());
            stmt.setString(3, account.getPassword());
            stmt.setString(4, account.getAccess_permissions());

            if (account.getCreatedDate() != null) {
                stmt.setTimestamp(5, new Timestamp(account.getCreatedDate().getTime()));
            } else {
                stmt.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            }

            if (account.getLastLogin() != null) {
                stmt.setTimestamp(6, new Timestamp(account.getLastLogin().getTime()));
            } else {
                stmt.setNull(6, Types.TIMESTAMP);
            }

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next() && account.getUserId() == 0) {
                        account.setUserId(generatedKeys.getInt(1));
                    }
                    return true;
                }
            }

        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm tài khoản mới: " + e.getMessage());
        }

        return false;
    }

    public boolean updateAccount(Account account) {
        String sql = "UPDATE accounts SET username = ?, password = ?, access_permissions = ?, last_login = ? " +
                     "WHERE user_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, account.getUsername());
            stmt.setString(2, account.getPassword());
            stmt.setString(3, account.getAccess_permissions());

            if (account.getLastLogin() != null) {
                stmt.setTimestamp(4, new Timestamp(account.getLastLogin().getTime()));
            } else {
                stmt.setNull(4, Types.TIMESTAMP);
            }

            stmt.setInt(5, account.getUserId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật thông tin tài khoản: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteAccount(int userId) {
        String sql = "UPDATE accounts SET access_permissions = 'INACTIVE' WHERE user_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa tài khoản: " + e.getMessage());
            return false;
        }
    }

    public boolean permanentDeleteAccount(int userId) {
        String sql = "DELETE FROM accounts WHERE user_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa vĩnh viễn tài khoản: " + e.getMessage());
            return false;
        }
    }

    private Account extractAccountFromResultSet(ResultSet rs) throws SQLException {
        Account account = new Account();
        account.setUserId(rs.getInt("user_id"));
        account.setUsername(rs.getString("username"));
        account.setPassword(rs.getString("password"));
        account.setAccess_permissions(rs.getString("access_permissions"));
        account.setCreatedDate(rs.getTimestamp("created_date"));
        account.setLastLogin(rs.getTimestamp("last_login"));
        return account;
    }

    public List<Account> searchAccountsByUsername(String searchUsername) {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE username LIKE ? ORDER BY username";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + searchUsername + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    accounts.add(extractAccountFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm tài khoản theo tên đăng nhập: " + e.getMessage());
        }

        return accounts;
    }

    public List<Account> getAccountsByAccessPermission(String accessPermission) {
        List<Account> accounts = new ArrayList<>();
        String sql = "SELECT * FROM accounts WHERE access_permissions = ? ORDER BY username";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, accessPermission);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    accounts.add(extractAccountFromResultSet(rs));
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lọc tài khoản theo quyền truy cập: " + e.getMessage());
        }

        return accounts;
    }

    public boolean authenticate(String username, String password) {
        String sql = "SELECT COUNT(*) FROM accounts WHERE username = ? AND password = ? AND access_permissions = 'ACTIVE'";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi xác thực đăng nhập: " + e.getMessage());
        }

        return false;
    }

    public boolean updateLastLogin(int userId) {
        String sql = "UPDATE accounts SET last_login = ? WHERE user_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            stmt.setInt(2, userId);

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật thời gian đăng nhập cuối: " + e.getMessage());
            return false;
        }
    }
}
