package com.auca.feepayment;

import com.auca.student.Student;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.exceptions.ValidationException;
import java.time.LocalDate;
import java.math.BigDecimal;

public class FeePayment implements Identifiable, Registrable {
    private String paymentId, paymentMethod, receiptNumber;
    private Student student;
    private BigDecimal amount;
    private LocalDate paymentDate;

    public FeePayment() {}
    public FeePayment(String id, Student student, BigDecimal amount, LocalDate date, String method, String receipt) {
        setPaymentId(id); setStudent(student); setAmount(amount); setPaymentDate(date);
        setPaymentMethod(method); setReceiptNumber(receipt);
    }
    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String v) { if (v == null || v.trim().isEmpty()) throw new IllegalArgumentException("Value cannot be empty"); paymentId = v; }
    public Student getStudent() { return student; }
    public void setStudent(Student v) { if (v == null) throw new IllegalArgumentException("student reference is required"); student = v; }
    public BigDecimal getAmount() { return amount; }
    public void setAmount(BigDecimal v) { if (v == null || v.compareTo(BigDecimal.ZERO) < 0) throw new IllegalArgumentException("amount must be nonnegative"); amount = v; }
    public LocalDate getPaymentDate() { return paymentDate; }
    public void setPaymentDate(LocalDate v) { if (v == null) throw new IllegalArgumentException("paymentDate is required"); paymentDate = v; }
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String v) { if (v == null || v.trim().length() < 2) throw new IllegalArgumentException("Value must contain at least 2 characters"); paymentMethod = v; }
    public String getReceiptNumber() { return receiptNumber; }
    public void setReceiptNumber(String v) { if (v == null || v.trim().isEmpty()) throw new IllegalArgumentException("Value cannot be empty"); receiptNumber = v; }
    @Override public String getId() { return paymentId; }
    @Override public String getStatus() { return Identifiable.DEFAULT_STATUS; }
    @Override public String getRegistrationName() { return paymentId; }
    @Override public void validateRegistration() throws ValidationException {
        if (paymentId == null || student == null || paymentDate == null || amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
            throw new ValidationException("Payment details invalid");
    }
    @Override public String toString() {
        return "FEE PAYMENT RECORD\nPayment ID: " + paymentId + "\nStudent: " +
            student.getFirstName() + " " + student.getLastName() + " (" + student.getStudentNumber() + ")" +
            "\nAmount: " + amount + " RWF\nPayment Date: " + paymentDate +
            "\nMethod: " + paymentMethod + "\nReceipt: " + receiptNumber;
    }
}
