package com.phonestore.controller;

import com.phonestore.service.DanhMucService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/danh-muc")
public class DanhMucController {
    @Autowired private DanhMucService danhMucService;

    @GetMapping
    public ResponseEntity<?> getDanhSach() {
        return ResponseEntity.ok(Map.of("data", danhMucService.getDanhSachDanhMuc()));
    }

    @GetMapping("/{id}/san-pham")
    public ResponseEntity<?> getSanPhamTheoDanhMuc(
            @PathVariable UUID id,
            @RequestParam(defaultValue = "0") int trang,
            @RequestParam(defaultValue = "10") int gioi_han) {
        Page<?> page = danhMucService.getSanPhamTheoDanhMuc(id, trang, gioi_han);
        Map<String, Object> response = new HashMap<>();
        response.put("data", page.getContent());
        response.put("tong", page.getTotalElements());
        return ResponseEntity.ok(response);
    }
}