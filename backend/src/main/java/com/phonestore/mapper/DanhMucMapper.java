package com.phonestore.mapper;

import com.phonestore.dto.admin.CategoryResponse;
import com.phonestore.entity.DanhMuc;
import org.springframework.stereotype.Component;

@Component
public class DanhMucMapper {

    public CategoryResponse toResponse(DanhMuc d) {
        if (d == null) return null;
        CategoryResponse r = new CategoryResponse();
        r.setMaDanhMuc(d.getMaDanhMuc());
        r.setTenDanhMuc(d.getTenDanhMuc());
        r.setDuongDan(d.getDuongDan());
        r.setNgayTao(d.getNgayTao());
        if (d.getDanhMucCha() != null) {
            r.setMaCha(d.getDanhMucCha().getMaDanhMuc());
            r.setTenDanhMucCha(d.getDanhMucCha().getTenDanhMuc());
        }
        return r;
    }
}
