package com.phonestore.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public class TaoDonHangRequest {
    // BUG FIX: added @NotBlank/@NotNull/@NotEmpty/@Valid validation annotations
    // Previously missing, causing NullPointerException when required fields absent

    @NotBlank(message = "Họ tên không được để trống")
    private String hoTen;

    @NotBlank(message = "Số điện thoại không được để trống")
    private String soDienThoai;

    @NotBlank(message = "Địa chỉ không được để trống")
    private String diaChi;

    @NotBlank(message = "Tỉnh/Thành không được để trống")
    private String tinhThanh;

    private String phuongThucTT;
    private String ghiChu;

    @NotEmpty(message = "Đơn hàng phải có ít nhất một sản phẩm")
    @Valid
    private List<CartItemRequest> items;

    public TaoDonHangRequest() {}
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }
    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public String getTinhThanh() { return tinhThanh; }
    public void setTinhThanh(String tinhThanh) { this.tinhThanh = tinhThanh; }
    public String getPhuongThucTT() { return phuongThucTT; }
    public void setPhuongThucTT(String phuongThucTT) { this.phuongThucTT = phuongThucTT; }
    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
    public List<CartItemRequest> getItems() { return items; }
    public void setItems(List<CartItemRequest> items) { this.items = items; }
}
