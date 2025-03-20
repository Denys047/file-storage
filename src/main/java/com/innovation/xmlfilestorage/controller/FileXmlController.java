package com.innovation.xmlfilestorage.controller;

import com.innovation.xmlfilestorage.dto.FileUploadResponse;
import com.innovation.xmlfilestorage.exception.InvalidFileNameException;
import com.innovation.xmlfilestorage.service.XmlFileProcessorService;
import com.innovation.xmlfilestorage.utils.FileNameValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/file-processing")
@RequiredArgsConstructor
public class FileXmlController {

    private final XmlFileProcessorService xmlFileProcessorService;

    @GetMapping
    public ResponseEntity<String> getFileByName(@RequestParam("fileName") String fileName) {
        if (!FileNameValidator.isValid(fileName)) {
            throw new InvalidFileNameException();
        }

        var json = xmlFileProcessorService.getFileByName(fileName);
        return ResponseEntity.ok(json);
    }

    @GetMapping("/files")
    public ResponseEntity<List<String>> getFiles(@RequestParam(required = false) String customer,
                                                 @RequestParam(required = false) String type,
                                                 @RequestParam(required = false) String date) {
        return ResponseEntity.ok(xmlFileProcessorService.getFiles(customer, type, date));
    }

    @PostMapping("/upload")
    public ResponseEntity<FileUploadResponse> upload(@RequestParam("file") MultipartFile multipartFile) {
        var fileName = multipartFile.getOriginalFilename();

        if (!FileNameValidator.isValid(fileName)) {
            throw new InvalidFileNameException();
        }

        xmlFileProcessorService.save(fileName, multipartFile);

        return ResponseEntity.ok(new FileUploadResponse(fileName, multipartFile.getSize()));
    }

    @PutMapping("/upload")
    public ResponseEntity<Void> update(@RequestParam("file") MultipartFile multipartFile) {
        var fileName = multipartFile.getOriginalFilename();

        if (!FileNameValidator.isValid(fileName)) {
            throw new InvalidFileNameException();
        }

        return xmlFileProcessorService.update(fileName, multipartFile) ?
                ResponseEntity.ok().build() :
                ResponseEntity.notFound().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteByName(@RequestParam("fileName") String fileName) {
        return xmlFileProcessorService.delete(fileName) ?
                ResponseEntity.ok().build() :
                ResponseEntity.notFound().build();
    }

}
