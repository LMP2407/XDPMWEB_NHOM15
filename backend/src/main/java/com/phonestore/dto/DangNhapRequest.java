package com.phonestore.dto;

import jakarta.validation.constraints.NotBlank;

public class DangNhapRequest {
    @NotBlank(message = "Email không được để trống")
    private String email;
    @NotBlank(message = "Mật khẩu không được để trống")
    private String matKhau;

    public DangNhapRequest() {}
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getMatKhau() { return matKhau; }
    public void setMatKhau(String matKhau) { this.matKhau = matKhau; }
}