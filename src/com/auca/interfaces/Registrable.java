package com.auca.interfaces;

public interface Registrable {
    int MIN_NAME_LENGTH = 2;
    int MAX_NAME_LENGTH = 100;
    int MAX_EMAIL_LENGTH = 100;
    int MIN_PHONE_LENGTH = 7;
    int MAX_PHONE_LENGTH = 15;
    String DATE_PATTERN = "yyyy-MM-dd";

    String getRegistrationName();
    void validateRegistration() throws com.auca.exceptions.ValidationException;
}
