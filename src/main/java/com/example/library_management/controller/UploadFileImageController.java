package com.example.library_management.controller;

import com.example.library_management.Exception.NotFoundException;
import com.example.library_management.model.response.UploadFileImageResponse;
import com.example.library_management.service.UploadFileImageService;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UploadFileImageController {
    private final UploadFileImageService fileImageService;

    //    @Value("${project.image}")
    private String path = "src/main/resources/images";

    //    upload image
    @PostMapping(value = "/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<UploadFileImageResponse> uploadFile(@RequestParam MultipartFile file) throws IOException {
        UploadFileImageResponse fileName = fileImageService.uploadFile(file);
        return ResponseEntity.ok(fileName);
    }

    //    get all images
    @GetMapping("/files")
    public ResponseEntity<UploadFileImageResponse> getAllImages() throws IOException {
//        Path imagesDir = Paths.get("src/main/resources/images");
//        List<File> files = Files.list(imagesDir).filter(Files::isRegularFile).map(Path::toFile)
//                .collect(Collectors.toList());
//
//        List<File> images = files.stream().filter(file -> file.getName().endsWith(".png") ||
//                file.getName().endsWith(".jpg") || file.getName().endsWith(".jpeg")).collect(Collectors.toList());

        return ResponseEntity.ok(fileImageService.getAllImages());
    }

    //    get a specific image by image name
    @GetMapping(value = "/file/{imageName}")
    public ResponseEntity<UploadFileImageResponse> getImage(@PathVariable String imageName) throws IOException {
//        Path imagesDir = Paths.get("src/main/resources/images");
//        List<File> files = Files.list(imagesDir).filter(Files::isRegularFile).map(Path::toFile)
//                .collect(Collectors.toList());
//
//        File image = files.stream().filter(file -> file.getName().equals(imageName)).findFirst().orElse(null);

        if (!fileImageService.isImageExist(imageName)) {
            throw new NotFoundException("Image name is not found.");
        }

        return ResponseEntity.ok(fileImageService.getImageByImageName(imageName));
    }
}
