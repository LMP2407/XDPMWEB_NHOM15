package com.phonestore.dto.admin;

import java.math.BigDecimal;
import java.util.List;

public class DashboardResponse {
    private BigDecimal tongDoanhThu;
    private BigDecimal doanhThuThangNay;
    private long tongNguoiDung;
    private long tongDonHang;
    private long tongSanPham;
    private long donChoXacNhan;
    private long donDangGiao;
    private long donDaGiao;
    private long donDaHuy;
    private List<TopProductResponse> topSanPham;

    public DashboardResponse() {}

    public BigDecimal getTongDoanhThu() { return tongDoanhThu; }
    public void setTongDoanhThu(BigDecimal tongDoanhThu) { this.tongDoanhThu = tongDoanhThu; }
    public BigDecimal getDoanhThuThangNay() { return doanhThuThangNay; }
    public void setDoanhThuThangNay(BigDecimal doanhThuThangNay) { this.doanhThuThangNay = doanhThuThangNay; }
    public long getTongNguoiDung() { return tongNguoiDung; }
    public void setTongNguoiDung(long tongNguoiDung) { this.tongNguoiDung = tongNguoiDung; }
    public long getTongDonHang() { return tongDonHang; }
    public void setTongDonHang(long tongDonHang) { this.tongDonHang = tongDonHang; }
    public long getTongSanPham() { return tongSanPham; }
    public void setTongSanPham(long tongSanPham) { this.tongSanPham = tongSanPham; }
    public long getDonChoXacNhan() { return donChoXacNhan; }
    public void setDonChoXacNhan(long donChoXacNhan) { this.donChoXacNhan = donChoXacNhan; }
    public long getDonDangGiao() { return donDangGiao; }
    public void setDonDangGiao(long donDangGiao) { this.donDangGiao = donDangGiao; }
    public long getDonDaGiao() { return donDaGiao; }
    public void setDonDaGiao(long donDaGiao) { this.donDaGiao = donDaGiao; }
    public long getDonDaHuy() { return donDaHuy; }
    public void setDonDaHuy(long donDaHuy) { this.donDaHuy = donDaHuy; }
    public List<TopProductResponse> getTopSanPham() { return topSanPham; }
    public void setTopSanPham(List<TopProductResponse> topSanPham) { this.topSanPham = topSanPham; }
}
