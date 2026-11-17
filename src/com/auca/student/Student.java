package com.auca.student;

import com.auca.abstracts.AcademicMember;
import com.auca.department.Department;
import com.auca.exceptions.ValidationException;
import java.time.LocalDate;

public class Student extends AcademicMember {
    private String studentNumber, programName, guardianPhone;
    private LocalDate admissionDate;
    private int studyYear;
    private boolean scholarshipStatus;

    public Student() {}
    public Student(String personId, String firstName, String lastName, String email, String phoneNumber,
            LocalDate dateOfBirth, String memberNumber, Department department, LocalDate joinDate,
            String academicStatus, String institutionalEmail, String advisorName, String studentNumber,
            String programName, LocalDate admissionDate, int studyYear, String guardianPhone, boolean scholarshipStatus) {
        super(personId, firstName, lastName, email, phoneNumber, dateOfBirth, memberNumber, department,
              joinDate, academicStatus, institutionalEmail, advisorName);
        setStudentNumber(studentNumber); setProgramName(programName); setAdmissionDate(admissionDate);
        setStudyYear(studyYear); setGuardianPhone(guardianPhone); setScholarshipStatus(scholarshipStatus);
    }
    public String getStudentNumber() { return studentNumber; }
    public void setStudentNumber(String v) { if (v == null || v.trim().isEmpty()) throw new IllegalArgumentException("Student number required"); studentNumber = v; }
    public String getProgramName() { return programName; }
    public void setProgramName(String v) { if (v == null || v.trim().isEmpty()) throw new IllegalArgumentException("Program required"); programName = v; }
    public LocalDate getAdmissionDate() { return admissionDate; }
    public void setAdmissionDate(LocalDate v) { if (v == null) throw new IllegalArgumentException("Admission date required"); admissionDate = v; }
    public int getStudyYear() { return studyYear; }
    public void setStudyYear(int v) { if (v < 1 || v > 6) throw new IllegalArgumentException("Study year must be 1-6"); studyYear = v; }
    public String getGuardianPhone() { return guardianPhone; }
    public void setGuardianPhone(String v) { if (v == null || !v.matches("\\+?[0-9]{7,15}")) throw new IllegalArgumentException("Invalid guardian phone"); guardianPhone = v; }
    public boolean isScholarshipStatus() { return scholarshipStatus; }
    public void setScholarshipStatus(boolean v) { scholarshipStatus = v; }
    @Override public void validateRegistration() throws ValidationException {
        super.validateRegistration();
        if (studentNumber == null || programName == null || admissionDate == null || studyYear < 1 || guardianPhone == null)
            throw new ValidationException("Complete Student details");
    }
    @Override public String getRegistrationName() { return getFirstName() + " " + getLastName(); }
    @Override public String getId() { return getStudentNumber(); }
    @Override public String getStatus() { return getAcademicStatus(); }
    @Override public String toString() {
        return "STUDENT RECORD\n" + super.toString() + "\nStudent Number: " + studentNumber +
            "\nProgram: " + programName + "\nAdmission Date: " + admissionDate + "\nStudy Year: " + studyYear +
            "\nGuardian Phone: " + guardianPhone + "\nScholarship: " + scholarshipStatus;
    }
}
