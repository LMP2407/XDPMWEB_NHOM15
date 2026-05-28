package com.phonestore.dto.admin;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.UUID;

public class CategoryRequest {
    @NotBlank(message = "Tên danh mục không được để trống")
    @Size(max = 100)
    private String tenDanhMuc;

    /** Optional. Nếu null sẽ tự generate từ tên */
    private String duongDan;

    /** Optional, parent category */
    private UUID maCha;

    public CategoryRequest() {}
    public String getTenDanhMuc() { return tenDanhMuc; }
    public void setTenDanhMuc(String tenDanhMuc) { this.tenDanhMuc = tenDanhMuc; }
    public String getDuongDan() { return duongDan; }
    public void setDuongDan(String duongDan) { this.duongDan = duongDan; }
    public UUID getMaCha() { return maCha; }
    public void setMaCha(UUID maCha) { this.maCha = maCha; }
}
