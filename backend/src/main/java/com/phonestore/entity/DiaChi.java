package com.phonestore.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "dia_chi")
public class DiaChi {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "ma_dia_chi", updatable = false, nullable = false)
    private UUID maDiaChi;

    @ManyToOne
    @JoinColumn(name = "ma_nguoi_dung", nullable = false)
    private NguoiDung nguoiDung;

    @Column(name = "ho_ten", nullable = false, length = 100)
    private String hoTen;

    @Column(name = "so_dien_thoai", nullable = false, length = 20)
    private String soDienThoai;

    @Column(name = "so_nha", nullable = false)
    private String soNha;

    @Column(name = "phuong_xa", length = 100)
    private String phuongXa;

    @Column(name = "quan_huyen", length = 100)
    private String quanHuyen;

    @Column(name = "tinh_thanh", nullable = false, length = 100)
    private String tinhThanh;

    @Column(name = "mac_dinh")
    private Boolean macDinh = false;

    @Column(name = "ngay_tao")
    private LocalDateTime ngayTao = LocalDateTime.now();

    public DiaChi() {}

    public UUID getMaDiaChi() { return maDiaChi; }
    public void setMaDiaChi(UUID maDiaChi) { this.maDiaChi = maDiaChi; }
    public NguoiDung getNguoiDung() { return nguoiDung; }
    public void setNguoiDung(NguoiDung nguoiDung) { this.nguoiDung = nguoiDung; }
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }
    public String getSoNha() { return soNha; }
    public void setSoNha(String soNha) { this.soNha = soNha; }
    public String getPhuongXa() { return phuongXa; }
    public void setPhuongXa(String phuongXa) { this.phuongXa = phuongXa; }
    public String getQuanHuyen() { return quanHuyen; }
    public void setQuanHuyen(String quanHuyen) { this.quanHuyen = quanHuyen; }
    public String getTinhThanh() { return tinhThanh; }
    public void setTinhThanh(String tinhThanh) { this.tinhThanh = tinhThanh; }
    public Boolean getMacDinh() { return macDinh; }
    public void setMacDinh(Boolean macDinh) { this.macDinh = macDinh; }
    public LocalDateTime getNgayTao() { return ngayTao; }
    public void setNgayTao(LocalDateTime ngayTao) { this.ngayTao = ngayTao; }
}