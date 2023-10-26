package com.greenatom.service.impl;

import com.greenatom.domain.dto.ProductDTO;
import com.greenatom.domain.dto.UploadDocDTO;
import com.greenatom.service.OrderService;
import com.greenatom.service.UploadDocService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UploadDocImpl implements UploadDocService {
    private final Logger log = LoggerFactory.getLogger(UploadDocService.class);
    @Override
    public void upload(MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                // Получите путь к корневой папке проекта (в данном случае, текущей директории)
                String projectRoot = System.getProperty("user.dir");

                // Установите путь загрузки относительно корневой папки проекта
                String uploadDir = projectRoot + "/Documents/UploadDoc";

                String fileName = file.getOriginalFilename();
                File uploadPath = new File(uploadDir);
                if (!uploadPath.exists()) {
                    uploadPath.mkdirs();
                }

                File targetFile = new File(uploadPath, fileName);
                try (FileOutputStream fos = new FileOutputStream(targetFile)) {
                    fos.write(file.getBytes());
                }

                // Логгирование успешной загрузки
                log.info("Файл успешно загружен. Имя файла: " + fileName + ", Путь: " + targetFile.getAbsolutePath());
            } catch (IOException e) {
                // Логгирование ошибки
                log.error("Ошибка при загрузке файла: " + e.getMessage());
            }
        } else {
            // Логгирование пустого файла
            log.error("Файл пустой, загрузка не выполнена.");
        }
    }

}
