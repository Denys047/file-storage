package com.innovation.xmlfilestorage.exception;

public class XmlParsingException extends RuntimeException {

    private static final String MESSAGE = "Failed to parse XML file: %s";

    public XmlParsingException(String fileName) {
        super(MESSAGE.formatted(fileName));
    }

}
