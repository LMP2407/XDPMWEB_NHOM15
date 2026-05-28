package com.phonestore.repository;

import com.phonestore.entity.ChiTietDonHang;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.UUID;

public interface ChiTietDonHangRepository extends JpaRepository<ChiTietDonHang, UUID> {
    List<ChiTietDonHang> findByDonHang_MaDonHang(UUID maDonHang);
}