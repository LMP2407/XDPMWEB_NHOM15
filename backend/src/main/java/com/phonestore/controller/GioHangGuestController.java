package com.phonestore.controller;

import com.phonestore.dto.CartItemRequest;
import com.phonestore.service.GuestCartService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/gio-hang/guest")
public class GioHangGuestController {
    @Autowired private GuestCartService guestCartService;

    @GetMapping
    public ResponseEntity<?> xemGioHang(HttpSession session) {
        return ResponseEntity.ok(guestCartService.getCart(session));
    }

    @PostMapping
    public ResponseEntity<?> themVaoGio(@Valid @RequestBody CartItemRequest request, HttpSession session) {
        guestCartService.addToCart(session, request);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Đã thêm vào giỏ hàng");
        response.put("items", guestCartService.getCart(session).getItems());
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> capNhatSoLuong(@PathVariable UUID id, @RequestBody Map<String, Integer> body, HttpSession session) {
        // BUG FIX: frontend sends { soLuong } (camelCase), was only checking "so_luong" (snake_case)
        // Now accepts both field names for compatibility
        Integer soLuong = body.get("soLuong");
        if (soLuong == null) {
            soLuong = body.get("so_luong"); // fallback for snake_case
        }
        if (soLuong == null || soLuong < 0) {
            return ResponseEntity.badRequest().body(Map.of("loi", "Số lượng không hợp lệ", "ma_loi", 400));
        }
        guestCartService.updateQuantity(session, id, soLuong);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Đã cập nhật");
        response.put("items", guestCartService.getCart(session).getItems());
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> xoaKhoiGio(@PathVariable UUID id, HttpSession session) {
        guestCartService.removeFromCart(session, id);
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Đã xóa sản phẩm");
        response.put("items", guestCartService.getCart(session).getItems());
        return ResponseEntity.ok(response);
    }
}
