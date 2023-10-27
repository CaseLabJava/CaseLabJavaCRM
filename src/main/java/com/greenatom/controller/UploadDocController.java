package com.greenatom.controller;

import com.greenatom.domain.dto.order.UploadDocRequest;
import com.greenatom.service.UploadDocService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class UploadDocController {
    private final UploadDocService uploadDocService;
    @PostMapping(value = "/upload", consumes = "multipart/form-data", produces = {"application/json"})
    public void uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id) {
        UploadDocRequest uploadDocRequest = new UploadDocRequest(file, id);
        uploadDocService.upload(uploadDocRequest);
    }

}

