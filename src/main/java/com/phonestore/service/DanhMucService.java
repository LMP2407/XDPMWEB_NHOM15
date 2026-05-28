package com.phonestore.service;

import com.phonestore.dto.SanPhamResponse;
import com.phonestore.entity.DanhMuc;
import com.phonestore.repository.DanhMucRepository;
import com.phonestore.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class DanhMucService {
    @Autowired
    private DanhMucRepository danhMucRepository;
    @Autowired
    private SanPhamRepository sanPhamRepository;
    @Autowired
    private SanPhamService sanPhamService;

    public List<DanhMuc> getDanhSachDanhMuc() {
        return danhMucRepository.findAll();
    }

    public Page<SanPhamResponse> getSanPhamTheoDanhMuc(UUID maDanhMuc, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return sanPhamRepository.findByHienThiTrueAndDanhMuc_MaDanhMuc(maDanhMuc, pageable)
                .map(sp -> sanPhamService.getChiTietSanPham(sp.getMaSanPham()));
    }
}