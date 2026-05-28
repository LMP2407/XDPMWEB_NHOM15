package com.phonestore.controller;

import com.phonestore.dto.admin.OrderResponse;
import com.phonestore.dto.common.ApiResponse;
import com.phonestore.dto.common.PageResponse;
import com.phonestore.dto.user.CheckoutRequest;
import com.phonestore.entity.NguoiDung;
import com.phonestore.security.SecurityUtil;
import com.phonestore.service.UserDonHangService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Order endpoints cho user đã đăng nhập.
 */
@RestController
@RequestMapping("/api/orders")
@PreAuthorize("isAuthenticated()")
public class UserOrderController {

    private final UserDonHangService service;
    private final SecurityUtil securityUtil;

    public UserOrderController(UserDonHangService service, SecurityUtil securityUtil) {
        this.service = service;
        this.securityUtil = securityUtil;
    }

    @PostMapping("/checkout")
    public ResponseEntity<ApiResponse<OrderResponse>> checkout(@Valid @RequestBody CheckoutRequest req) {
        NguoiDung me = securityUtil.requireCurrentUser();
        return ResponseEntity.ok(ApiResponse.success("Đặt hàng thành công", service.checkout(me, req)));
    }

    @GetMapping("/my-orders")
    public ResponseEntity<ApiResponse<PageResponse<OrderResponse>>> myOrders(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        NguoiDung me = securityUtil.requireCurrentUser();
        return ResponseEntity.ok(ApiResponse.success(service.myOrders(me, page, size)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> detail(@PathVariable UUID id) {
        NguoiDung me = securityUtil.requireCurrentUser();
        return ResponseEntity.ok(ApiResponse.success(service.myOrderDetail(me, id)));
    }
}
