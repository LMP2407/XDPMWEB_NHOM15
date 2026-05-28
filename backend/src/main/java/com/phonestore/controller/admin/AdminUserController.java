package com.phonestore.controller.admin;

import com.phonestore.dto.admin.UserResponse;
import com.phonestore.dto.common.ApiResponse;
import com.phonestore.dto.common.PageResponse;
import com.phonestore.service.AdminNguoiDungService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/users")
@PreAuthorize("hasRole('ADMIN')")
public class AdminUserController {

    private final AdminNguoiDungService service;

    public AdminUserController(AdminNguoiDungService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<UserResponse>>> list(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String vaiTro,
            @RequestParam(required = false) Boolean hoatDong,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(service.list(keyword, vaiTro, hoatDong, page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> get(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(service.getById(id)));
    }

    @PutMapping("/{id}/lock")
    public ResponseEntity<ApiResponse<UserResponse>> lock(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success("Đã khóa tài khoản", service.lock(id)));
    }

    @PutMapping("/{id}/unlock")
    public ResponseEntity<ApiResponse<UserResponse>> unlock(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success("Đã mở khóa tài khoản", service.unlock(id)));
    }
}
