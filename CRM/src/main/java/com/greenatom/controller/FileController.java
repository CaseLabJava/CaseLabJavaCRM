package com.greenatom.controller;

import com.greenatom.controller.api.FileApi;
import com.greenatom.domain.dto.order.UploadDocumentRequestDTO;
import com.greenatom.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/orders/file")
public class FileController implements FileApi {

    private final FileService fileService;

    @PostMapping(value = "/upload", consumes = "multipart/form-data", produces = {"application/json"})
    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<Void> uploadFile(@RequestParam("file") MultipartFile file, @RequestParam("id") Long id) {
        UploadDocumentRequestDTO uploadDocumentRequestDTO = new UploadDocumentRequestDTO(file, id);
        fileService.uploadFile(uploadDocumentRequestDTO);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<ByteArrayResource> downloadFile(@RequestParam("path") String path,
                                                          @RequestParam("content-type") String contentType) {
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, contentType)
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=" + fileService.hashCode())
                .body(new ByteArrayResource(fileService.downloadFile(path)));
    }

    @DeleteMapping
    @PreAuthorize(value = "hasAnyRole('ROLE_MANAGER', 'ROLE_SUPER_ADMIN')")
    public ResponseEntity<Void> deleteFile(@RequestParam("fileName") String fileName) {
        fileService.deleteFile(fileName);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
