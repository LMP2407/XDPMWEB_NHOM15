package com.phonestore.dto.admin;

import java.math.BigDecimal;
import java.util.UUID;

public class TopProductResponse {
    private UUID maSanPham;
    private String tenSanPham;
    private String hinhAnh;
    private Long soLuongBan;
    private BigDecimal doanhThu;

    public TopProductResponse() {}
    public TopProductResponse(UUID maSanPham, String tenSanPham, String hinhAnh, Long soLuongBan, BigDecimal doanhThu) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.hinhAnh = hinhAnh;
        this.soLuongBan = soLuongBan;
        this.doanhThu = doanhThu;
    }
    public UUID getMaSanPham() { return maSanPham; }
    public void setMaSanPham(UUID maSanPham) { this.maSanPham = maSanPham; }
    public String getTenSanPham() { return tenSanPham; }
    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }
    public String getHinhAnh() { return hinhAnh; }
    public void setHinhAnh(String hinhAnh) { this.hinhAnh = hinhAnh; }
    public Long getSoLuongBan() { return soLuongBan; }
    public void setSoLuongBan(Long soLuongBan) { this.soLuongBan = soLuongBan; }
    public BigDecimal getDoanhThu() { return doanhThu; }
    public void setDoanhThu(BigDecimal doanhThu) { this.doanhThu = doanhThu; }
}
