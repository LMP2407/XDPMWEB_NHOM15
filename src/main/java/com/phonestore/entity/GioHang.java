package com.phonestore.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "gio_hang", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ma_nguoi_dung", "ma_san_pham"})
})
public class GioHang {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ma_gio_hang", updatable = false, nullable = false)
    private UUID maGioHang;

    @ManyToOne
    @JoinColumn(name = "ma_nguoi_dung", nullable = false)
    private NguoiDung nguoiDung;

    @ManyToOne
    @JoinColumn(name = "ma_san_pham", nullable = false)
    private SanPham sanPham;

    @Column(name = "so_luong", nullable = false)
    private Integer soLuong = 1;

    @Column(name = "ngay_them")
    private LocalDateTime ngayThem = LocalDateTime.now();

    public GioHang() {}

    public UUID getMaGioHang() { return maGioHang; }
    public void setMaGioHang(UUID maGioHang) { this.maGioHang = maGioHang; }
    public NguoiDung getNguoiDung() { return nguoiDung; }
    public void setNguoiDung(NguoiDung nguoiDung) { this.nguoiDung = nguoiDung; }
    public SanPham getSanPham() { return sanPham; }
    public void setSanPham(SanPham sanPham) { this.sanPham = sanPham; }
    public Integer getSoLuong() { return soLuong; }
    public void setSoLuong(Integer soLuong) { this.soLuong = soLuong; }
    public LocalDateTime getNgayThem() { return ngayThem; }
    public void setNgayThem(LocalDateTime ngayThem) { this.ngayThem = ngayThem; }
}