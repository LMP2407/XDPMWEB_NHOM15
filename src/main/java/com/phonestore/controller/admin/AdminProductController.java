package com.phonestore.controller.admin;

import com.phonestore.dto.admin.ProductRequest;
import com.phonestore.dto.admin.ProductResponse;
import com.phonestore.dto.common.ApiResponse;
import com.phonestore.dto.common.PageResponse;
import com.phonestore.service.AdminSanPhamService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/admin/products")
@PreAuthorize("hasRole('ADMIN')")
public class AdminProductController {

    private final AdminSanPhamService service;

    public AdminProductController(AdminSanPhamService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<ProductResponse>>> list(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String thuongHieu,
            @RequestParam(required = false) BigDecimal giaMin,
            @RequestParam(required = false) BigDecimal giaMax,
            @RequestParam(required = false) UUID maDanhMuc,
            @RequestParam(required = false) Boolean hienThi,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String sort) {
        return ResponseEntity.ok(ApiResponse.success(
                service.list(keyword, thuongHieu, giaMin, giaMax, maDanhMuc, hienThi, page, size, sort)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> get(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(service.getById(id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ProductResponse>> create(@Valid @RequestBody ProductRequest req) {
        return ResponseEntity.ok(ApiResponse.success("Tạo sản phẩm thành công", service.create(req)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductResponse>> update(@PathVariable UUID id,
                                                               @Valid @RequestBody ProductRequest req) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật thành công", service.update(id, req)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@PathVariable UUID id) {
        service.softDelete(id);
        return ResponseEntity.ok(ApiResponse.success("Đã ẩn sản phẩm", null));
    }
}
