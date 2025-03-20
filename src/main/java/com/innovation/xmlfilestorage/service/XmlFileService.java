package com.innovation.xmlfilestorage.service;

import com.innovation.xmlfilestorage.exception.ResourceAlreadyExistsException;
import com.innovation.xmlfilestorage.exception.ResourceNotFoundException;
import com.innovation.xmlfilestorage.mapper.FileExtensionMapper;
import com.innovation.xmlfilestorage.mapper.JsonMapper;
import com.innovation.xmlfilestorage.utils.FileUploadDirectoryInitializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;

import static com.innovation.xmlfilestorage.common.Constants.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class XmlFileService {

    private final FileUploadDirectoryInitializer directory;

    private final JsonFileService jsonFileService;

    private final JsonMapper jsonMapper;

    private final FileExtensionMapper fileExtensionMapper;

    public void upload(String fileName, MultipartFile multipartFile) {
        Path filePath = directory.getUploadDirectory().resolve(fileExtensionMapper.convertXmlToJso(fileName));

        if (Files.exists(filePath)) {
            throw new ResourceAlreadyExistsException(ALREADY_EXISTS_FILE.formatted(fileName));
        }

        var json = jsonMapper.xmlToJson(multipartFile);
        jsonFileService.save(filePath, json);
    }

    public void update(String fileName, MultipartFile multipartFile) {
        Path filePath = directory.getUploadDirectory().resolve(fileExtensionMapper.convertXmlToJso(fileName));
        if (!Files.exists(filePath)) {
            throw new ResourceNotFoundException(NOT_FOUND_FILE.formatted(fileName));
        }
        var json = jsonMapper.xmlToJson(multipartFile);
        jsonFileService.save(filePath, json);
    }

}

