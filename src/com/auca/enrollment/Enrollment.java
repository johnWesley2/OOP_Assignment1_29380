package com.auca.enrollment;

import com.auca.student.Student;
import com.auca.course.Course;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.exceptions.ValidationException;
import java.time.LocalDate;

public class Enrollment implements Identifiable, Registrable {
    private String enrollmentId, semester, enrollmentStatus;
    private Student student;
    private Course course;
    private LocalDate enrollmentDate;

    public Enrollment() {}
    public Enrollment(String id, Student student, Course course, LocalDate date, String semester, String status) {
        setEnrollmentId(id); setStudent(student); setCourse(course); setEnrollmentDate(date);
        setSemester(semester); setEnrollmentStatus(status);
    }
    public String getEnrollmentId() { return enrollmentId; }
    public void setEnrollmentId(String v) { if (v == null || v.trim().isEmpty()) throw new IllegalArgumentException("Value cannot be empty"); enrollmentId = v; }
    public Student getStudent() { return student; }
    public void setStudent(Student v) { if (v == null) throw new IllegalArgumentException("student reference is required"); student = v; }
    public Course getCourse() { return course; }
    public void setCourse(Course v) { if (v == null) throw new IllegalArgumentException("course reference is required"); course = v; }
    public LocalDate getEnrollmentDate() { return enrollmentDate; }
    public void setEnrollmentDate(LocalDate v) { if (v == null) throw new IllegalArgumentException("enrollmentDate is required"); enrollmentDate = v; }
    public String getSemester() { return semester; }
    public void setSemester(String v) { if (v == null || v.trim().length() < 2) throw new IllegalArgumentException("Value must contain at least 2 characters"); semester = v; }
    public String getEnrollmentStatus() { return enrollmentStatus; }
    public void setEnrollmentStatus(String v) { if (v == null || v.trim().length() < 2) throw new IllegalArgumentException("Value must contain at least 2 characters"); enrollmentStatus = v; }
    @Override public String getId() { return enrollmentId; }
    @Override public String getStatus() { return Identifiable.DEFAULT_STATUS; }
    @Override public String getRegistrationName() { return enrollmentId; }
    @Override public void validateRegistration() throws ValidationException {
        if (enrollmentId == null || student == null || course == null || enrollmentDate == null)
            throw new ValidationException("Enrollment references and date required");
    }
    @Override public String toString() {
        return "ENROLLMENT RECORD\nEnrollment ID: " + enrollmentId + "\nStudent: " +
            student.getFirstName() + " " + student.getLastName() + " (" + student.getStudentNumber() + ")" +
            "\nCourse: " + course.getCourseTitle() + "\nDate: " + enrollmentDate +
            "\nSemester: " + semester + "\nStatus: " + enrollmentStatus;
    }
}
