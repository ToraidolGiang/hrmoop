package model;

import model.DatabaseConnection.*;
import model.Manager.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ManagerDAO {
    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance().getConnection();
    }

    public List<Manager> getAllManagers() {
        List<Manager> managers = new ArrayList<>();
        String sql = "SELECT m.* " +
                    "FROM managers m " +
                    "ORDER BY m.last_name, m.first_name";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Manager manager = extractManagerFromResultSet(rs);
                managers.add(manager);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách quản lý: " + e.getMessage());
        }
        
        return managers;
    }

    public Manager getManagerById(int userId) {
        String sql = "SELECT m.* " +
                    "FROM managers m " +
                    "WHERE m.user_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractManagerFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy thông tin quản lý: " + e.getMessage());
        }
        
        return null;
    }

    public boolean addManager(Manager manager) {
        String sql = "INSERT INTO managers (email, first_name, last_name, " +
                    "date_of_birth, gender, phone_number, address, " +
                    "status, access_permissions) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, manager.getEmail());
            stmt.setString(2, manager.getFirstName());
            stmt.setString(3, manager.getLastName());
            
            if (manager.getDateOfBirth() != null) {
                stmt.setDate(4, new java.sql.Date(manager.getDateOfBirth().getTime()));
            } else {
                stmt.setNull(4, Types.DATE);
            }
            
            stmt.setString(5, manager.getGender());
            stmt.setString(6, manager.getPhoneNumber());
            stmt.setString(7, manager.getAddress());
            stmt.setString(8, manager.getStatus());
            stmt.setString(9, manager.getAccessPermissions());
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        manager.setUserId(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm quản lý mới: " + e.getMessage());
        }
        
        return false;
    }

    public boolean updateManager(Manager manager) {
        String sql = "UPDATE managers SET email = ?, first_name = ?, last_name = ?, " +
                    "date_of_birth = ?, gender = ?, phone_number = ?, address = ?, " +
                    "status = ?, access_permissions = ? " +
                    "WHERE user_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, manager.getEmail());
            stmt.setString(2, manager.getFirstName());
            stmt.setString(3, manager.getLastName());
            
            if (manager.getDateOfBirth() != null) {
                stmt.setDate(4, new java.sql.Date(manager.getDateOfBirth().getTime()));
            } else {
                stmt.setNull(4, Types.DATE);
            }
            
            stmt.setString(5, manager.getGender());
            stmt.setString(6, manager.getPhoneNumber());
            stmt.setString(7, manager.getAddress());
            stmt.setString(8, manager.getStatus());
            stmt.setString(9, manager.getAccessPermissions());
            stmt.setInt(10, manager.getUserId());
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật thông tin quản lý: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteManager(int userId) {
        String sql = "UPDATE managers SET status = 'INACTIVE' WHERE user_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa quản lý: " + e.getMessage());
            return false;
        }
    }

    private Manager extractManagerFromResultSet(ResultSet rs) throws SQLException {
        Manager manager = new Manager();
        manager.setUserId(rs.getInt("user_id"));
        manager.setEmail(rs.getString("email"));
        manager.setFirstName(rs.getString("first_name"));
        manager.setLastName(rs.getString("last_name"));
        manager.setDateOfBirth(rs.getDate("date_of_birth"));
        manager.setGender(rs.getString("gender"));
        manager.setPhoneNumber(rs.getString("phone_number"));
        manager.setAddress(rs.getString("address"));
        manager.setStatus(rs.getString("status"));
        manager.setAccessPermissions(rs.getString("access_permissions"));
        return manager;
    }
    
    // Method to search managers by name
    public List<Manager> searchManagersByName(String searchName) {
        List<Manager> managers = new ArrayList<>();
        String sql = "SELECT m.* FROM managers m " +
                     "WHERE CONCAT(m.first_name, ' ', m.last_name) LIKE ? " +
                     "ORDER BY m.last_name, m.first_name";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, "%" + searchName + "%");
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Manager manager = extractManagerFromResultSet(rs);
                    managers.add(manager);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi tìm kiếm quản lý theo tên: " + e.getMessage());
        }
        
        return managers;
    }
    
    // Method to filter managers by status
    public List<Manager> getManagersByStatus(String status) {
        List<Manager> managers = new ArrayList<>();
        String sql = "SELECT m.* FROM managers m " +
                     "WHERE m.status = ? " +
                     "ORDER BY m.last_name, m.first_name";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, status);
            
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Manager manager = extractManagerFromResultSet(rs);
                    managers.add(manager);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lọc quản lý theo trạng thái: " + e.getMessage());
        }
        
        return managers;
    }
}
