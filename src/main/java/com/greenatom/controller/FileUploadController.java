package com.greenatom.controller;

import com.greenatom.service.UploadDocService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileUploadController {
    private final UploadDocService uploadDocService;

    @PostMapping(value ="/upload",consumes = "multipart/form-data")
    public void uploadFile(@RequestParam("file") MultipartFile file){
       uploadDocService.upload(file);
    }

}

