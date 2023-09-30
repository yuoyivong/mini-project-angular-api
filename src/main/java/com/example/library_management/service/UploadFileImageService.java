package com.example.library_management.service;

import com.example.library_management.model.response.UploadFileImageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UploadFileImageService {

    private final Path root = Paths.get("src/main/resources/images");

//    upload image
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

//    get all images
    public UploadFileImageResponse getAllImages() throws IOException {

        List<File> files = Files.list(root).filter(Files::isRegularFile).map(Path::toFile)
                .collect(Collectors.toList());

        System.out.println("Files : " + files);
        List<File> images = files.stream().filter(file -> file.getName().endsWith(".png") ||
                file.getName().endsWith(".jpg") || file.getName().endsWith(".jpeg")).collect(Collectors.toList());

        System.out.println("Images : " + images.stream().map(file -> file.getName()));
//        images.stream().map(file -> file.getName()).collect(Collectors.toList());
        return UploadFileImageResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Get all images successfully.")
                .payload(images.stream().map(file -> file.getName()).collect(Collectors.toList()))
                .build();
    }

//    get specific image
    public UploadFileImageResponse getImageByImageName(String imageName) throws IOException {

        List<File> files = Files.list(root).filter(Files::isRegularFile).map(Path::toFile)
                .collect(Collectors.toList());

        File image = files.stream().filter(file -> file.getName().equals(imageName)).findFirst().orElse(null);

        return UploadFileImageResponse.builder()
                .status(HttpStatus.OK.value())
                .message("Successfully get image name : " + image.getName())
                .payload(image.getName())
                .build();
    }

    public boolean isImageExist(String imageName) {
        Path imagePath = Paths.get("src/main/resources/images/" + imageName);

        try {
            return Files.exists(imagePath);
        } catch (Exception e) {
            return false;
        }
//        boolean exists = Files.exists(imagePath);
//        System.out.println(exists);
//        System.out.println(imagePath);
//        return exists;
    }

}
