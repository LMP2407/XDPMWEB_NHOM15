package com.phonestore.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "don_hang")
public class DonHang {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ma_don_hang", updatable = false, nullable = false)
    private UUID maDonHang;

    @ManyToOne
    @JoinColumn(name = "ma_nguoi_dung", nullable = false)
    private NguoiDung nguoiDung;

    @ManyToOne
    @JoinColumn(name = "ma_dia_chi")
    private DiaChi diaChi;

    @Column(name = "tong_tien", nullable = false, precision = 15, scale = 2)
    private BigDecimal tongTien;

    @Column(name = "trang_thai", nullable = false, length = 30)
    private String trangThai = "cho_xac_nhan";

    @Column(name = "phuong_thuc_tt", length = 50)
    private String phuongThucTT;

    @Column(name = "trang_thai_tt", length = 20)
    private String trangThaiTT = "chua_thanh_toan";

    @Column(name = "ghi_chu", columnDefinition = "TEXT")
    private String ghiChu;

    @Column(name = "ngay_dat")
    private LocalDateTime ngayDat = LocalDateTime.now();

    @Column(name = "ngay_cap_nhat")
    private LocalDateTime ngayCapNhat;

    @PreUpdate
    public void preUpdate() { this.ngayCapNhat = LocalDateTime.now(); }

    public DonHang() {}

    public UUID getMaDonHang() { return maDonHang; }
    public void setMaDonHang(UUID maDonHang) { this.maDonHang = maDonHang; }
    public NguoiDung getNguoiDung() { return nguoiDung; }
    public void setNguoiDung(NguoiDung nguoiDung) { this.nguoiDung = nguoiDung; }
    public DiaChi getDiaChi() { return diaChi; }
    public void setDiaChi(DiaChi diaChi) { this.diaChi = diaChi; }
    public BigDecimal getTongTien() { return tongTien; }
    public void setTongTien(BigDecimal tongTien) { this.tongTien = tongTien; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public String getPhuongThucTT() { return phuongThucTT; }
    public void setPhuongThucTT(String phuongThucTT) { this.phuongThucTT = phuongThucTT; }
    public String getTrangThaiTT() { return trangThaiTT; }
    public void setTrangThaiTT(String trangThaiTT) { this.trangThaiTT = trangThaiTT; }
    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }
    public LocalDateTime getNgayDat() { return ngayDat; }
    public void setNgayDat(LocalDateTime ngayDat) { this.ngayDat = ngayDat; }
    public LocalDateTime getNgayCapNhat() { return ngayCapNhat; }
    public void setNgayCapNhat(LocalDateTime ngayCapNhat) { this.ngayCapNhat = ngayCapNhat; }
}