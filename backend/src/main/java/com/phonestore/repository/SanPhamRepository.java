package com.phonestore.repository;

import com.phonestore.entity.SanPham;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.math.BigDecimal;
import java.util.UUID;

public interface SanPhamRepository extends JpaRepository<SanPham, UUID> {
    Page<SanPham> findByHienThiTrue(Pageable pageable);
    Page<SanPham> findByHienThiTrueAndTenSanPhamContainingIgnoreCase(String keyword, Pageable pageable);
    Page<SanPham> findByHienThiTrueAndThuongHieu(String thuongHieu, Pageable pageable);
    Page<SanPham> findByHienThiTrueAndGiaBetween(BigDecimal min, BigDecimal max, Pageable pageable);
    Page<SanPham> findByHienThiTrueAndDanhMuc_MaDanhMuc(UUID maDanhMuc, Pageable pageable);
    
    @Query("SELECT sp FROM SanPham sp WHERE sp.hienThi = true AND " +
           "(:keyword IS NULL OR LOWER(sp.tenSanPham) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
           "(:thuongHieu IS NULL OR sp.thuongHieu = :thuongHieu) AND " +
           "(:giaMin IS NULL OR sp.gia >= :giaMin) AND " +
           "(:giaMax IS NULL OR sp.gia <= :giaMax) AND " +
           "(:maDanhMuc IS NULL OR sp.danhMuc.maDanhMuc = :maDanhMuc)")
    Page<SanPham> searchAndFilter(@Param("keyword") String keyword,
                                  @Param("thuongHieu") String thuongHieu,
                                  @Param("giaMin") BigDecimal giaMin,
                                  @Param("giaMax") BigDecimal giaMax,
                                  @Param("maDanhMuc") UUID maDanhMuc,
                                  Pageable pageable);

    /** Admin search: không lọc hien_thi để admin thấy cả sản phẩm đã ẩn */
    @Query("SELECT sp FROM SanPham sp WHERE " +
           "(:keyword IS NULL OR LOWER(sp.tenSanPham) LIKE LOWER(CONCAT('%', :keyword, '%'))) AND " +
           "(:thuongHieu IS NULL OR sp.thuongHieu = :thuongHieu) AND " +
           "(:giaMin IS NULL OR sp.gia >= :giaMin) AND " +
           "(:giaMax IS NULL OR sp.gia <= :giaMax) AND " +
           "(:maDanhMuc IS NULL OR sp.danhMuc.maDanhMuc = :maDanhMuc) AND " +
           "(:hienThi IS NULL OR sp.hienThi = :hienThi)")
    Page<SanPham> adminSearch(@Param("keyword") String keyword,
                              @Param("thuongHieu") String thuongHieu,
                              @Param("giaMin") BigDecimal giaMin,
                              @Param("giaMax") BigDecimal giaMax,
                              @Param("maDanhMuc") UUID maDanhMuc,
                              @Param("hienThi") Boolean hienThi,
                              Pageable pageable);

    long countByHienThiTrue();
}