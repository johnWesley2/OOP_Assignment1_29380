package com.auca.abstracts;

import com.auca.interfaces.Identifiable;
import com.auca.interfaces.Registrable;
import com.auca.exceptions.ValidationException;
import java.time.LocalDate;

public abstract class Person implements Identifiable, Registrable {
    private String personId, firstName, lastName, email, phoneNumber;
    private LocalDate dateOfBirth;

    public Person() {}

    public Person(String personId, String firstName, String lastName, String email,
                  String phoneNumber, LocalDate dateOfBirth) {
        setPersonId(personId); setFirstName(firstName); setLastName(lastName);
        setEmail(email); setPhoneNumber(phoneNumber); setDateOfBirth(dateOfBirth);
    }

    public String getPersonId() { return personId; }
    public void setPersonId(String v) {
        if (v == null || v.trim().isEmpty()) throw new IllegalArgumentException("Person ID required");
        personId = v;
    }
    public String getFirstName() { return firstName; }
    public void setFirstName(String v) {
        if (v == null || v.trim().length() < 2) throw new IllegalArgumentException("First name must have at least 2 characters");
        firstName = v.trim();
    }
    public String getLastName() { return lastName; }
    public void setLastName(String v) {
        if (v == null || v.trim().length() < 2) throw new IllegalArgumentException("Last name must have at least 2 characters");
        lastName = v.trim();
    }
    public String getEmail() { return email; }
    public void setEmail(String v) {
        if (v == null || !v.matches("^[\\w.+-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) throw new IllegalArgumentException("Invalid email");
        email = v;
    }
    public String getPhoneNumber() { return phoneNumber; }
    public void setPhoneNumber(String v) {
        if (v == null || !v.matches("\\+?[0-9]{7,15}")) throw new IllegalArgumentException("Phone must contain 7-15 digits");
        phoneNumber = v;
    }
    public LocalDate getDateOfBirth() { return dateOfBirth; }
    public void setDateOfBirth(LocalDate v) {
        if (v == null || v.isAfter(LocalDate.now())) throw new IllegalArgumentException("Invalid date of birth");
        dateOfBirth = v;
    }

    @Override public String getId() { return personId; }
    @Override public String getStatus() { return Identifiable.DEFAULT_STATUS; }
    @Override public String getRegistrationName() { return firstName + " " + lastName; }

    @Override public void validateRegistration() throws ValidationException {
        if (personId == null || firstName == null || lastName == null || email == null ||
            phoneNumber == null || dateOfBirth == null) throw new ValidationException("Complete all Person details");
    }

    @Override public String toString() {
        return "Person ID: " + personId + "\nName: " + firstName + " " + lastName +
               "\nEmail: " + email + "\nPhone: " + phoneNumber + "\nDate of Birth: " + dateOfBirth;
    }
}
