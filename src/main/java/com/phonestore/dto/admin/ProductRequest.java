package com.phonestore.dto.admin;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.util.UUID;

public class ProductRequest {
    @NotBlank(message = "Tên sản phẩm không được để trống")
    @Size(max = 200)
    private String tenSanPham;

    private String moTa;

    @NotNull(message = "Giá không được để trống")
    @DecimalMin(value = "0.0", inclusive = false, message = "Giá phải lớn hơn 0")
    private BigDecimal gia;

    private String hinhAnh;
    private String mauSac;
    private String dungLuong;
    private String thuongHieu;

    @NotNull
    @Min(value = 0, message = "Số lượng tồn không được âm")
    private Integer soLuongTon;

    @NotNull(message = "Danh mục không được để trống")
    private UUID maDanhMuc;

    private Boolean hienThi = true;

    public ProductRequest() {}

    public String getTenSanPham() { return tenSanPham; }
    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }
    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
    public BigDecimal getGia() { return gia; }
    public void setGia(BigDecimal gia) { this.gia = gia; }
    public String getHinhAnh() { return hinhAnh; }
    public void setHinhAnh(String hinhAnh) { this.hinhAnh = hinhAnh; }
    public String getMauSac() { return mauSac; }
    public void setMauSac(String mauSac) { this.mauSac = mauSac; }
    public String getDungLuong() { return dungLuong; }
    public void setDungLuong(String dungLuong) { this.dungLuong = dungLuong; }
    public String getThuongHieu() { return thuongHieu; }
    public void setThuongHieu(String thuongHieu) { this.thuongHieu = thuongHieu; }
    public Integer getSoLuongTon() { return soLuongTon; }
    public void setSoLuongTon(Integer soLuongTon) { this.soLuongTon = soLuongTon; }
    public UUID getMaDanhMuc() { return maDanhMuc; }
    public void setMaDanhMuc(UUID maDanhMuc) { this.maDanhMuc = maDanhMuc; }
    public Boolean getHienThi() { return hienThi; }
    public void setHienThi(Boolean hienThi) { this.hienThi = hienThi; }
}
