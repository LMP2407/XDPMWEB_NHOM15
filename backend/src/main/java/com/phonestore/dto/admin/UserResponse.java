package com.phonestore.dto.admin;

import java.time.LocalDateTime;
import java.util.UUID;

public class UserResponse {
    private UUID maNguoiDung;
    private String hoTen;
    private String email;
    private String soDienThoai;
    private String vaiTro;
    private Boolean hoatDong;
    private LocalDateTime ngayTao;

    public UserResponse() {}

    public UUID getMaNguoiDung() { return maNguoiDung; }
    public void setMaNguoiDung(UUID maNguoiDung) { this.maNguoiDung = maNguoiDung; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }
    public String getVaiTro() { return vaiTro; }
    public void setVaiTro(String vaiTro) { this.vaiTro = vaiTro; }
    public Boolean getHoatDong() { return hoatDong; }
    public void setHoatDong(Boolean hoatDong) { this.hoatDong = hoatDong; }
    public LocalDateTime getNgayTao() { return ngayTao; }
    public void setNgayTao(LocalDateTime ngayTao) { this.ngayTao = ngayTao; }
}
