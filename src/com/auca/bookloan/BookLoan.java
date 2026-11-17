package com.auca.bookloan;

import com.auca.student.Student;
import com.auca.librarybook.LibraryBook;
import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.exceptions.ValidationException;
import java.time.LocalDate;

public class BookLoan implements Identifiable, Registrable {
    private String loanId;
    private Student student;
    private LibraryBook book;
    private LocalDate borrowedDate, dueDate, returnedDate;

    public BookLoan() {}
    public BookLoan(String id, Student student, LibraryBook book, LocalDate borrowed, LocalDate due, LocalDate returned) {
        setLoanId(id); setStudent(student); setBook(book); setBorrowedDate(borrowed);
        setDueDate(due); setReturnedDate(returned);
    }
    public String getLoanId() { return loanId; }
    public void setLoanId(String v) { if (v == null || v.trim().isEmpty()) throw new IllegalArgumentException("Value cannot be empty"); loanId = v; }
    public Student getStudent() { return student; }
    public void setStudent(Student v) { if (v == null) throw new IllegalArgumentException("student reference is required"); student = v; }
    public LibraryBook getBook() { return book; }
    public void setBook(LibraryBook v) { if (v == null) throw new IllegalArgumentException("book reference is required"); book = v; }
    public LocalDate getBorrowedDate() { return borrowedDate; }
    public void setBorrowedDate(LocalDate v) { if (v == null) throw new IllegalArgumentException("borrowedDate is required"); borrowedDate = v; }
    public LocalDate getDueDate() { return dueDate; }
    public void setDueDate(LocalDate v) { if (v == null) throw new IllegalArgumentException("dueDate is required"); dueDate = v; }
    public LocalDate getReturnedDate() { return returnedDate; }
    public void setReturnedDate(LocalDate v) { returnedDate = v; }
    @Override public String getId() { return loanId; }
    @Override public String getStatus() { return Identifiable.DEFAULT_STATUS; }
    @Override public String getRegistrationName() { return loanId; }
    @Override public void validateRegistration() throws ValidationException {
        if (loanId == null || student == null || book == null || borrowedDate == null || dueDate == null ||
            dueDate.isBefore(borrowedDate) || (returnedDate != null && returnedDate.isBefore(borrowedDate)))
            throw new ValidationException("Loan dates or references invalid");
    }
    @Override public String toString() {
        return "BOOK LOAN RECORD\nLoan ID: " + loanId + "\nStudent: " +
            student.getFirstName() + " " + student.getLastName() + " (" + student.getStudentNumber() + ")" +
            "\nBook: " + book.getTitle() + "\nBorrowed Date: " + borrowedDate +
            "\nDue Date: " + dueDate + "\nReturned Date: " + (returnedDate == null ? "Not returned" : returnedDate);
    }
}
