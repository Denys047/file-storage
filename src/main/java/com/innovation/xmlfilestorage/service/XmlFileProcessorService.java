package com.innovation.xmlfilestorage.service;

import com.innovation.xmlfilestorage.exception.FileAlreadyExistsException;
import com.innovation.xmlfilestorage.exception.FileNotFoundException;
import com.innovation.xmlfilestorage.exception.XmlParsingException;
import com.innovation.xmlfilestorage.utils.FileUploadDirectoryInitializer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Slf4j
@Service
@RequiredArgsConstructor
public class XmlFileProcessorService {

    private final FileUploadDirectoryInitializer directory;

    @Value("${json.indent-factor}")
    private int jsonIndentFactor;

    public void save(String fileName, MultipartFile multipartFile) {
        Path filePath = directory.getUploadDirectory().resolve(convertXmlToJsonFileName(fileName));

        if (Files.exists(filePath)) {
            throw new FileAlreadyExistsException(fileName);
        }

        toJson(filePath, fileName, multipartFile);
    }

    public String getFileByName(String fileName) {
        Path resolve = directory.getUploadDirectory().resolve(convertXmlToJsonFileName(fileName));
        try {
            return new String(Files.readAllBytes(resolve));
        } catch (IOException e) {
            throw new FileNotFoundException(fileName);
        }
    }

    public List<String> getFiles(String customer, String type, String date) {
        List<Path> paths = findFilesByParams(customer, type, date);
        return paths.stream().map(it -> getFileByName(it.getFileName().toString())).collect(Collectors.toList());
    }

    private List<Path> findFilesByParams(String customer, String type, String date) {
        Path uploadDirectory = directory.getUploadDirectory();
        List<Path> result = List.of();
        try (Stream<Path> filesPath = Files.find(uploadDirectory,
                Integer.MAX_VALUE,
                ((path, basicFileAttributes) ->
                        isValid(path.getFileName().toString(), customer, type, date)))) {
            result = filesPath.collect(Collectors.toList());
        } catch (IOException e) {
            e.getMessage();
        }
        return result;
    }

    private boolean isValid(String fileName, String customer, String type, String date) {
        boolean matches = true;
        var file = fileName.replace(".json", "");
        if (customer != null) {
            matches &= file.startsWith(customer);
        }
        if (type != null) {
            matches &= file.contains(type);
        }
        if (date != null) {
            matches &= file.endsWith(date);
        }
        return matches;
    }


    public boolean update(String fileName, MultipartFile multipartFile) {
        Path filePath = directory.getUploadDirectory().resolve(convertXmlToJsonFileName(fileName));
        if (Files.exists(filePath)) {
            toJson(filePath, fileName, multipartFile);
            return true;
        }
        return false;
    }

    public boolean delete(String fileName) {
        return directory.getUploadDirectory()
                .resolve(fileName.concat(".json"))
                .toFile()
                .delete();
    }

    private void toJson(Path filePath, String fileName, MultipartFile multipartFile) {
        try {
            JSONObject json = XML.toJSONObject(new String(multipartFile.getBytes()));
            Files.writeString(filePath, json.toString(jsonIndentFactor));
        } catch (IOException | JSONException e) {
            log.error("Failed to parse XML file: {}", fileName, e);
            throw new XmlParsingException(fileName);
        }
    }

    private String convertXmlToJsonFileName(String fileName) {
        return fileName.replace(".xml", ".json");
    }
}

