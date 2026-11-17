package com.auca.administrator;

import com.auca.abstracts.Person;
import com.auca.exceptions.ValidationException;
import java.time.LocalDate;

public class Administrator extends Person {
    private String staffNumber, jobTitle, assignedOffice;
    private LocalDate employmentDate;
    private int accessLevel;
    private boolean active;

    public Administrator() {}
    public Administrator(String personId, String firstName, String lastName, String email,
            String phoneNumber, LocalDate dateOfBirth, String staffNumber, String jobTitle,
            String assignedOffice, LocalDate employmentDate, int accessLevel, boolean active) {
        super(personId, firstName, lastName, email, phoneNumber, dateOfBirth);
        setStaffNumber(staffNumber); setJobTitle(jobTitle); setAssignedOffice(assignedOffice);
        setEmploymentDate(employmentDate); setAccessLevel(accessLevel); setActive(active);
    }
    public String getStaffNumber() { return staffNumber; }
    public void setStaffNumber(String v) { if (v == null || v.trim().isEmpty()) throw new IllegalArgumentException("Staff number is required"); staffNumber = v; }
    public String getJobTitle() { return jobTitle; }
    public void setJobTitle(String v) { if (v == null || v.trim().length() < 2) throw new IllegalArgumentException("Job title must have at least 2 characters"); jobTitle = v; }
    public String getAssignedOffice() { return assignedOffice; }
    public void setAssignedOffice(String v) { if (v == null || v.trim().isEmpty()) throw new IllegalArgumentException("Assigned office is required"); assignedOffice = v; }
    public LocalDate getEmploymentDate() { return employmentDate; }
    public void setEmploymentDate(LocalDate v) { if (v == null) throw new IllegalArgumentException("Employment date is required"); employmentDate = v; }
    public int getAccessLevel() { return accessLevel; }
    public void setAccessLevel(int v) { if (v < 1 || v > 5) throw new IllegalArgumentException("Access level must be between 1 and 5"); accessLevel = v; }
    public boolean isActive() { return active; }
    public void setActive(boolean v) { active = v; }
    @Override public String getId() { return staffNumber; }
    @Override public String getStatus() { return active ? "ACTIVE" : "INACTIVE"; }
    @Override public String getRegistrationName() { return getFirstName() + " " + getLastName(); }
    @Override public void validateRegistration() throws ValidationException {
        super.validateRegistration();
        if (staffNumber == null || jobTitle == null || assignedOffice == null || employmentDate == null)
            throw new ValidationException("Complete all administrator details");
    }
    @Override public String toString() {
        return "ADMINISTRATOR RECORD\n" + super.toString() + "\nStaff Number: " + staffNumber +
            "\nJob Title: " + jobTitle + "\nAssigned Office: " + assignedOffice +
            "\nEmployment Date: " + employmentDate + "\nAccess Level: " + accessLevel + "\nActive: " + active;
    }
}
