package com.phonestore.dto.user;

import java.math.BigDecimal;
import java.util.List;

public class CartResponse {
    private List<CartItemResponse> items;
    private BigDecimal tongTien;
    private int soLoaiSanPham;
    private int tongSoLuong;

    public CartResponse() {}

    public List<CartItemResponse> getItems() { return items; }
    public void setItems(List<CartItemResponse> items) { this.items = items; }
    public BigDecimal getTongTien() { return tongTien; }
    public void setTongTien(BigDecimal tongTien) { this.tongTien = tongTien; }
    public int getSoLoaiSanPham() { return soLoaiSanPham; }
    public void setSoLoaiSanPham(int soLoaiSanPham) { this.soLoaiSanPham = soLoaiSanPham; }
    public int getTongSoLuong() { return tongSoLuong; }
    public void setTongSoLuong(int tongSoLuong) { this.tongSoLuong = tongSoLuong; }
}
