package com.innovation.xmlfilestorage.mapper;

import org.springframework.stereotype.Component;

import static com.innovation.xmlfilestorage.common.Constants.FILE_EXTENSION_JSON;
import static com.innovation.xmlfilestorage.common.Constants.FILE_EXTENSION_XML;

@Component
public class FileExtensionMapper {

    public String convertXmlToJso(String fileName) {
        return fileName.replace(FILE_EXTENSION_XML, FILE_EXTENSION_JSON);
    }

}
