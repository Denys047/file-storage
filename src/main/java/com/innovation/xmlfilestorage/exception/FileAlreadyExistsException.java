package com.innovation.xmlfilestorage.exception;

public class FileAlreadyExistsException extends RuntimeException {

    private static final String MESSAGE = "File with the same name already exists: %s";

    public FileAlreadyExistsException(String fileName) {
        super(MESSAGE.formatted(fileName));
    }

}
