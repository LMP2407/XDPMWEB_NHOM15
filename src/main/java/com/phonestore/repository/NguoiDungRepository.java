package com.phonestore.repository;

import com.phonestore.entity.NguoiDung;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface NguoiDungRepository extends JpaRepository<NguoiDung, UUID> {
    Optional<NguoiDung> findByEmail(String email);
    Optional<NguoiDung> findBySoDienThoai(String soDienThoai);

    @Query("SELECT u FROM NguoiDung u WHERE " +
           "(:keyword IS NULL OR LOWER(u.hoTen) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           " OR LOWER(u.email) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
           " OR u.soDienThoai LIKE CONCAT('%', :keyword, '%')) AND " +
           "(:vaiTro IS NULL OR u.vaiTro = :vaiTro) AND " +
           "(:hoatDong IS NULL OR u.hoatDong = :hoatDong)")
    Page<NguoiDung> adminSearch(@Param("keyword") String keyword,
                                @Param("vaiTro") String vaiTro,
                                @Param("hoatDong") Boolean hoatDong,
                                Pageable pageable);
}