package com.phonestore.dto.admin;

import java.math.BigDecimal;
import java.util.UUID;

public class OrderItemResponse {
    private UUID maChiTiet;
    private UUID maSanPham;
    private String tenSanPham;
    private String hinhAnh;
    private Integer soLuong;
    private BigDecimal donGia;
    private BigDecimal thanhTien;

    public OrderItemResponse() {}

    public UUID getMaChiTiet() { return maChiTiet; }
    public void setMaChiTiet(UUID maChiTiet) { this.maChiTiet = maChiTiet; }
    public UUID getMaSanPham() { return maSanPham; }
    public void setMaSanPham(UUID maSanPham) { this.maSanPham = maSanPham; }
    public String getTenSanPham() { return tenSanPham; }
    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }
    public String getHinhAnh() { return hinhAnh; }
    public void setHinhAnh(String hinhAnh) { this.hinhAnh = hinhAnh; }
    public Integer getSoLuong() { return soLuong; }
    public void setSoLuong(Integer soLuong) { this.soLuong = soLuong; }
    public BigDecimal getDonGia() { return donGia; }
    public void setDonGia(BigDecimal donGia) { this.donGia = donGia; }
    public BigDecimal getThanhTien() { return thanhTien; }
    public void setThanhTien(BigDecimal thanhTien) { this.thanhTien = thanhTien; }
}
