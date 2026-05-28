package com.phonestore.repository;

import com.phonestore.entity.DonHang;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DonHangRepository extends JpaRepository<DonHang, UUID> {
    Optional<DonHang> findByMaDonHangAndNguoiDung_SoDienThoai(UUID maDonHang, String soDienThoai);

    @Query("SELECT dh FROM DonHang dh WHERE " +
           "(:trangThai IS NULL OR dh.trangThai = :trangThai) AND " +
           "(:keyword IS NULL OR LOWER(dh.diaChi.hoTen) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           " OR dh.diaChi.soDienThoai LIKE CONCAT('%', :keyword, '%'))")
    Page<DonHang> adminSearch(@Param("keyword") String keyword,
                              @Param("trangThai") String trangThai,
                              Pageable pageable);

    Page<DonHang> findByNguoiDung_MaNguoiDungOrderByNgayDatDesc(UUID maNguoiDung, Pageable pageable);

    long countByTrangThai(String trangThai);

    @Query("SELECT COALESCE(SUM(dh.tongTien), 0) FROM DonHang dh WHERE dh.trangThai = 'da_giao'")
    BigDecimal totalRevenueAllTime();

    @Query("SELECT COALESCE(SUM(dh.tongTien), 0) FROM DonHang dh " +
           "WHERE dh.trangThai = 'da_giao' AND dh.ngayDat >= :from AND dh.ngayDat < :to")
    BigDecimal totalRevenueBetween(@Param("from") LocalDateTime from, @Param("to") LocalDateTime to);

    @Query("SELECT ct.sanPham.maSanPham, ct.sanPham.tenSanPham, ct.sanPham.hinhAnh, " +
           " SUM(ct.soLuong), SUM(ct.donGia * ct.soLuong) " +
           "FROM ChiTietDonHang ct WHERE ct.donHang.trangThai = 'da_giao' " +
           "GROUP BY ct.sanPham.maSanPham, ct.sanPham.tenSanPham, ct.sanPham.hinhAnh " +
           "ORDER BY SUM(ct.soLuong) DESC")
    List<Object[]> topSellingProducts(Pageable pageable);
}