package com.innovation.filestorage.common;

import lombok.experimental.UtilityClass;

/**
 * Constant variables used in the project
 */

@UtilityClass
public class Constants {

    public static final String ALREADY_EXISTS_FILE = "File with the same name already exists: %s";
    public static final String NOT_FOUND_FILE = "File not found: %s";

    public static final String INVALID_XML_FORMAT = "Failed to parse XML file";
    public static final String INVALID_FILE_NAME_FORMAT = "Invalid file name format! Expected: customer_type_date.xml";

    public static final String FILE_EXTENSION_XML = ".xml";
    public static final String FILE_EXTENSION_JSON = ".json";

    public static final String UNDERSCORE = "_";
    public static final String EMPTY_STRING = "";

    public static final String DEFAULT_API_RESPONSE_STATUS = "success";

}
