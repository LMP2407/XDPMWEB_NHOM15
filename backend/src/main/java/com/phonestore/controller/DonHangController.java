package com.phonestore.controller;

import com.phonestore.dto.TaoDonHangRequest;
import com.phonestore.dto.TaoDonHangResponse;
import com.phonestore.entity.DonHang;
import com.phonestore.service.DonHangService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/don-hang")
public class DonHangController {
    @Autowired private DonHangService donHangService;

    @PostMapping
    public ResponseEntity<?> taoDonHang(@Valid @RequestBody TaoDonHangRequest request) {
        try {
            DonHang donHang = donHangService.taoDonHang(request);
            TaoDonHangResponse response = new TaoDonHangResponse(
                donHang.getMaDonHang().toString(),
                donHang.getTongTien(),
                donHang.getTrangThai()
            );
            return ResponseEntity.ok(response);
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(Map.of("loi", e.getMessage(), "ma_loi", 400));
        }
    }

    @GetMapping("/tra-cuu")
    public ResponseEntity<?> traCuu(@RequestParam String sdt, @RequestParam String ma) {
        try {
            return ResponseEntity.ok(donHangService.traCuuDonHang(ma, sdt));
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(Map.of("loi", e.getMessage(), "ma_loi", 404));
        }
    }
}