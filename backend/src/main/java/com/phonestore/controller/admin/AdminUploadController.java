package com.phonestore.controller.admin;

import com.phonestore.dto.common.ApiResponse;
import com.phonestore.service.FileStorageService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("/api/admin/upload")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUploadController {

    private final FileStorageService fileStorageService;

    public AdminUploadController(FileStorageService fileStorageService) {
        this.fileStorageService = fileStorageService;
    }

    @PostMapping(consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<Map<String, String>>> upload(@RequestParam("file") MultipartFile file) {
        String filename = fileStorageService.store(file);
        return ResponseEntity.ok(ApiResponse.success("Upload thành công",
                Map.of("filename", filename, "url", "/uploads/" + filename)));
    }
}
