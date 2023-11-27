package com.greenatom.service.impl;

import com.greenatom.domain.dto.order.UploadDocumentRequestDTO;
import com.greenatom.domain.entity.Order;
import com.greenatom.domain.enums.OrderStatus;
import com.greenatom.exception.FileException;
import com.greenatom.exception.OrderException;
import com.greenatom.repository.OrderRepository;
import com.greenatom.service.FileService;
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

    private static final String DOC_DIR_NAME = "documents";

    @Override
    public void uploadFile(UploadDocumentRequestDTO uploadDocumentRequestDTO) {
        MultipartFile file = uploadDocumentRequestDTO.getFile();
        if (!file.isEmpty()) {
            try {
                minioClient.putObject(
                        PutObjectArgs.builder()
                                .bucket(DOC_DIR_NAME)
                                .object(uploadDocumentRequestDTO.getId() + "/" +
                                        "signed_by_client.docx")
                                .stream(file.getInputStream(), file.getSize(), -1)
                                .contentType(file.getContentType())
                                .build());
                uploadDocumentRequestDTO.setLinkToFolder(uploadDocumentRequestDTO.getId() + file.getName());
            } catch (MinioException e) {
                throw FileException.CODE.MINIO.get(e.getMessage());
            } catch (InvalidKeyException e) {
                throw FileException.CODE.INVALID_KEY.get(e.getMessage());
            } catch (NoSuchAlgorithmException e) {
                throw FileException.CODE.ALGORITHM_NOT_FOUND.get(e.getMessage());
            } catch (IOException e) {
                throw FileException.CODE.IO.get(e.getMessage());
            }
        }
        updateStatus(uploadDocumentRequestDTO);
    }

    @Override
    public byte[] downloadFile(String pathToFile) {
        try (InputStream stream = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket(DOC_DIR_NAME)
                        .object(pathToFile)
                        .build())) {
            return stream.readAllBytes();
        } catch (MinioException e) {
            throw FileException.CODE.MINIO.get(e.getMessage());
        } catch (InvalidKeyException e) {
            throw FileException.CODE.INVALID_KEY.get(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw FileException.CODE.ALGORITHM_NOT_FOUND.get(e.getMessage());
        } catch (IOException e) {
            throw FileException.CODE.IO.get(e.getMessage());
        }
    }

    @Override
    public void deleteFile(String fileName) {
        try {
            minioClient.removeObject(
                    RemoveObjectArgs.builder()
                            .bucket(DOC_DIR_NAME)
                            .object(fileName)
                            .build());
        } catch (MinioException e) {
            throw FileException.CODE.MINIO.get(e.getMessage());
        } catch (InvalidKeyException e) {
            throw FileException.CODE.INVALID_KEY.get(e.getMessage());
        } catch (NoSuchAlgorithmException e) {
            throw FileException.CODE.ALGORITHM_NOT_FOUND.get(e.getMessage());
        } catch (IOException e) {
            throw FileException.CODE.IO.get(e.getMessage());
        }
    }


    private void updateStatus(UploadDocumentRequestDTO uploadDocumentRequestDTO) {
        Order order = orderRepository
                .findById(uploadDocumentRequestDTO.getId())
                .orElseThrow(OrderException.CODE.NO_SUCH_ORDER::get);
        if (!order.getOrderStatus().equals(OrderStatus.DELIVERY_FINISHED)) {
            throw OrderException.CODE.CANNOT_ASSIGN_ORDER.get();
        }
        order.setOrderStatus(OrderStatus.SIGNED_BY_CLIENT);
        updatePath(uploadDocumentRequestDTO);
    }

    private void updatePath(UploadDocumentRequestDTO uploadDocumentRequestDTO) {
        log.debug("Order to update link_to_folder : {}", uploadDocumentRequestDTO);

        Long orderId = uploadDocumentRequestDTO.getId();
        String linkToFolder = uploadDocumentRequestDTO.getLinkToFolder();

        orderRepository.updateLinkToFolder(orderId, linkToFolder);
    }
}
