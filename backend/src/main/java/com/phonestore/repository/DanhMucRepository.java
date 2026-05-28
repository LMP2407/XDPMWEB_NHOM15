package com.phonestore.repository;

import com.phonestore.entity.DanhMuc;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.UUID;

public interface DanhMucRepository extends JpaRepository<DanhMuc, UUID> {
    boolean existsByDuongDan(String duongDan);
    Optional<DanhMuc> findByDuongDan(String duongDan);
    long countByDanhMucCha_MaDanhMuc(UUID maCha);
}