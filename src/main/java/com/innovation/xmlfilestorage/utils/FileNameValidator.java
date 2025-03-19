package com.innovation.xmlfilestorage.utils;

import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

@UtilityClass
public class FileNameValidator {

    private final String FILE_EXTENSION = ".xml";

    public static boolean isValid(final String fileName) {

        if (fileName == null || !fileName.endsWith(FILE_EXTENSION)) {
            return false;
        }

        String fileNameWithoutExtension = fileName.substring(0, fileName.length() - FILE_EXTENSION.length());
        String[] parts = fileNameWithoutExtension.split("_");

        if (parts.length != 3) {
            return false;
        }

        String customer = parts[0];
        String type = parts[1];
        String dateStr = parts[2];

        if (!startsWithLetter(customer) || !startsWithLetter(type)) {
            return false;
        }

        try {
            LocalDate.parse(dateStr);
        } catch (DateTimeParseException ex) {
            return false;
        }

        return true;
    }

    private static boolean startsWithLetter(String str) {
        return !str.isBlank() && Character.isLetter(str.charAt(0));
    }

}
