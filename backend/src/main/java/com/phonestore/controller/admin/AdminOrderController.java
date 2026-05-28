package com.phonestore.controller.admin;

import com.phonestore.dto.admin.OrderResponse;
import com.phonestore.dto.admin.UpdatePaymentStatusRequest;
import com.phonestore.dto.common.ApiResponse;
import com.phonestore.dto.common.PageResponse;
import com.phonestore.service.AdminDonHangService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/admin/orders")
@PreAuthorize("hasRole('ADMIN')")
public class AdminOrderController {

    private final AdminDonHangService service;

    public AdminOrderController(AdminDonHangService service) { this.service = service; }

    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<OrderResponse>>> list(
            @RequestParam(required = false) String keyword,
            @RequestParam(required = false) String trangThai,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(ApiResponse.success(service.list(keyword, trangThai, page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> detail(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success(service.detail(id)));
    }

    @PutMapping("/{id}/confirm")
    public ResponseEntity<ApiResponse<OrderResponse>> confirm(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success("Xác nhận đơn", service.confirm(id)));
    }

    @PutMapping("/{id}/shipping")
    public ResponseEntity<ApiResponse<OrderResponse>> shipping(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success("Đang giao", service.shipping(id)));
    }

    @PutMapping("/{id}/delivered")
    public ResponseEntity<ApiResponse<OrderResponse>> delivered(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success("Đã giao", service.delivered(id)));
    }

    @PutMapping("/{id}/cancel")
    public ResponseEntity<ApiResponse<OrderResponse>> cancel(@PathVariable UUID id) {
        return ResponseEntity.ok(ApiResponse.success("Đã hủy", service.cancel(id)));
    }

    @PutMapping("/{id}/payment-status")
    public ResponseEntity<ApiResponse<OrderResponse>> updatePayment(@PathVariable UUID id,
                                                                     @Valid @RequestBody UpdatePaymentStatusRequest req) {
        return ResponseEntity.ok(ApiResponse.success("Cập nhật thanh toán", service.updatePaymentStatus(id, req.getTrangThaiTT())));
    }
}
