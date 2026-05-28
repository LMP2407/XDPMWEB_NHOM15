package com.phonestore.service;

import com.phonestore.dto.admin.ProductRequest;
import com.phonestore.dto.admin.ProductResponse;
import com.phonestore.dto.common.PageResponse;
import com.phonestore.entity.DanhMuc;
import com.phonestore.entity.SanPham;
import com.phonestore.exception.BadRequestException;
import com.phonestore.exception.ResourceNotFoundException;
import com.phonestore.mapper.SanPhamMapper;
import com.phonestore.repository.DanhMucRepository;
import com.phonestore.repository.SanPhamRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class AdminSanPhamService {

    private final SanPhamRepository sanPhamRepository;
    private final DanhMucRepository danhMucRepository;
    private final SanPhamMapper mapper;

    public AdminSanPhamService(SanPhamRepository sanPhamRepository,
                               DanhMucRepository danhMucRepository,
                               SanPhamMapper mapper) {
        this.sanPhamRepository = sanPhamRepository;
        this.danhMucRepository = danhMucRepository;
        this.mapper = mapper;
    }

    public PageResponse<ProductResponse> list(String keyword, String thuongHieu,
                                              BigDecimal giaMin, BigDecimal giaMax,
                                              UUID maDanhMuc, Boolean hienThi,
                                              int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, parseSort(sort));
        Page<SanPham> result = sanPhamRepository.adminSearch(keyword, thuongHieu, giaMin, giaMax,
                maDanhMuc, hienThi, pageable);
        return PageResponse.of(result, result.map(mapper::toResponse).getContent());
    }

    public ProductResponse getById(UUID id) {
        SanPham sp = sanPhamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm: " + id));
        return mapper.toResponse(sp);
    }

    @Transactional
    public ProductResponse create(ProductRequest req) {
        DanhMuc dm = danhMucRepository.findById(req.getMaDanhMuc())
                .orElseThrow(() -> new BadRequestException("Danh mục không tồn tại"));
        SanPham sp = new SanPham();
        applyToEntity(sp, req, dm);
        sp = sanPhamRepository.save(sp);
        return mapper.toResponse(sp);
    }

    @Transactional
    public ProductResponse update(UUID id, ProductRequest req) {
        SanPham sp = sanPhamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm: " + id));
        DanhMuc dm = danhMucRepository.findById(req.getMaDanhMuc())
                .orElseThrow(() -> new BadRequestException("Danh mục không tồn tại"));
        applyToEntity(sp, req, dm);
        return mapper.toResponse(sanPhamRepository.save(sp));
    }

    /** Soft delete bằng cách set hienThi = false */
    @Transactional
    public void softDelete(UUID id) {
        SanPham sp = sanPhamRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy sản phẩm: " + id));
        sp.setHienThi(false);
        sanPhamRepository.save(sp);
    }

    private void applyToEntity(SanPham sp, ProductRequest req, DanhMuc dm) {
        sp.setTenSanPham(req.getTenSanPham());
        sp.setMoTa(req.getMoTa());
        sp.setGia(req.getGia());
        sp.setHinhAnh(req.getHinhAnh());
        sp.setMauSac(req.getMauSac());
        sp.setDungLuong(req.getDungLuong());
        sp.setThuongHieu(req.getThuongHieu());
        sp.setSoLuongTon(req.getSoLuongTon());
        sp.setDanhMuc(dm);
        if (req.getHienThi() != null) sp.setHienThi(req.getHienThi());
    }

    private Sort parseSort(String sort) {
        if (sort == null || sort.isBlank()) return Sort.by("ngayTao").descending();
        String[] parts = sort.split(",");
        if (parts.length != 2) return Sort.by(sort);
        Sort.Direction dir = parts[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
        return Sort.by(dir, parts[0]);
    }
}
