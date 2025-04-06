package Employee;

import java.util.Date;

public class Employee {
    private int userId;
    private String username;  // Cần khai báo biến username, thay cho email
    private String email;
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String gender;
    private String phoneNumber;
    private String address;
    private Date hireDate;
    private double salary;  // Thêm thuộc tính salary
    private String status;
    private boolean access_permissions;  // Sửa lại thành kiểu dữ liệu boolean

    // Constructor đầy đủ
    public Employee(int userId, String username, String email, String firstName, String lastName,
                    Date dateOfBirth, String gender, String phoneNumber, String address, Date hireDate, 
                    double salary, String status, boolean access_permissions) {
        this.userId = userId;
        this.username = username;  // Sửa lại tên tham số
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.hireDate = hireDate;
        this.salary = salary;
        this.status = status;
        this.access_permissions = access_permissions;
    }

    // Constructor rỗng
    public Employee() {
    }

    // Getter và Setter cho tất cả các thuộc tính
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return firstName + " " + lastName;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean isAccessPermissions() {
        return access_permissions;
    }

    public void setAccessPermissions(boolean access_permissions) {
        this.access_permissions = access_permissions;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "userId=" + userId +
                ", fullName='" + getFullName() + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
