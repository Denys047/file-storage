package com.innovation.filestorage.utils;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Component
public class FileUploadDirectoryInitializer {

    @Value("${file.storage.dir.name}")
    private String uploadDir;

    @Getter
    private Path uploadDirectory;

    @PostConstruct
    public void init() throws IOException {
        uploadDirectory = Paths.get(uploadDir);
        if (Files.notExists(uploadDirectory)) {
            Files.createDirectories(uploadDirectory);
        }
    }

}
