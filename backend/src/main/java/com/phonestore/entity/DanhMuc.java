package com.phonestore.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "danh_muc")
public class DanhMuc {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ma_danh_muc", updatable = false, nullable = false)
    private UUID maDanhMuc;

    @Column(name = "ten_danh_muc", nullable = false, length = 100)
    private String tenDanhMuc;

    @Column(name = "duong_dan", nullable = false, unique = true, length = 100)
    private String duongDan;

    @ManyToOne
    @JoinColumn(name = "ma_cha")
    private DanhMuc danhMucCha;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao = LocalDateTime.now();

    // Constructors
    public DanhMuc() {}

    // Getters and Setters
    public UUID getMaDanhMuc() { return maDanhMuc; }
    public void setMaDanhMuc(UUID maDanhMuc) { this.maDanhMuc = maDanhMuc; }

    public String getTenDanhMuc() { return tenDanhMuc; }
    public void setTenDanhMuc(String tenDanhMuc) { this.tenDanhMuc = tenDanhMuc; }

    public String getDuongDan() { return duongDan; }
    public void setDuongDan(String duongDan) { this.duongDan = duongDan; }

    public DanhMuc getDanhMucCha() { return danhMucCha; }
    public void setDanhMucCha(DanhMuc danhMucCha) { this.danhMucCha = danhMucCha; }

    public LocalDateTime getNgayTao() { return ngayTao; }
    public void setNgayTao(LocalDateTime ngayTao) { this.ngayTao = ngayTao; }
}