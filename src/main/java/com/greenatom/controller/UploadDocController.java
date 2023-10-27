package com.greenatom.controller;

import com.greenatom.controller.api.UploadDocApi;
import com.greenatom.domain.dto.order.UploadDocRequest;
import com.greenatom.service.UploadDocService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.greenatom.controller.api.UploadDocApi;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class UploadDocController implements UploadDocApi {
    private final UploadDocService uploadDocService;
    @PostMapping(value = "/upload", consumes = "multipart/form-data", produces = {"application/json"})
    public void uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id) {
        UploadDocRequest uploadDocRequest = new UploadDocRequest(file, id);
        uploadDocService.upload(uploadDocRequest);
        uploadDocService.updateStatus(uploadDocRequest);
        uploadDocService.updatePath(uploadDocRequest);
    }


    @Override
    public void uploadDocument(Long id, MultipartFile file) {

    }
}

