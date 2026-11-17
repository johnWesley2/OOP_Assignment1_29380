package com.auca.abstracts;

import com.auca.department.Department;
import com.auca.exceptions.ValidationException;
import java.time.LocalDate;

public abstract class AcademicMember extends Person {
    private String memberNumber, academicStatus, institutionalEmail, advisorName;
    private Department department;
    private LocalDate joinDate;

    public AcademicMember() {}

    public AcademicMember(String personId, String firstName, String lastName, String email,
            String phoneNumber, LocalDate dateOfBirth, String memberNumber, Department department,
            LocalDate joinDate, String academicStatus, String institutionalEmail, String advisorName) {
        super(personId, firstName, lastName, email, phoneNumber, dateOfBirth);
        setMemberNumber(memberNumber); 
        setDepartment(department); 
        setJoinDate(joinDate);
        setAcademicStatus(academicStatus); 
        setInstitutionalEmail(institutionalEmail);
        setAdvisorName(advisorName);
    }

    public String getMemberNumber() {
        return memberNumber; 
    }
    public void setMemberNumber(String v) {
        if (v == null || v.trim().isEmpty())
            throw new IllegalArgumentException("Member number required");
        memberNumber = v;
    }
    public Department getDepartment() { 
        return department; 
    }
    public void setDepartment(Department v) {
        if (v == null) throw new IllegalArgumentException("Department required");
        department = v;
    }
    public LocalDate getJoinDate() {
        return joinDate; 
    }
    public void setJoinDate(LocalDate v) {
        if (v == null) throw new IllegalArgumentException("Join date required");
        joinDate = v;
    }
    public String getAcademicStatus() { 
        return academicStatus; 
    }
    public void setAcademicStatus(String v) {
    if (v == null || v.trim().isEmpty()) 
            throw new IllegalArgumentException("Academic status required");
            academicStatus = v;
    }
    public String getInstitutionalEmail() { return institutionalEmail; }
    public void setInstitutionalEmail(String v) {
    if (v == null || !v.matches("^[\\w.+-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) 
        throw new IllegalArgumentException("Invalid institutional email");
        institutionalEmail = v;
    }
    public String getAdvisorName() { return advisorName; }
    public void setAdvisorName(String v) {
        if (v == null || v.trim().isEmpty()) throw new IllegalArgumentException("Advisor name required");
        advisorName = v;
    }

    @Override public void validateRegistration() throws ValidationException {
        super.validateRegistration();
        if (memberNumber == null || department == null || joinDate == null || academicStatus == null ||
            institutionalEmail == null || advisorName == null) throw new ValidationException("Complete AcademicMember details");
    }

    @Override public String toString() {
        return super.toString() + "\nMember Number: " + memberNumber + "\nDepartment: " +
            department.getDepartmentName() + "\nJoin Date: " + joinDate + "\nAcademic Status: " +
            academicStatus + "\nInstitutional Email: " + institutionalEmail + "\nAdvisor: " + advisorName;
    }
}
