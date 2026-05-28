package com.phonestore.repository;

import com.phonestore.entity.GioHang;
import com.phonestore.entity.NguoiDung;
import com.phonestore.entity.SanPham;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GioHangRepository extends JpaRepository<GioHang, UUID> {
    List<GioHang> findByNguoiDung(NguoiDung nguoiDung);
    List<GioHang> findByNguoiDung_MaNguoiDung(UUID maNguoiDung);
    Optional<GioHang> findByNguoiDungAndSanPham(NguoiDung nguoiDung, SanPham sanPham);
    void deleteByNguoiDungAndSanPham(NguoiDung nguoiDung, SanPham sanPham);

    @Modifying
    @Query("DELETE FROM GioHang g WHERE g.nguoiDung.maNguoiDung = :maNguoiDung")
    void deleteAllByNguoiDung(@Param("maNguoiDung") UUID maNguoiDung);
}