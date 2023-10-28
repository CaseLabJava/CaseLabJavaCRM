package com.greenatom.service.impl;

import com.greenatom.domain.dto.order.UploadDocRequest;
import com.greenatom.domain.entity.Order;
import com.greenatom.domain.enums.OrderStatus;
import com.greenatom.repository.OrderRepository;
import com.greenatom.service.UploadDocService;
import com.greenatom.utils.exception.OrderException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


@Service
@RequiredArgsConstructor
@Slf4j
public class UploadDocImpl implements UploadDocService {
    private final OrderRepository orderRepository;

    @Override
    public void upload(UploadDocRequest uploadDocRequest) {
        MultipartFile file = uploadDocRequest.getFile();
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

                uploadDocRequest.setLinkToFolder(targetFile.getAbsolutePath());

                log.info("Файл успешно загружен. Имя файла: " + fileName + ", Путь: " + targetFile.getAbsolutePath());
            } catch (IOException e) {
                log.error("Ошибка при загрузке файла: " + e.getMessage());
            }
        } else {
            log.error("Файл пустой, загрузка не выполнена.");
        }
    }


    @Override
    public void updateStatus(UploadDocRequest uploadDocRequest) {
        Order order = orderRepository
                .findById(uploadDocRequest.getId())
                .orElseThrow(OrderException.CODE.NO_SUCH_ORDER::get);
        if (order.getOrderStatus().equals(OrderStatus.SIGNED_BY_EMPLOYEE.name())) {

            order.setOrderStatus(OrderStatus.SIGNED_BY_CLIENT.name());
        } else {
            throw OrderException.CODE.CANNOT_ASSIGN_ORDER.get();
        }
    }
    @Override
    public void updatePath(UploadDocRequest uploadDocRequest) {
        log.debug("Order to update link_to_folder : {}", uploadDocRequest);

        Long orderId = uploadDocRequest.getId();
        String linkToFolder = uploadDocRequest.getLinkToFolder();

        orderRepository.updateLinkToFolder(orderId, linkToFolder);
    }
}
