package com.greenatom.controller;

import com.greenatom.controller.api.UploadDocApi;
import com.greenatom.domain.dto.order.UploadDocumentRequestDTO;
import com.greenatom.service.UploadDocService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class UploadDocController implements UploadDocApi {
    private final UploadDocService uploadDocService;
    @PostMapping(value = "/upload", consumes = "multipart/form-data", produces = {"application/json"})
    @PreAuthorize(value = "hasRole('ROLE_MANAGER')")
    public void uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id) {
        UploadDocumentRequestDTO uploadDocumentRequestDTO = new UploadDocumentRequestDTO(file, id);
        uploadDocService.upload(uploadDocumentRequestDTO);
        uploadDocService.updateStatus(uploadDocumentRequestDTO);
        uploadDocService.updatePath(uploadDocumentRequestDTO);
    }
}

