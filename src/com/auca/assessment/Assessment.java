package com.auca.assessment;

import com.auca.enrollment.Enrollment;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.exceptions.ValidationException;
import java.time.LocalDate;

public class Assessment implements Identifiable, Registrable {
    private String assessmentId, assessmentType;
    private Enrollment enrollment;
    private double maximumScore, obtainedScore;
    private LocalDate assessmentDate;

    public Assessment() {}
    public Assessment(String id, Enrollment enrollment, String type, double max, double obtained, LocalDate date) {
        setAssessmentId(id); setEnrollment(enrollment); setAssessmentType(type);
        setMaximumScore(max); setObtainedScore(obtained); setAssessmentDate(date);
    }
    public String getAssessmentId() { return assessmentId; }
    public void setAssessmentId(String v) { if (v == null || v.trim().isEmpty()) throw new IllegalArgumentException("Value cannot be empty"); assessmentId = v; }
    public Enrollment getEnrollment() { return enrollment; }
    public void setEnrollment(Enrollment v) { if (v == null) throw new IllegalArgumentException("enrollment reference is required"); enrollment = v; }
    public String getAssessmentType() { return assessmentType; }
    public void setAssessmentType(String v) { if (v == null || v.trim().length() < 2) throw new IllegalArgumentException("Value must contain at least 2 characters"); assessmentType = v; }
    public double getMaximumScore() { return maximumScore; }
    public void setMaximumScore(double v) { if (!Double.isFinite(v) || v < 0) throw new IllegalArgumentException("maximumScore must be nonnegative"); maximumScore = v; }
    public double getObtainedScore() { return obtainedScore; }
    public void setObtainedScore(double v) { if (!Double.isFinite(v) || v < 0) throw new IllegalArgumentException("obtainedScore must be nonnegative"); obtainedScore = v; }
    public LocalDate getAssessmentDate() { return assessmentDate; }
    public void setAssessmentDate(LocalDate v) { if (v == null) throw new IllegalArgumentException("assessmentDate is required"); assessmentDate = v; }
    @Override public String getId() { return assessmentId; }
    @Override public String getStatus() { return Identifiable.DEFAULT_STATUS; }
    @Override public String getRegistrationName() { return assessmentId; }
    @Override public void validateRegistration() throws ValidationException {
        if (assessmentId == null || enrollment == null || assessmentDate == null || maximumScore <= 0 || obtainedScore > maximumScore)
            throw new ValidationException("Assessment score or references invalid");
    }
    @Override public String toString() {
        return "ASSESSMENT RECORD\nAssessment ID: " + assessmentId + "\nEnrollment: " + enrollment.getEnrollmentId() +
            "\nType: " + assessmentType + "\nMaximum Score: " + maximumScore +
            "\nObtained Score: " + obtainedScore + "\nDate: " + assessmentDate;
    }
}
