package com.greenatom.service.impl;

import com.greenatom.domain.dto.UploadDocDTO;
import com.greenatom.domain.entity.Order;
import com.greenatom.domain.enums.OrderStatus;
import com.greenatom.repository.OrderRepository;
import com.greenatom.service.UploadDocService;


import com.greenatom.utils.exception.OrderException;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@Service
@RequiredArgsConstructor
public class UploadDocImpl implements UploadDocService {
    private final OrderRepository orderRepository;
    private final Logger log = LoggerFactory.getLogger(UploadDocService.class);

    @Override
    public void upload(MultipartFile file) {
        if (!file.isEmpty()) {
            try {

                String projectRoot = System.getProperty("user.dir");
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


                log.info("Файл успешно загружен. Имя файла: " + fileName + ", Путь: " + targetFile.getAbsolutePath());
            } catch (IOException e) {
                log.error("Ошибка при загрузке файла: " + e.getMessage());
            }
        } else {
            log.error("Файл пустой, загрузка не выполнена.");
        }
    }

    @Override
    public void updateStatus(UploadDocDTO uploadDocDTO) {
        Order order = orderRepository
                .findById(uploadDocDTO.getId())
                .orElseThrow(OrderException.CODE.NO_SUCH_ORDER::get);
        if (order.getOrderStatus().equals(OrderStatus.SIGNED_BY_EMPLOYEE.name())) {

            order.setOrderStatus(OrderStatus.SIGNED_BY_CLIENT.name());
        } else {
            throw OrderException.CODE.CANNOT_ASSIGN_ORDER.get();
        }
    }

}
