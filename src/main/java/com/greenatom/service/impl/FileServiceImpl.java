package com.greenatom.service.impl;

import com.greenatom.domain.dto.order.UploadDocumentRequestDTO;
import com.greenatom.domain.entity.Order;
import com.greenatom.domain.enums.OrderStatus;
import com.greenatom.repository.OrderRepository;
import com.greenatom.service.FileService;
import com.greenatom.utils.exception.OrderException;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.MinioException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class FileServiceImpl implements FileService {

    private final MinioClient minioClient;
    private final OrderRepository orderRepository;

    @Override
    public void uploadFile(UploadDocumentRequestDTO uploadDocumentRequestDTO) {
        MultipartFile file = uploadDocumentRequestDTO.getFile();
        if (!file.isEmpty()) {
            try {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket("documents")
                                .object(uploadDocumentRequestDTO.getId() + "/" + file.getOriginalFilename())
                                .stream(file.getInputStream(), file.getSize(), -1)
                                .contentType(file.getContentType())
                                .build());
                uploadDocumentRequestDTO.setLinkToFolder(uploadDocumentRequestDTO.getId() + file.getName());

            } catch (MinioException | InvalidKeyException | NoSuchAlgorithmException | IOException e) {
                throw new RuntimeException(e);
            }
        }
        updateStatus(uploadDocumentRequestDTO);
    }

    @Override
    public byte[] downloadFile(String pathToFile) {
        try (InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket("documents")
                        .object(pathToFile)
                        .build())) {
            return stream.readAllBytes();
        } catch (MinioException | InvalidKeyException | NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteFile(String fileName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket("documents")
                            .object(fileName)
                            .build());
        } catch (MinioException | InvalidKeyException | NoSuchAlgorithmException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    private void updateStatus(UploadDocumentRequestDTO uploadDocumentRequestDTO) {
        Order order = orderRepository
                .findById(uploadDocumentRequestDTO.getId())
                .orElseThrow(OrderException.CODE.NO_SUCH_ORDER::get);
        if (order.getOrderStatus().equals(OrderStatus.SIGNED_BY_EMPLOYEE)) {

            order.setOrderStatus(OrderStatus.SIGNED_BY_CLIENT);
        } else {
            throw OrderException.CODE.CANNOT_ASSIGN_ORDER.get();
        }
        updatePath(uploadDocumentRequestDTO);
    }


    private void updatePath(UploadDocumentRequestDTO uploadDocumentRequestDTO) {
        log.debug("Order to update link_to_folder : {}", uploadDocumentRequestDTO);

        Long orderId = uploadDocumentRequestDTO.getId();
        String linkToFolder = uploadDocumentRequestDTO.getLinkToFolder();

        orderRepository.updateLinkToFolder(orderId, linkToFolder);
    }
}
