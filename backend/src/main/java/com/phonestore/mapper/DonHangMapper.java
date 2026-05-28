package com.phonestore.mapper;

import com.phonestore.dto.admin.OrderItemResponse;
import com.phonestore.dto.admin.OrderResponse;
import com.phonestore.entity.ChiTietDonHang;
import com.phonestore.entity.DiaChi;
import com.phonestore.entity.DonHang;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class DonHangMapper {

    public OrderItemResponse toItemResponse(ChiTietDonHang ct) {
        OrderItemResponse i = new OrderItemResponse();
        i.setMaChiTiet(ct.getMaChiTiet());
        i.setSoLuong(ct.getSoLuong());
        i.setDonGia(ct.getDonGia());
        i.setThanhTien(ct.getDonGia().multiply(BigDecimal.valueOf(ct.getSoLuong())));
        if (ct.getSanPham() != null) {
            i.setMaSanPham(ct.getSanPham().getMaSanPham());
            i.setTenSanPham(ct.getSanPham().getTenSanPham());
            i.setHinhAnh(ct.getSanPham().getHinhAnh());
        }
        return i;
    }

    public OrderResponse toResponse(DonHang dh, List<ChiTietDonHang> chiTiet) {
        OrderResponse r = new OrderResponse();
        r.setMaDonHang(dh.getMaDonHang());
        r.setTongTien(dh.getTongTien());
        r.setTrangThai(dh.getTrangThai());
        r.setPhuongThucTT(dh.getPhuongThucTT());
        r.setTrangThaiTT(dh.getTrangThaiTT());
        r.setGhiChu(dh.getGhiChu());
        r.setNgayDat(dh.getNgayDat());
        r.setNgayCapNhat(dh.getNgayCapNhat());
        if (dh.getNguoiDung() != null) {
            r.setMaNguoiDung(dh.getNguoiDung().getMaNguoiDung());
        }
        DiaChi dc = dh.getDiaChi();
        if (dc != null) {
            r.setHoTenKhach(dc.getHoTen());
            r.setSoDienThoai(dc.getSoDienThoai());
            StringBuilder sb = new StringBuilder();
            sb.append(safe(dc.getSoNha()));
            if (dc.getPhuongXa() != null) sb.append(", ").append(dc.getPhuongXa());
            if (dc.getQuanHuyen() != null) sb.append(", ").append(dc.getQuanHuyen());
            sb.append(", ").append(safe(dc.getTinhThanh()));
            r.setDiaChiNhan(sb.toString());
        }
        if (chiTiet != null) {
            r.setItems(chiTiet.stream().map(this::toItemResponse).collect(Collectors.toList()));
        }
        return r;
    }

    private String safe(String s) { return s == null ? "" : s; }
}
