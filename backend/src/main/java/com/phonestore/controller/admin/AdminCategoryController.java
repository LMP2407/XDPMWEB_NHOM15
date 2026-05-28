package com.phonestore.controller.admin;

import com.phonestore.dto.admin.CategoryRequest;
import com.phonestore.dto.admin.CategoryResponse;
import com.phonestore.dto.common.ApiResponse;
import com.phonestore.service.AdminDanhMucService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/categories")
@PreAuthorize("hasRole('ADMIN')")
public class AdminCategoryController {

    private final AdminDanhMucService service;

    public AdminCategoryController(AdminDanhMucService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CategoryResponse>>> list() {
        return ResponseEntity.ok(ApiResponse.success(service.list()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> get(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(service.getById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CategoryResponse>> create(@Valid @RequestBody CategoryRequest req) {
        return ResponseEntity.ok(ApiResponse.success("Tạo danh mục thành công", service.create(req)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CategoryResponse>> update(@PathVariable UUID id,
                                                                @Valid @RequestBody CategoryRequest req) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật danh mục thành công", service.update(id, req)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.ok(ApiResponse.success("Đã xóa danh mục", null));
    }
}
