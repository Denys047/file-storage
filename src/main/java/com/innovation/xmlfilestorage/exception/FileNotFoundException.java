package com.innovation.xmlfilestorage.exception;

public class FileNotFoundException extends RuntimeException {

    private static final String MESSAGE = "File not found: %s";

    public FileNotFoundException(String message) {
        super(MESSAGE.formatted(message));
    }

}
