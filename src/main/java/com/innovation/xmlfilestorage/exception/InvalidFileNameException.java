package com.innovation.xmlfilestorage.exception;

public class InvalidFileNameException extends RuntimeException {

    private static final String MESSAGE = "Invalid file name format! Expected: customer_type_date.xml";

    public InvalidFileNameException() {
        super(MESSAGE);
    }

}
