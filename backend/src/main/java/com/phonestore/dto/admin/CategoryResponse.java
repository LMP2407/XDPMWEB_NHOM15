package com.phonestore.dto.admin;

import java.time.LocalDateTime;
import java.util.UUID;

public class CategoryResponse {
    private UUID maDanhMuc;
    private String tenDanhMuc;
    private String duongDan;
    private UUID maCha;
    private String tenDanhMucCha;
    private LocalDateTime ngayTao;

    public CategoryResponse() {}

    public UUID getMaDanhMuc() { return maDanhMuc; }
    public void setMaDanhMuc(UUID maDanhMuc) { this.maDanhMuc = maDanhMuc; }
    public String getTenDanhMuc() { return tenDanhMuc; }
    public void setTenDanhMuc(String tenDanhMuc) { this.tenDanhMuc = tenDanhMuc; }
    public String getDuongDan() { return duongDan; }
    public void setDuongDan(String duongDan) { this.duongDan = duongDan; }
    public UUID getMaCha() { return maCha; }
    public void setMaCha(UUID maCha) { this.maCha = maCha; }
    public String getTenDanhMucCha() { return tenDanhMucCha; }
    public void setTenDanhMucCha(String tenDanhMucCha) { this.tenDanhMucCha = tenDanhMucCha; }
    public LocalDateTime getNgayTao() { return ngayTao; }
    public void setNgayTao(LocalDateTime ngayTao) { this.ngayTao = ngayTao; }
}
