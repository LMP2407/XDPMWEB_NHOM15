package com.phonestore.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

public class CartItemRequest {
    @NotNull(message = "Mã sản phẩm không được để trống")
    private UUID maSanPham;
    @NotNull(message = "Số lượng không được để trống")
    @Min(value = 1, message = "Số lượng phải lớn hơn 0")
    private Integer soLuong;

    public CartItemRequest() {}
    public UUID getMaSanPham() { return maSanPham; }
    public void setMaSanPham(UUID maSanPham) { this.maSanPham = maSanPham; }
    public Integer getSoLuong() { return soLuong; }
    public void setSoLuong(Integer soLuong) { this.soLuong = soLuong; }
}