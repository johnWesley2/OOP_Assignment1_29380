package com.auca.librarybook;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.exceptions.ValidationException;

public class LibraryBook implements Identifiable, Registrable {
    private String bookId, isbn, title, author;
    private int totalCopies, availableCopies;

    public LibraryBook() {}
    public LibraryBook(String id, String isbn, String title, String author, int total, int available) {
        setBookId(id); setIsbn(isbn); setTitle(title); setAuthor(author);
        setTotalCopies(total); setAvailableCopies(available);
    }
    public String getBookId() { return bookId; }
    public void setBookId(String v) { if (v == null || v.trim().isEmpty()) throw new IllegalArgumentException("Value cannot be empty"); bookId = v; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String v) { if (v == null || v.trim().isEmpty()) throw new IllegalArgumentException("Value cannot be empty"); isbn = v; }
    public String getTitle() { return title; }
    public void setTitle(String v) { if (v == null || v.trim().length() < 2) throw new IllegalArgumentException("Value must contain at least 2 characters"); title = v; }
    public String getAuthor() { return author; }
    public void setAuthor(String v) { if (v == null || v.trim().length() < 2) throw new IllegalArgumentException("Value must contain at least 2 characters"); author = v; }
    public int getTotalCopies() { return totalCopies; }
    public void setTotalCopies(int v) { if (v < 0) throw new IllegalArgumentException("totalCopies cannot be negative"); totalCopies = v; }
    public int getAvailableCopies() { return availableCopies; }
    public void setAvailableCopies(int v) { if (v < 0) throw new IllegalArgumentException("availableCopies cannot be negative"); availableCopies = v; }
    @Override public String getId() { return bookId; }
    @Override public String getStatus() { return Identifiable.DEFAULT_STATUS; }
    @Override public String getRegistrationName() { return title; }
    @Override public void validateRegistration() throws ValidationException {
        if (bookId == null || title == null) throw new ValidationException("Book ID and title required");
        if (totalCopies <= 0 || availableCopies < 0 || availableCopies > totalCopies)
            throw new ValidationException("Book copy count invalid");
    }
    @Override public String toString() {
        return "LIBRARY BOOK RECORD\nBook ID: " + bookId + "\nISBN: " + isbn + "\nTitle: " + title +
            "\nAuthor: " + author + "\nTotal Copies: " + totalCopies + "\nAvailable Copies: " + availableCopies;
    }
}
