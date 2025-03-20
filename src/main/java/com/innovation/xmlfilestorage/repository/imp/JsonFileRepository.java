package com.innovation.xmlfilestorage.repository.imp;

import com.innovation.xmlfilestorage.exception.ResourceNotFoundException;
import com.innovation.xmlfilestorage.repository.FileRepository;
import com.innovation.xmlfilestorage.utils.FileUploadDirectoryInitializer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.stream.Stream;

import static com.innovation.xmlfilestorage.common.Constants.*;

@Repository
@RequiredArgsConstructor
public class JsonFileRepository implements FileRepository {

    private final FileUploadDirectoryInitializer directory;

    @Override
    public void save(Path filePath, String file) {
        write(filePath, file);
    }

    @Override
    public List<String> findBy(String customer, String type, String date) {
        return findPathFiles(customer, type, date)
                .stream()
                .map(path -> findBy(path.toFile().getName()))
                .toList();
    }

    @Override
    public String findBy(String fileName) {
        return readAll(fileName, directory.getUploadDirectory().resolve(fileName));
    }

    @Override
    public boolean delete(String fileName) {
        return directory.getUploadDirectory().resolve(fileName).toFile().delete();
    }

    private List<Path> findPathFiles(String customer, String type, String date) {
        try (Stream<Path> filesPath = Files.find(
                directory.getUploadDirectory(),
                Integer.MAX_VALUE,
                ((path, attrs) -> matchesFilter(path.getFileName().toString(), customer, type, date)))) {
            return filesPath.toList();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean matchesFilter(String fileName, String customer, String type, String date) {
        boolean matches = true;
        var file = fileName.replace(FILE_EXTENSION_JSON, EMPTY_STRING);
        if (Objects.nonNull(customer)) {
            matches &= file.startsWith(customer);
        }
        if (Objects.nonNull(type)) {
            matches &= file.contains(type);
        }
        if (Objects.nonNull(date)) {
            matches &= file.endsWith(date);
        }
        return matches;
    }

    private void write(Path filePath, String file) {
        try {
            Files.writeString(filePath, file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String readAll(String fileName, Path filePath) {
        try {
            return new String(Files.readAllBytes(filePath));
        } catch (IOException e) {
            throw new ResourceNotFoundException(NOT_FOUND_FILE.formatted(fileName));
        }
    }

}
