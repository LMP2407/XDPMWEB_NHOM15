package com.phonestore.dto;

public class DangNhapResponse {
    private String token;
    private String hoTen;
    private String email;

    public DangNhapResponse() {}
    public DangNhapResponse(String token, String hoTen, String email) {
        this.token = token;
        this.hoTen = hoTen;
        this.email = email;
    }
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
}