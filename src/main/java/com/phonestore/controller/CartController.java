package com.phonestore.controller;

import com.phonestore.dto.common.ApiResponse;
import com.phonestore.dto.user.AddToCartRequest;
import com.phonestore.dto.user.CartResponse;
import com.phonestore.dto.user.UpdateCartRequest;
import com.phonestore.entity.NguoiDung;
import com.phonestore.security.SecurityUtil;
import com.phonestore.service.GioHangService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Cart cho user đã đăng nhập (yêu cầu JWT).
 * Cart cho khách giữ nguyên ở /api/gio-hang/guest.
 */
@RestController
@RequestMapping("/api/cart")
@PreAuthorize("isAuthenticated()")
public class CartController {

    private final GioHangService cartService;
    private final SecurityUtil securityUtil;

    public CartController(GioHangService cartService, SecurityUtil securityUtil) {
        this.cartService = cartService;
        this.securityUtil = securityUtil;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<CartResponse>> getCart() {
        NguoiDung me = securityUtil.requireCurrentUser();
        return ResponseEntity.ok(ApiResponse.success(cartService.getCart(me)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<CartResponse>> add(@Valid @RequestBody AddToCartRequest req) {
        NguoiDung me = securityUtil.requireCurrentUser();
        return ResponseEntity.ok(ApiResponse.success("Đã thêm vào giỏ", cartService.addToCart(me, req)));
    }

    @PutMapping("/{maSanPham}")
    public ResponseEntity<ApiResponse<CartResponse>> update(@PathVariable UUID maSanPham,
                                                            @Valid @RequestBody UpdateCartRequest req) {
        NguoiDung me = securityUtil.requireCurrentUser();
        return ResponseEntity.ok(ApiResponse.success("Đã cập nhật", cartService.updateQuantity(me, maSanPham, req.getSoLuong())));
    }

    @DeleteMapping("/{maSanPham}")
    public ResponseEntity<ApiResponse<CartResponse>> remove(@PathVariable UUID maSanPham) {
        NguoiDung me = securityUtil.requireCurrentUser();
        return ResponseEntity.ok(ApiResponse.success("Đã xóa", cartService.removeItem(me, maSanPham)));
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> clear() {
        NguoiDung me = securityUtil.requireCurrentUser();
        cartService.clearCart(me);
        return ResponseEntity.ok(ApiResponse.success("Đã xóa toàn bộ giỏ hàng", null));
    }
}
