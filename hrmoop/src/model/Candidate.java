package model;

import java.util.Date;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a candidate in a recruitment or election system
 */
public class Candidate {
    private int id;
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Date dateOfBirth;
    private String address;
    private List<String> skills;
    private String resumeUrl;
    private String status; // e.g., "Applied", "Interviewing", "Rejected", "Hired"
    
    // Default constructor
    public Candidate() {
        this.skills = new ArrayList<>();
        this.status = "Applied";
    }
    
    // Parameterized constructor
    public Candidate(int id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.skills = new ArrayList<>();
        this.status = "Applied";
    }
    
    // Getters and Setters
    public int getId() {
        return id;
    }
    
    public void setId(int id) {
        this.id = id;
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
    
    public Date getDateOfBirth() {
        return dateOfBirth;
    }
    
    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    
    public String getAddress() {
        return address;
    }
    
    public void setAddress(String address) {
        this.address = address;
    }
    
    public List<String> getSkills() {
        return skills;
    }
    
    public void setSkills(List<String> skills) {
        this.skills = skills;
    }
    
    public void addSkill(String skill) {
        this.skills.add(skill);
    }
    
    public String getResumeUrl() {
        return resumeUrl;
    }
    
    public void setResumeUrl(String resumeUrl) {
        this.resumeUrl = resumeUrl;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    @Override
    public String toString() {
        return "Candidate{" +
                "id=" + id +
                ", name='" + getFullName() + '\'' +
                ", email='" + email + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
    
    // Example method to update candidate status
    public boolean updateStatus(String newStatus) {
        // Validate status transition
        if (isValidStatusTransition(this.status, newStatus)) {
            this.status = newStatus;
            return true;
        }
        return false;
    }
    
    // Example method to validate status transitions
    private boolean isValidStatusTransition(String currentStatus, String newStatus) {
        // Example implementation - could be more complex in a real system
        if (currentStatus.equals("Rejected") || currentStatus.equals("Hired")) {
            // Can't change status once rejected or hired
            return false;
        }
        
        // Prevent invalid transitions like going directly from Applied to Hired
        if (currentStatus.equals("Applied") && newStatus.equals("Hired")) {
            return false;
        }
        
        return true;
    }
}
