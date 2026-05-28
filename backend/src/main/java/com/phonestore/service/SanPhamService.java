package com.phonestore.service;

import com.phonestore.dto.SanPhamResponse;
import com.phonestore.entity.SanPham;
import com.phonestore.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.UUID;

@Service
public class SanPhamService {
    @Autowired
    private SanPhamRepository sanPhamRepository;

    private SanPhamResponse convertToResponse(SanPham sp) {
        SanPhamResponse res = new SanPhamResponse();
        res.setMaSanPham(sp.getMaSanPham());
        res.setTenSanPham(sp.getTenSanPham());
        res.setMoTa(sp.getMoTa());
        res.setGia(sp.getGia());
        res.setHinhAnh(sp.getHinhAnh());
        res.setMauSac(sp.getMauSac());
        res.setDungLuong(sp.getDungLuong());
        res.setThuongHieu(sp.getThuongHieu());
        res.setSoLuongTon(sp.getSoLuongTon());
        if (sp.getDanhMuc() != null) {
            res.setTenDanhMuc(sp.getDanhMuc().getTenDanhMuc());
            res.setMaDanhMuc(sp.getDanhMuc().getMaDanhMuc());
        }
        return res;
    }

    public Page<SanPhamResponse> getDanhSachSanPham(int page, int size, String sort) {
        Sort sortOrder = Sort.by("ngayTao").descending();
        if (sort != null && !sort.isEmpty()) {
            String[] parts = sort.split(",");
            if (parts.length == 2) {
                Sort.Direction direction = parts[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
                sortOrder = Sort.by(direction, parts[0]);
            }
        }
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        return sanPhamRepository.findByHienThiTrue(pageable).map(this::convertToResponse);
    }

    public SanPhamResponse getChiTietSanPham(UUID id) {
        SanPham sp = sanPhamRepository.findById(id).orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));
        return convertToResponse(sp);
    }

    public Page<SanPhamResponse> timKiemVaLoc(String keyword, String thuongHieu, BigDecimal giaMin, BigDecimal giaMax, UUID maDanhMuc, int page, int size, String sort) {
        Sort sortOrder = Sort.by("ngayTao").descending();
        if (sort != null && !sort.isEmpty()) {
            String[] parts = sort.split(",");
            if (parts.length == 2) {
                Sort.Direction direction = parts[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
                sortOrder = Sort.by(direction, parts[0]);
            }
        }
        Pageable pageable = PageRequest.of(page, size, sortOrder);
        return sanPhamRepository.searchAndFilter(keyword, thuongHieu, giaMin, giaMax, maDanhMuc, pageable).map(this::convertToResponse);
    }
}