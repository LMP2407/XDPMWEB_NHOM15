package com.phonestore.dto;

import java.math.BigDecimal;

public class TaoDonHangResponse {
    private String maDonHang;
    private BigDecimal tongTien;
    private String trangThai;

    public TaoDonHangResponse() {}
    public TaoDonHangResponse(String maDonHang, BigDecimal tongTien, String trangThai) {
        this.maDonHang = maDonHang;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
    }
    public String getMaDonHang() { return maDonHang; }
    public void setMaDonHang(String maDonHang) { this.maDonHang = maDonHang; }
    public BigDecimal getTongTien() { return tongTien; }
    public void setTongTien(BigDecimal tongTien) { this.tongTien = tongTien; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}