package com.phonestore.mapper;

import com.phonestore.dto.admin.UserResponse;
import com.phonestore.entity.NguoiDung;
import org.springframework.stereotype.Component;

@Component
public class NguoiDungMapper {

    public UserResponse toResponse(NguoiDung u) {
        if (u == null) return null;
        UserResponse r = new UserResponse();
        r.setMaNguoiDung(u.getMaNguoiDung());
        r.setHoTen(u.getHoTen());
        r.setEmail(u.getEmail());
        r.setSoDienThoai(u.getSoDienThoai());
        r.setVaiTro(u.getVaiTro());
        r.setHoatDong(u.getHoatDong());
        r.setNgayTao(u.getNgayTao());
        return r;
    }
}
