package com.innovation.xmlfilestorage.controller;

import com.innovation.xmlfilestorage.dto.ApiResponse;
import com.innovation.xmlfilestorage.dto.FileUploadResponse;
import com.innovation.xmlfilestorage.exception.InvalidFileNameException;
import com.innovation.xmlfilestorage.service.JsonFileService;
import com.innovation.xmlfilestorage.service.XmlFileService;
import com.innovation.xmlfilestorage.utils.FileNameValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

import static com.innovation.xmlfilestorage.common.Constants.*;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class FileController {

    private final XmlFileService xmlFileService;

    private final JsonFileService jsonFileService;

    @PostMapping("/upload")
    public ResponseEntity<ApiResponse<FileUploadResponse>> upload(@RequestParam("file") MultipartFile multipartFile) {
        var fileName = multipartFile.getOriginalFilename();
        validateFileName(fileName);
        xmlFileService.upload(fileName, multipartFile);

        return ResponseEntity.ok(ApiResponse.success(new FileUploadResponse(fileName, multipartFile.getSize())));
    }

    @PutMapping("/upload")
    public ResponseEntity<Void> update(@RequestParam("file") MultipartFile multipartFile) {
        var fileName = multipartFile.getOriginalFilename();
        validateFileName(fileName);
        xmlFileService.update(fileName, multipartFile);

        return ResponseEntity.ok().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<ApiResponse<List<Map<String, Object>>>> getFiles(@RequestParam(required = false) String customer,
                                                                           @RequestParam(required = false) String type,
                                                                           @RequestParam(required = false) String date) {

        return ResponseEntity.ok(ApiResponse.success((jsonFileService.getFiles(customer, type, date))));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteByName(@RequestParam("fileName") String fileName) {
        return jsonFileService.delete(fileName) ?
                ResponseEntity.ok().build() :
                ResponseEntity.notFound().build();
    }

    private void validateFileName(String fileName) {
        if (!FileNameValidator.isValid(fileName)) {
            throw new InvalidFileNameException(INVALID_FILE_NAME_FORMAT);
        }
    }

}
