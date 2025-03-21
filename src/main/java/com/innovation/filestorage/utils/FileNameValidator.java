package com.innovation.filestorage.utils;

import lombok.AllArgsConstructor;
import lombok.experimental.UtilityClass;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import static com.innovation.filestorage.common.Constants.*;

@UtilityClass
public class FileNameValidator {

    @AllArgsConstructor
    private static class FileNameComponents {

        private String customer;

        private String type;

        private String date;

        public static Optional<FileNameComponents> parseFileName(String fileName) {
            String fileNameWithoutExtension = fileName.substring(0, fileName.length() - FILE_EXTENSION_XML.length());
            String[] parts = fileNameWithoutExtension.split(UNDERSCORE);

            if (parts.length != 3) {
                return Optional.empty();
            }
            return Optional.of(new FileNameComponents(parts[0], parts[1], parts[2]));
        }

    }

    public static boolean isValid(final String fileName) {
        if ((Objects.isNull(fileName)) || !fileName.endsWith(FILE_EXTENSION_XML)) {
            return false;
        }
        return FileNameComponents.parseFileName(fileName)
                .filter(validate())
                .isPresent();
    }

    private Predicate<FileNameComponents> validate() {
        return components ->
                startsWithLetter(components.customer) &&
                        startsWithLetter(components.type) &&
                        isValidDate(components.date);
    }

    private static boolean startsWithLetter(String str) {
        return !str.isBlank() && Character.isLetter(str.charAt(0));
    }

    private static boolean isValidDate(final String date) {
        try {
            LocalDate.parse(date);
        } catch (DateTimeParseException ex) {
            return false;
        }
        return true;
    }

}
