package com.greenatom.service;

import com.greenatom.domain.dto.order.UploadDocumentRequestDTO;

public interface FileService {
    void uploadFile(UploadDocumentRequestDTO uploadDocumentRequestDTO);

    byte[] downloadFile(String pathToFile);

    void deleteFile(String fileName);
}
