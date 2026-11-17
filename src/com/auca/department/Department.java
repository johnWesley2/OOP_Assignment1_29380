package com.auca.department;

import com.auca.teacher.Teacher;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.exceptions.ValidationException;

public class Department implements Identifiable, Registrable {
    private String departmentId, departmentName, departmentCode, officeLocation, contactEmail;
    private Teacher headTeacher;

    public Department() {}
    public Department(String id, String name, String code, String office, String email, Teacher head) {
        setDepartmentId(id); setDepartmentName(name); setDepartmentCode(code);
        setOfficeLocation(office); setContactEmail(email); setHeadTeacher(head);
    }
    public String getDepartmentId() { return departmentId; }
    public void setDepartmentId(String v) { if (v == null || v.trim().isEmpty()) throw new IllegalArgumentException("Value cannot be empty"); departmentId = v; }
    public String getDepartmentName() { return departmentName; }
    public void setDepartmentName(String v) { if (v == null || v.trim().length() < 2) throw new IllegalArgumentException("Value must contain at least 2 characters"); departmentName = v; }
    public String getDepartmentCode() { return departmentCode; }
    public void setDepartmentCode(String v) { if (v == null || v.trim().length() < 2) throw new IllegalArgumentException("Value must contain at least 2 characters"); departmentCode = v; }
    public String getOfficeLocation() { return officeLocation; }
    public void setOfficeLocation(String v) { if (v == null || v.trim().length() < 2) throw new IllegalArgumentException("Value must contain at least 2 characters"); officeLocation = v; }
    public String getContactEmail() { return contactEmail; }
    public void setContactEmail(String v) { if (v == null || v.trim().isEmpty() || !v.matches("^[\\w.+-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) throw new IllegalArgumentException("Invalid email"); contactEmail = v; }
    public Teacher getHeadTeacher() { return headTeacher; }
    public void setHeadTeacher(Teacher v) { headTeacher = v; }
    @Override public String getId() { return departmentId; }
    @Override public String getStatus() { return Identifiable.DEFAULT_STATUS; }
    @Override public String getRegistrationName() { return departmentName; }
    @Override public void validateRegistration() throws ValidationException {
        if (departmentId == null || departmentName == null) throw new ValidationException("Department ID and name required");
        if (headTeacher == null) throw new ValidationException("Head teacher required");
    }
    @Override public String toString() {
        return "DEPARTMENT RECORD\nDepartment ID: " + departmentId + "\nName: " + departmentName +
            "\nCode: " + departmentCode + "\nOffice: " + officeLocation + "\nContact Email: " + contactEmail +
            "\nHead Teacher: " + (headTeacher == null ? "Not assigned" : headTeacher.getFirstName() + " " + headTeacher.getLastName());
    }
}
