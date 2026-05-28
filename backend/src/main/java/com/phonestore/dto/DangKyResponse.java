package com.phonestore.dto;

public class DangKyResponse {
    private String message;
    private String maNguoiDung;

    public DangKyResponse() {}
    public DangKyResponse(String message, String maNguoiDung) {
        this.message = message;
        this.maNguoiDung = maNguoiDung;
    }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
    public String getMaNguoiDung() { return maNguoiDung; }
    public void setMaNguoiDung(String maNguoiDung) { this.maNguoiDung = maNguoiDung; }
}