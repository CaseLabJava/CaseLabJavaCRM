package com.greenatom.service;

import com.greenatom.domain.dto.order.UploadDocumentRequestDTO;
import org.springframework.transaction.annotation.Transactional;

public interface UploadDocService {

    @Transactional
    void upload(UploadDocumentRequestDTO uploadDocumentRequestDTO);

    @Transactional
    void updateStatus(UploadDocumentRequestDTO uploadDocumentRequestDTO);
    @Transactional
    void updatePath(UploadDocumentRequestDTO uploadDocumentRequestDTO);
}
