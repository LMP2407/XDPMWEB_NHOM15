package com.phonestore.service;

import com.phonestore.dto.admin.CategoryRequest;
import com.phonestore.dto.admin.CategoryResponse;
import com.phonestore.entity.DanhMuc;
import com.phonestore.exception.BadRequestException;
import com.phonestore.exception.ResourceNotFoundException;
import com.phonestore.mapper.DanhMucMapper;
import com.phonestore.repository.DanhMucRepository;
import com.phonestore.util.SlugUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdminDanhMucService {

    private final DanhMucRepository danhMucRepository;
    private final DanhMucMapper mapper;

    public AdminDanhMucService(DanhMucRepository danhMucRepository, DanhMucMapper mapper) {
        this.danhMucRepository = danhMucRepository;
        this.mapper = mapper;
    }

    public List<CategoryResponse> list() {
        return danhMucRepository.findAll().stream().map(mapper::toResponse).collect(Collectors.toList());
    }

    public CategoryResponse getById(UUID id) {
        DanhMuc d = danhMucRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy danh mục"));
        return mapper.toResponse(d);
    }

    @Transactional
    public CategoryResponse create(CategoryRequest req) {
        DanhMuc d = new DanhMuc();
        d.setTenDanhMuc(req.getTenDanhMuc());
        d.setDuongDan(resolveSlug(req.getDuongDan(), req.getTenDanhMuc(), null));
        if (req.getMaCha() != null) {
            d.setDanhMucCha(danhMucRepository.findById(req.getMaCha())
                    .orElseThrow(() -> new BadRequestException("Danh mục cha không tồn tại")));
        }
        return mapper.toResponse(danhMucRepository.save(d));
    }

    @Transactional
    public CategoryResponse update(UUID id, CategoryRequest req) {
        DanhMuc d = danhMucRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy danh mục"));
        d.setTenDanhMuc(req.getTenDanhMuc());
        String newSlug = resolveSlug(req.getDuongDan(), req.getTenDanhMuc(), d.getDuongDan());
        d.setDuongDan(newSlug);
        if (req.getMaCha() != null) {
            if (req.getMaCha().equals(id)) throw new BadRequestException("Không thể là cha của chính nó");
            d.setDanhMucCha(danhMucRepository.findById(req.getMaCha())
                    .orElseThrow(() -> new BadRequestException("Danh mục cha không tồn tại")));
        } else {
            d.setDanhMucCha(null);
        }
        return mapper.toResponse(danhMucRepository.save(d));
    }

    @Transactional
    public void delete(UUID id) {
        DanhMuc d = danhMucRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy danh mục"));
        if (danhMucRepository.countByDanhMucCha_MaDanhMuc(id) > 0) {
            throw new BadRequestException("Danh mục có danh mục con, không thể xóa");
        }
        danhMucRepository.delete(d);
    }

    /** Tự generate slug nếu rỗng. Tránh trùng bằng cách thêm hậu tố nếu cần. */
    private String resolveSlug(String input, String fallbackName, String currentSlug) {
        String base = (input != null && !input.isBlank()) ? SlugUtil.slugify(input) : SlugUtil.slugify(fallbackName);
        if (base.isEmpty()) base = "danh-muc";
        if (base.equals(currentSlug)) return base;
        String slug = base;
        int i = 2;
        while (danhMucRepository.existsByDuongDan(slug)) {
            slug = base + "-" + i++;
        }
        return slug;
    }
}
