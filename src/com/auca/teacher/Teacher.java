package com.auca.teacher;

import com.auca.abstracts.AcademicMember;
import com.auca.department.Department;
import com.auca.exceptions.ValidationException;
import java.time.LocalDate;
import java.math.BigDecimal;

public class Teacher extends AcademicMember {
    private String employeeNumber, specialization, officeNumber, employmentType;
    private LocalDate hireDate;
    private BigDecimal salary;

    public Teacher() {}
    public Teacher(String personId, String firstName, String lastName, String email, String phoneNumber,
            LocalDate dateOfBirth, String memberNumber, Department department, LocalDate joinDate,
            String academicStatus, String institutionalEmail, String advisorName, String employeeNumber,
            String specialization, LocalDate hireDate, BigDecimal salary, String officeNumber, String employmentType) {
        super(personId, firstName, lastName, email, phoneNumber, dateOfBirth, memberNumber, department,
              joinDate, academicStatus, institutionalEmail, advisorName);
        setEmployeeNumber(employeeNumber); setSpecialization(specialization); setHireDate(hireDate);
        setSalary(salary); setOfficeNumber(officeNumber); setEmploymentType(employmentType);
    }
    public String getEmployeeNumber() { return employeeNumber; }
    public void setEmployeeNumber(String v) { if (v == null || v.trim().isEmpty()) throw new IllegalArgumentException("Employee number required"); employeeNumber = v; }
    public String getSpecialization() { return specialization; }
    public void setSpecialization(String v) { if (v == null || v.trim().isEmpty()) throw new IllegalArgumentException("Specialization required"); specialization = v; }
    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate v) { if (v == null) throw new IllegalArgumentException("Hire date required"); hireDate = v; }
    public BigDecimal getSalary() { return salary; }
    public void setSalary(BigDecimal v) { if (v == null || v.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("Salary must be nonnegative"); salary = v; }
    public String getOfficeNumber() { return officeNumber; }
    public void setOfficeNumber(String v) { if (v == null || v.trim().isEmpty()) throw new IllegalArgumentException("Office number required"); officeNumber = v; }
    public String getEmploymentType() { return employmentType; }
    public void setEmploymentType(String v) { if (v == null || v.trim().isEmpty()) throw new IllegalArgumentException("Employment type required"); employmentType = v; }
    @Override public void validateRegistration() throws ValidationException {
        super.validateRegistration();
        if (employeeNumber == null || specialization == null || hireDate == null || salary == null || officeNumber == null || employmentType == null)
            throw new ValidationException("Complete Teacher details");
    }
    @Override public String getRegistrationName() { return getFirstName() + " " + getLastName(); }
    @Override public String getId() { return employeeNumber; }
    @Override public String getStatus() { return getAcademicStatus(); }
    @Override public String toString() {
        return "TEACHER RECORD\n" + super.toString() + "\nEmployee Number: " + employeeNumber +
            "\nSpecialization: " + specialization + "\nHire Date: " + hireDate + "\nSalary: " + salary +
            " RWF\nOffice: " + officeNumber + "\nEmployment Type: " + employmentType;
    }
}
