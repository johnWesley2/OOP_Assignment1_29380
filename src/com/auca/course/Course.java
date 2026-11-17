package com.auca.course;

import com.auca.teacher.Teacher;
import com.auca.department.Department;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.exceptions.ValidationException;

public class Course implements Identifiable, Registrable {
    private String courseId, courseCode, courseTitle;
    private int creditHours;
    private Department department;
    private Teacher assignedTeacher;

    public Course() {}
    public Course(String id, String code, String title, int hours, Department department, Teacher teacher) {
        setCourseId(id); setCourseCode(code); setCourseTitle(title); setCreditHours(hours);
        setDepartment(department); setAssignedTeacher(teacher);
    }
    public String getCourseId() { return courseId; }
    public void setCourseId(String v) { if (v == null || v.trim().isEmpty()) throw new IllegalArgumentException("Value cannot be empty"); courseId = v; }
    public String getCourseCode() { return courseCode; }
    public void setCourseCode(String v) { if (v == null || v.trim().isEmpty()) throw new IllegalArgumentException("Value cannot be empty"); courseCode = v; }
    public String getCourseTitle() { return courseTitle; }
    public void setCourseTitle(String v) { if (v == null || v.trim().length() < 2) throw new IllegalArgumentException("Value must contain at least 2 characters"); courseTitle = v; }
    public int getCreditHours() { return creditHours; }
    public void setCreditHours(int v) { if (v < 0) throw new IllegalArgumentException("creditHours cannot be negative"); creditHours = v; }
    public Department getDepartment() { return department; }
    public void setDepartment(Department v) { if (v == null) throw new IllegalArgumentException("department reference is required"); department = v; }
    public Teacher getAssignedTeacher() { return assignedTeacher; }
    public void setAssignedTeacher(Teacher v) { if (v == null) throw new IllegalArgumentException("assignedTeacher reference is required"); assignedTeacher = v; }
    @Override public String getId() { return courseId; }
    @Override public String getStatus() { return Identifiable.DEFAULT_STATUS; }
    @Override public String getRegistrationName() { return courseTitle; }
    @Override public void validateRegistration() throws ValidationException {
        if (courseId == null || courseTitle == null) throw new ValidationException("Course ID and title required");
        if (creditHours <= 0) throw new ValidationException("Credit hours must be positive");
    }
    @Override public String toString() {
        return "COURSE RECORD\nCourse ID: " + courseId + "\nCode: " + courseCode + "\nTitle: " + courseTitle +
            "\nCredit Hours: " + creditHours + "\nDepartment: " + department.getDepartmentName() +
            "\nAssigned Teacher: " + assignedTeacher.getFirstName() + " " + assignedTeacher.getLastName();
    }
}
