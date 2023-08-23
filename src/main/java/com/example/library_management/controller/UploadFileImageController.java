package com.example.library_management.controller;

import com.example.library_management.model.response.UploadFileImageResponse;
import com.example.library_management.service.UploadFileImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UploadFileImageController {
    private final UploadFileImageService fileImageService;

    @PostMapping(value = "/files", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadFileImageResponse> uploadFile(@RequestParam MultipartFile file) throws IOException {
        UploadFileImageResponse fileName = fileImageService.uploadFile(file);
        return ResponseEntity.ok(fileName);
    }

}
