package com.phonestore.repository;

import com.phonestore.entity.DiaChi;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.UUID;

public interface DiaChiRepository extends JpaRepository<DiaChi, UUID> {
}