package com.example.spa_case.controller.RESTController;


import com.example.spa_case.model.File;
import com.example.spa_case.service.file.UploadFileService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
@AllArgsConstructor
public class FileRestController {
    private final UploadFileService uploadFileService;

    @PostMapping
    public File upload(@RequestParam("avatar") MultipartFile avatar) throws IOException {
        return uploadFileService.saveAvatar(avatar);
    }
}