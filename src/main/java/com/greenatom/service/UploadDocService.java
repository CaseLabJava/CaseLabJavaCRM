package com.greenatom.service;

import com.greenatom.domain.dto.ProductDTO;
import com.greenatom.domain.dto.UploadDocDTO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

public interface UploadDocService {

    @Transactional
    void upload(MultipartFile file);

    @Transactional
    void updateStatus(UploadDocDTO uploadDocDTO);

}
