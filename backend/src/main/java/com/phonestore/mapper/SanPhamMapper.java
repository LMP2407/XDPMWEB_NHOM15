package com.phonestore.mapper;

import com.phonestore.dto.admin.ProductResponse;
import com.phonestore.entity.SanPham;
import org.springframework.stereotype.Component;

@Component
public class SanPhamMapper {

    public ProductResponse toResponse(SanPham sp) {
        if (sp == null) return null;
        ProductResponse r = new ProductResponse();
        r.setMaSanPham(sp.getMaSanPham());
        r.setTenSanPham(sp.getTenSanPham());
        r.setMoTa(sp.getMoTa());
        r.setGia(sp.getGia());
        r.setHinhAnh(sp.getHinhAnh());
        r.setMauSac(sp.getMauSac());
        r.setDungLuong(sp.getDungLuong());
        r.setThuongHieu(sp.getThuongHieu());
        r.setSoLuongTon(sp.getSoLuongTon());
        r.setHienThi(sp.getHienThi());
        r.setNgayTao(sp.getNgayTao());
        r.setNgayCapNhat(sp.getNgayCapNhat());
        if (sp.getDanhMuc() != null) {
            r.setMaDanhMuc(sp.getDanhMuc().getMaDanhMuc());
            r.setTenDanhMuc(sp.getDanhMuc().getTenDanhMuc());
        }
        return r;
    }
}
