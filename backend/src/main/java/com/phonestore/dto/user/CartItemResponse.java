package com.phonestore.dto.user;

import java.math.BigDecimal;
import java.util.UUID;

public class CartItemResponse {
    private UUID maGioHang;
    private UUID maSanPham;
    private String tenSanPham;
    private String hinhAnh;
    private BigDecimal gia;
    private Integer soLuong;
    private Integer soLuongTon;
    private BigDecimal thanhTien;

    public CartItemResponse() {}

    public UUID getMaGioHang() { return maGioHang; }
    public void setMaGioHang(UUID maGioHang) { this.maGioHang = maGioHang; }
    public UUID getMaSanPham() { return maSanPham; }
    public void setMaSanPham(UUID maSanPham) { this.maSanPham = maSanPham; }
    public String getTenSanPham() { return tenSanPham; }
    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }
    public String getHinhAnh() { return hinhAnh; }
    public void setHinhAnh(String hinhAnh) { this.hinhAnh = hinhAnh; }
    public BigDecimal getGia() { return gia; }
    public void setGia(BigDecimal gia) { this.gia = gia; }
    public Integer getSoLuong() { return soLuong; }
    public void setSoLuong(Integer soLuong) { this.soLuong = soLuong; }
    public Integer getSoLuongTon() { return soLuongTon; }
    public void setSoLuongTon(Integer soLuongTon) { this.soLuongTon = soLuongTon; }
    public BigDecimal getThanhTien() { return thanhTien; }
    public void setThanhTien(BigDecimal thanhTien) { this.thanhTien = thanhTien; }
}
