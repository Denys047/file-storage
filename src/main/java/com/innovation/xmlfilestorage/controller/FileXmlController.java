package com.innovation.xmlfilestorage.controller;

import com.innovation.xmlfilestorage.dto.FileUploadResponse;
import com.innovation.xmlfilestorage.exception.InvalidFileNameException;
import com.innovation.xmlfilestorage.service.XmlFileProcessorService;
import com.innovation.xmlfilestorage.utils.FileNameValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/file-processing")
@RequiredArgsConstructor
public class FileXmlController {

    private final XmlFileProcessorService xmlFileProcessorService;

    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> upload(@RequestParam("file") MultipartFile multipartFile) {
        var fileName = multipartFile.getOriginalFilename();

        if (!FileNameValidator.isValid(fileName)) {
            throw new InvalidFileNameException();
        }

        xmlFileProcessorService.save(fileName, multipartFile);

        return ResponseEntity.ok(new FileUploadResponse(fileName, multipartFile.getSize()));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteByName(@RequestParam("fileName") String fileName) {
        return xmlFileProcessorService.delete(fileName) ?
                ResponseEntity.ok().build() :
                ResponseEntity.notFound().build();
    }

}
