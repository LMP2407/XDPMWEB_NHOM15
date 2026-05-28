package com.phonestore.dto.admin;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class OrderResponse {
    private UUID maDonHang;
    private UUID maNguoiDung;
    private String hoTenKhach;
    private String soDienThoai;
    private String diaChiNhan;
    private BigDecimal tongTien;
    private String trangThai;
    private String phuongThucTT;
    private String trangThaiTT;
    private String ghiChu;
    private LocalDateTime ngayDat;
    private LocalDateTime ngayCapNhat;
    private List<OrderItemResponse> items;

    public OrderResponse() {}

    public UUID getMaDonHang() { return maDonHang; }
    public void setMaDonHang(UUID maDonHang) { this.maDonHang = maDonHang; }
    public UUID getMaNguoiDung() { return maNguoiDung; }
    public void setMaNguoiDung(UUID maNguoiDung) { this.maNguoiDung = maNguoiDung; }
    public String getHoTenKhach() { return hoTenKhach; }
    public void setHoTenKhach(String hoTenKhach) { this.hoTenKhach = hoTenKhach; }
    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }
    public String getDiaChiNhan() { return diaChiNhan; }
    public void setDiaChiNhan(String diaChiNhan) { this.diaChiNhan = diaChiNhan; }
    public BigDecimal getTongTien() { return tongTien; }
    public void setTongTien(BigDecimal tongTien) { this.tongTien = tongTien; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public String getPhuongThucTT() { return phuongThucTT; }
    public void setPhuongThucTT(String phuongThucTT) { this.phuongThucTT = phuongThucTT; }
    public String getTrangThaiTT() { return trangThaiTT; }
    public void setTrangThaiTT(String trangThaiTT) { this.trangThaiTT = trangThaiTT; }
    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
    public LocalDateTime getNgayDat() { return ngayDat; }
    public void setNgayDat(LocalDateTime ngayDat) { this.ngayDat = ngayDat; }
    public LocalDateTime getNgayCapNhat() { return ngayCapNhat; }
    public void setNgayCapNhat(LocalDateTime ngayCapNhat) { this.ngayCapNhat = ngayCapNhat; }
    public List<OrderItemResponse> getItems() { return items; }
    public void setItems(List<OrderItemResponse> items) { this.items = items; }
}
