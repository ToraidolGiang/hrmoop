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
 * Data Access Object for Candidate entity
 * Handles database operations related to candidates
 */
public class CandidateDAO {
    private Connection conn;
    
    public CandidateDAO() {
        try {
            conn = DatabaseConnection.getConnection();
        } catch (Exception e) {
            System.out.println("Error connecting to database: " + e.getMessage());
        }
    }
    
    /**
     * Insert a new candidate into the database
     * @param candidate The candidate to insert
     * @return The ID of the newly inserted candidate, or -1 if insertion failed
     */
    public int addCandidate(Candidate candidate) {
        String sql = "INSERT INTO candidates (first_name, last_name, email, phone, date_of_birth, " +
                     "address, education, experience, skills, application_date, status, position_id, recruitment_id) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            pstmt.setString(1, candidate.getFirstName());
            pstmt.setString(2, candidate.getLastName());
            pstmt.setString(3, candidate.getEmail());
            pstmt.setString(4, candidate.getPhone());
            
            if (candidate.getDateOfBirth() != null) {
                pstmt.setDate(5, new java.sql.Date(candidate.getDateOfBirth().getTime()));
            } else {
                pstmt.setNull(5, java.sql.Types.DATE);
            }
            
            pstmt.setString(6, candidate.getAddress());
            pstmt.setString(7, candidate.getEducation());
            pstmt.setString(8, candidate.getExperience());
            pstmt.setString(9, candidate.getSkills());
            
            if (candidate.getApplicationDate() != null) {
                pstmt.setDate(10, new java.sql.Date(candidate.getApplicationDate().getTime()));
            } else {
                pstmt.setDate(10, new java.sql.Date(new Date().getTime())); // Default to current date
            }
            
            pstmt.setString(11, candidate.getStatus());
            pstmt.setInt(12, candidate.getPositionId());
            pstmt.setInt(13, candidate.getRecruitmentId());
            
            int affectedRows = pstmt.executeUpdate();
            
            if (affectedRows > 0) {
                try (ResultSet rs = pstmt.getGeneratedKeys()) {
                    if (rs.next()) {
                        candidate.setCandidateId(rs.getInt(1));
                        return candidate.getCandidateId();
                    }
                }
            }
            return -1;
        } catch (SQLException e) {
            System.out.println("Error adding candidate: " + e.getMessage());
            return -1;
        }
    }
    
    /**
     * Update an existing candidate's information
     * @param candidate The candidate with updated information
     * @return true if update was successful, false otherwise
     */
    public boolean updateCandidate(Candidate candidate) {
        String sql = "UPDATE candidates SET first_name = ?, last_name = ?, email = ?, phone = ?, " +
                     "date_of_birth = ?, address = ?, education = ?, experience = ?, skills = ?, " +
                     "status = ?, position_id = ?, recruitment_id = ? WHERE candidate_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, candidate.getFirstName());
            pstmt.setString(2, candidate.getLastName());
            pstmt.setString(3, candidate.getEmail());
            pstmt.setString(4, candidate.getPhone());
            
            if (candidate.getDateOfBirth() != null) {
                pstmt.setDate(5, new java.sql.Date(candidate.getDateOfBirth().getTime()));
            } else {
                pstmt.setNull(5, java.sql.Types.DATE);
            }
            
            pstmt.setString(6, candidate.getAddress());
            pstmt.setString(7, candidate.getEducation());
            pstmt.setString(8, candidate.getExperience());
            pstmt.setString(9, candidate.getSkills());
            pstmt.setString(10, candidate.getStatus());
            pstmt.setInt(11, candidate.getPositionId());
            pstmt.setInt(12, candidate.getRecruitmentId());
            pstmt.setInt(13, candidate.getCandidateId());
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error updating candidate: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Delete a candidate from the database
     * @param candidateId The ID of the candidate to delete
     * @return true if deletion was successful, false otherwise
     */
    public boolean deleteCandidate(int candidateId) {
        String sql = "DELETE FROM candidates WHERE candidate_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, candidateId);
            
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            System.out.println("Error deleting candidate: " + e.getMessage());
            return false;
        }
    }
    
    /**
     * Get a candidate by ID
     * @param candidateId The ID of the candidate to retrieve
     * @return The candidate object if found, null otherwise
     */
    public Candidate getCandidateById(int candidateId) {
        String sql = "SELECT * FROM candidates WHERE candidate_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, candidateId);
            ResultSet rs = pstmt.executeQuery();
            
            if (rs.next()) {
                return mapResultSetToCandidate(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error getting candidate by ID: " + e.getMessage());
        }
        
        return null;
    }
    
    /**
     * Get all candidates
     * @return List of all candidates
     */
    public List<Candidate> getAllCandidates() {
        List<Candidate> candidates = new ArrayList<>();
        String sql = "SELECT * FROM candidates";
        
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                candidates.add(mapResultSetToCandidate(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error getting all candidates: " + e.getMessage());
        }
        
        return candidates;
    }
    
    /**
     * Get candidates by position ID
     * @param positionId The position ID to filter by
     * @return List of candidates for the specified position
     */
    public List<Candidate> getCandidatesByPosition(int positionId) {
        List<Candidate> candidates = new ArrayList<>();
        String sql = "SELECT * FROM candidates WHERE position_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, positionId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                candidates.add(mapResultSetToCandidate(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error getting candidates by position: " + e.getMessage());
        }
        
        return candidates;
    }
    
    /**
     * Get candidates by recruitment ID
     * @param recruitmentId The recruitment ID to filter by
     * @return List of candidates for the specified recruitment
     */
    public List<Candidate> getCandidatesByRecruitment(int recruitmentId) {
        List<Candidate> candidates = new ArrayList<>();
        String sql = "SELECT * FROM candidates WHERE recruitment_id = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, recruitmentId);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                candidates.add(mapResultSetToCandidate(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error getting candidates by recruitment: " + e.getMessage());
        }
        
        return candidates;
    }
    
    /**
     * Get candidates by status
     * @param status The status to filter by
     * @return List of candidates with the specified status
     */
    public List<Candidate> getCandidatesByStatus(String status) {
        List<Candidate> candidates = new ArrayList<>();
        String sql = "SELECT * FROM candidates WHERE status = ?";
        
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, status);
            ResultSet rs = pstmt.executeQuery();
            
            while (rs.next()) {
                candidates.add(mapResultSetToCandidate(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error getting candidates by status: " + e.getMessage());
        }
        
        return candidates;
    }
    
    /**
     * Helper method to map a ResultSet row to a Candidate object
     * @param rs The ResultSet to map
     * @return A new Candidate object with data from the ResultSet
     * @throws SQLException If there's an error accessing the ResultSet
     */
    private Candidate mapResultSetToCandidate(ResultSet rs) throws SQLException {
        Candidate candidate = new Candidate();
        candidate.setCandidateId(rs.getInt("candidate_id"));
        candidate.setFirstName(rs.getString("first_name"));
        candidate.setLastName(rs.getString("last_name"));
        candidate.setEmail(rs.getString("email"));
        candidate.setPhone(rs.getString("phone"));
        
        java.sql.Date dobDate = rs.getDate("date_of_birth");
        if (dobDate != null) {
            candidate.setDateOfBirth(new Date(dobDate.getTime()));
        }
        
        candidate.setAddress(rs.getString("address"));
        candidate.setEducation(rs.getString("education"));
        candidate.setExperience(rs.getString("experience"));
        candidate.setSkills(rs.getString("skills"));
        
        java.sql.Date appDate = rs.getDate("application_date");
        if (appDate != null) {
            candidate.setApplicationDate(new Date(appDate.getTime()));
        }
        
        candidate.setStatus(rs.getString("status"));
        candidate.setPositionId(rs.getInt("position_id"));
        candidate.setRecruitmentId(rs.getInt("recruitment_id"));
        
        return candidate;
    }
}
