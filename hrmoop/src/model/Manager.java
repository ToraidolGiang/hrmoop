package model;

public class Manager {
    private int managerId;
    private String name;
    private String email;
    private String phone;
    private int departmentId;

    public Manager() {
    }

    public Manager(int managerId, String name, String email, String phone, int departmentId) {
        this.managerId = managerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.departmentId = departmentId;
    }

    public int getManagerId() {
        return managerId;
    }

    public void setManagerId(int managerId) {
        this.managerId = managerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    @Override
    public String toString() {
        return "Manager{" +
                "managerId=" + managerId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", departmentId=" + departmentId +
                '}';
    }
}

