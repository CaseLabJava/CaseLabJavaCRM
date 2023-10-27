package com.greenatom.service;

import com.greenatom.domain.dto.order.UploadDocRequest;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface UploadDocService {

    @Transactional
    void upload(UploadDocRequest uploadDocRequest);

    @Transactional
    void updateStatus(UploadDocRequest uploadDocRequest);

}
