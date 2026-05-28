package com.phonestore.service;

import com.phonestore.dto.admin.UserResponse;
import com.phonestore.dto.common.PageResponse;
import com.phonestore.entity.NguoiDung;
import com.phonestore.exception.ResourceNotFoundException;
import com.phonestore.mapper.NguoiDungMapper;
import com.phonestore.repository.NguoiDungRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class AdminNguoiDungService {

    private final NguoiDungRepository nguoiDungRepository;
    private final NguoiDungMapper mapper;

    public AdminNguoiDungService(NguoiDungRepository nguoiDungRepository, NguoiDungMapper mapper) {
        this.nguoiDungRepository = nguoiDungRepository;
        this.mapper = mapper;
    }

    public PageResponse<UserResponse> list(String keyword, String vaiTro, Boolean hoatDong,
                                           int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("ngayTao").descending());
        Page<NguoiDung> result = nguoiDungRepository.adminSearch(keyword, vaiTro, hoatDong, pageable);
        return PageResponse.of(result, result.map(mapper::toResponse).getContent());
    }

    public UserResponse getById(UUID id) {
        return mapper.toResponse(findOrThrow(id));
    }

    @Transactional
    public UserResponse lock(UUID id) {
        NguoiDung u = findOrThrow(id);
        u.setHoatDong(false);
        return mapper.toResponse(nguoiDungRepository.save(u));
    }

    @Transactional
    public UserResponse unlock(UUID id) {
        NguoiDung u = findOrThrow(id);
        u.setHoatDong(true);
        return mapper.toResponse(nguoiDungRepository.save(u));
    }

    private NguoiDung findOrThrow(UUID id) {
        return nguoiDungRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy người dùng"));
    }
}
