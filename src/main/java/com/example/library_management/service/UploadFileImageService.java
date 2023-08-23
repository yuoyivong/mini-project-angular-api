package com.example.library_management.service;

import com.example.library_management.model.response.UploadFileImageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UploadFileImageService {

    private final Path root = Paths.get("src/main/resources/images");
    public UploadFileImageResponse uploadFile(MultipartFile file) throws IOException {
        try {
//            get file name
            String fileName = file.getOriginalFilename();
            if(fileName!=null &&
                fileName.contains(".jpg") ||
                    fileName.contains(".png") ||
                    fileName.contains(".jpeg")
            ) {
                fileName = UUID.randomUUID() + "." + StringUtils.getFilenameExtension(fileName);
                if(!Files.exists(root)) {
                    Files.createDirectories(root);
                }

                Files.copy(file.getInputStream(), root.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                return UploadFileImageResponse.builder()
                        .message("Upload file successfully.")
                        .status(HttpStatus.OK.value())
                        .payload(fileName)
                        .build();
            } else {
                return UploadFileImageResponse.builder()
                        .message("File is not found.")
                        .status(HttpStatus.NOT_FOUND.value())
                        .build();
            }

        } catch (IOException ex) {
            throw new IOException("File not found exception");
        }
    }
}
