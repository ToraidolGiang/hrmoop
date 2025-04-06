package EmployeeDAO;

import databaseconnection.*;
import Employee.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class EmployeeDAO {
    private Connection getConnection() throws SQLException {
        return DatabaseConnection.getInstance().getConnection();
    }

    public List<Employee> getAllEmployees() {
        List<Employee> employees = new ArrayList<>();
        String sql = "SELECT u.* " +
                    "FROM users u " +
                    "ORDER BY u.last_name, u.first_name";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Employee employee = extractEmployeeFromResultSet(rs);
                employees.add(employee);
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy danh sách nhân viên: " + e.getMessage());
        }
        
        return employees;
    }

    public Employee getEmployeeById(int userId) {
        String sql = "SELECT u.* " +
                    "FROM users u " +
                    "WHERE u.user_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return extractEmployeeFromResultSet(rs);
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi lấy thông tin nhân viên: " + e.getMessage());
        }
        
        return null;
    }

    public boolean addEmployee(Employee employee) {
        String sql = "INSERT INTO users (email, first_name, last_name, " +
                    "date_of_birth, gender, phone_number, address, " +
                    "hire_date, status,access_permissions) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
           
            stmt.setString(1, employee.getEmail());
            stmt.setString(2, employee.getFirstName());
            stmt.setString(3, employee.getLastName());
            
            if (employee.getDateOfBirth() != null) {
                stmt.setDate(4, new java.sql.Date(employee.getDateOfBirth().getTime()));
            } else {
                stmt.setNull(5, Types.DATE);
            }
            
            stmt.setString(6, employee.getGender());
            stmt.setString(7, employee.getPhoneNumber());
            stmt.setString(8, employee.getAddress());
            stmt.setDate(9, new java.sql.Date(employee.getHireDate().getTime()));
            stmt.setString(10, employee.getStatus());
            stmt.setString(11, employee.access_permissions());// code hamf getPermission
            
            int affectedRows = stmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        employee.setUserId(generatedKeys.getInt(1));
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Lỗi khi thêm nhân viên mới: " + e.getMessage());
        }
        
        return false;
    }

    public boolean updateEmployee(Employee employee) {
        String sql = "UPDATE users SET email = ?, first_name = ?, last_name = ?, " +
                    "date_of_birth = ?, gender = ?, phone_number = ?, address = ?, " +
                    "status = ? " +
                    "WHERE user_id = ? ";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
           stmt.setString(1, employee.getEmail());
            stmt.setString(2, employee.getFirstName());
            stmt.setString(3, employee.getLastName());
            
            if (employee.getDateOfBirth() != null) {
                stmt.setDate(4, new java.sql.Date(employee.getDateOfBirth().getTime()));
            } else {
                stmt.setNull(5, Types.DATE);
            }
            
            stmt.setString(6, employee.getGender());
            stmt.setString(7, employee.getPhoneNumber());
            stmt.setString(8, employee.getAddress());
            stmt.setDate(9, new java.sql.Date(employee.getHireDate().getTime()));
            stmt.setString(10, employee.getStatus());
            stmt.setString(11, employee.access_permissions());// code hamf getPermission
            
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi cập nhật thông tin nhân viên: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteEmployee(int userId) {
        String sql = "UPDATE users SET status = 'INACTIVE' WHERE user_id = ?";

        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, userId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi khi xóa nhân viên: " + e.getMessage());
            return false;
        }
    }

    private Employee extractEmployeeFromResultSet(ResultSet rs) throws SQLException {
        Employee employee = new Employee();
        employee.setUserId(rs.getInt("user_id"));
        employee.setEmail(rs.getString("email"));
        employee.setFirstName(rs.getString("first_name"));
        employee.setLastName(rs.getString("last_name"));
        employee.setDateOfBirth(rs.getDate("date_of_birth"));
        employee.setGender(rs.getString("gender"));
        employee.setPhoneNumber(rs.getString("phone_number"));
        employee.setAddress(rs.getString("address"));
        employee.setHireDate(rs.getDate("hire_date"));
        employee.setStatus(rs.getString("status"));
        employee.setAccessPermissions(rs.getString("access_permissions"));
        return employee;
    }
}
