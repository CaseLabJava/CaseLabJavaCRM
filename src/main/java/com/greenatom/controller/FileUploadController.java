package com.greenatom.controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

    @PostMapping(value ="/upload",consumes = "multipart/form-data")
    public String uploadFile(@RequestParam("file") MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                String uploadDir = "/Documents/UploadDoc";
                String fileName = file.getOriginalFilename();
                File uploadPath = new File(uploadDir);
                if (!uploadPath.exists()) {
                    uploadPath.mkdirs();
                }

                File targetFile = new File(uploadPath, fileName);
                try (FileOutputStream fos = new FileOutputStream(targetFile)) {
                    fos.write(file.getBytes());
                }
                return "Файл успешно загружен.";
            } catch (IOException e) {
                return "Ошибка при загрузке файла: " + e.getMessage();
            }
        } else {
            return "Файл пустой, загрузка не выполнена.";
        }
    }
}
