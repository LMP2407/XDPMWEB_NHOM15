package com.phonestore.controller;

import com.phonestore.service.SanPhamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/san-pham")
public class SanPhamController {
    @Autowired private SanPhamService sanPhamService;

    @GetMapping
    public ResponseEntity<?> getSanPham(
            @RequestParam(defaultValue = "0") int trang,
            @RequestParam(defaultValue = "10") int gioi_han,
            @RequestParam(required = false) String sap_xep,
            @RequestParam(required = false) String tu_khoa,
            @RequestParam(required = false) String thuong_hieu,
            @RequestParam(required = false) BigDecimal gia_min,
            @RequestParam(required = false) BigDecimal gia_max,
            @RequestParam(required = false) UUID ma_danh_muc) {
        Page<?> page;
        if (tu_khoa != null || thuong_hieu != null || gia_min != null || gia_max != null || ma_danh_muc != null) {
            page = sanPhamService.timKiemVaLoc(tu_khoa, thuong_hieu, gia_min, gia_max, ma_danh_muc, trang, gioi_han, sap_xep);
        } else {
            page = sanPhamService.getDanhSachSanPham(trang, gioi_han, sap_xep);
        }
        Map<String, Object> response = new HashMap<>();
        response.put("data", page.getContent());
        response.put("tong", page.getTotalElements());
        response.put("trang", page.getNumber());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getChiTiet(@PathVariable UUID id) {
        try {
            return ResponseEntity.ok(Map.of("san_pham", sanPhamService.getChiTietSanPham(id)));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("loi", e.getMessage(), "ma_loi", 404));
        }
    }
}