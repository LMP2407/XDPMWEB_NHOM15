package com.phonestore.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public class TraCuuDonHangResponse {
    private DonHangInfo donHang;
    private List<ChiTietDonHangInfo> chiTiet;

    public TraCuuDonHangResponse() {}

    public DonHangInfo getDonHang() { return donHang; }
    public void setDonHang(DonHangInfo donHang) { this.donHang = donHang; }
    public List<ChiTietDonHangInfo> getChiTiet() { return chiTiet; }
    public void setChiTiet(List<ChiTietDonHangInfo> chiTiet) { this.chiTiet = chiTiet; }

    public static class DonHangInfo {
        private String maDonHang;
        private String hoTenNguoiNhan;
        private String soDienThoaiNhan;
        private String diaChi;
        private BigDecimal tongTien;
        private String trangThai;
        private String trangThaiTT;
        private LocalDateTime ngayDat;

        public String getMaDonHang() { return maDonHang; }
        public void setMaDonHang(String maDonHang) { this.maDonHang = maDonHang; }
        public String getHoTenNguoiNhan() { return hoTenNguoiNhan; }
        public void setHoTenNguoiNhan(String hoTenNguoiNhan) { this.hoTenNguoiNhan = hoTenNguoiNhan; }
        public String getSoDienThoaiNhan() { return soDienThoaiNhan; }
        public void setSoDienThoaiNhan(String soDienThoaiNhan) { this.soDienThoaiNhan = soDienThoaiNhan; }
        public String getDiaChi() { return diaChi; }
        public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
        public BigDecimal getTongTien() { return tongTien; }
        public void setTongTien(BigDecimal tongTien) { this.tongTien = tongTien; }
        public String getTrangThai() { return trangThai; }
        public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
        public String getTrangThaiTT() { return trangThaiTT; }
        public void setTrangThaiTT(String trangThaiTT) { this.trangThaiTT = trangThaiTT; }
        public LocalDateTime getNgayDat() { return ngayDat; }
        public void setNgayDat(LocalDateTime ngayDat) { this.ngayDat = ngayDat; }
    }

    public static class ChiTietDonHangInfo {
        private String tenSanPham;
        private Integer soLuong;
        private BigDecimal donGia;
        private BigDecimal thanhTien;

        public String getTenSanPham() { return tenSanPham; }
        public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }
        public Integer getSoLuong() { return soLuong; }
        public void setSoLuong(Integer soLuong) { this.soLuong = soLuong; }
        public BigDecimal getDonGia() { return donGia; }
        public void setDonGia(BigDecimal donGia) { this.donGia = donGia; }
        public BigDecimal getThanhTien() { return thanhTien; }
        public void setThanhTien(BigDecimal thanhTien) { this.thanhTien = thanhTien; }
    }
}