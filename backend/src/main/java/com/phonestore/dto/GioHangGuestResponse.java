package com.phonestore.dto;

import java.math.BigDecimal;
import java.util.List;

public class GioHangGuestResponse {
    private List<GuestCartItem> items;
    private BigDecimal tongTien;

    public GioHangGuestResponse() {}
    public List<GuestCartItem> getItems() { return items; }
    public void setItems(List<GuestCartItem> items) { this.items = items; }
    public BigDecimal getTongTien() { return tongTien; }
    public void setTongTien(BigDecimal tongTien) { this.tongTien = tongTien; }

    public static class GuestCartItem {
        private String maSanPham;
        private String tenSanPham;
        private BigDecimal gia;
        private Integer soLuong;
        private BigDecimal thanhTien;

        public String getMaSanPham() { return maSanPham; }
        public void setMaSanPham(String maSanPham) { this.maSanPham = maSanPham; }
        public String getTenSanPham() { return tenSanPham; }
        public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }
        public BigDecimal getGia() { return gia; }
        public void setGia(BigDecimal gia) { this.gia = gia; }
        public Integer getSoLuong() { return soLuong; }
        public void setSoLuong(Integer soLuong) { this.soLuong = soLuong; }
        public BigDecimal getThanhTien() { return thanhTien; }
        public void setThanhTien(BigDecimal thanhTien) { this.thanhTien = thanhTien; }
    }
}