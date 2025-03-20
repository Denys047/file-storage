package com.innovation.filestorage.service;

import com.innovation.filestorage.mapper.JsonMapper;
import com.innovation.filestorage.repository.FileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import static com.innovation.filestorage.common.Constants.*;

@Service
@RequiredArgsConstructor
public class JsonFileService {

    private final FileRepository jsonFileRepository;

    private final JsonMapper jsonMapper;

    public boolean delete(String fileName) {
        return jsonFileRepository.delete(fileName.concat(FILE_EXTENSION_JSON));
    }

    public void save(Path filePath, String json) {
        jsonFileRepository.save(filePath, json);
    }

    public List<Map<String, Object>> getFiles(String customer, String type, String date) {
        return jsonFileRepository.findBy(customer, type, date)
                .stream()
                .map(jsonMapper::parseJson)
                .toList();
    }

}

