package com.phonestore.service;

import com.phonestore.dto.CartItemRequest;
import com.phonestore.dto.user.AddToCartRequest;
import com.phonestore.dto.user.CartItemResponse;
import com.phonestore.dto.user.CartResponse;
import com.phonestore.entity.GioHang;
import com.phonestore.entity.NguoiDung;
import com.phonestore.entity.SanPham;
import com.phonestore.exception.BadRequestException;
import com.phonestore.exception.ResourceNotFoundException;
import com.phonestore.repository.GioHangRepository;
import com.phonestore.repository.NguoiDungRepository;
import com.phonestore.repository.SanPhamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class GioHangService {
    @Autowired private GioHangRepository gioHangRepository;
    @Autowired private SanPhamRepository sanPhamRepository;
    @Autowired private NguoiDungRepository nguoiDungRepository;

    @Transactional
    public void mergeGuestCart(UUID maNguoiDung, List<CartItemRequest> guestCart) {
        NguoiDung nguoiDung = nguoiDungRepository.findById(maNguoiDung)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
        for (CartItemRequest item : guestCart) {
            SanPham sanPham = sanPhamRepository.findById(item.getMaSanPham())
                    .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại"));
            GioHang existing = gioHangRepository.findByNguoiDungAndSanPham(nguoiDung, sanPham).orElse(null);
            if (existing != null) {
                existing.setSoLuong(existing.getSoLuong() + item.getSoLuong());
                gioHangRepository.save(existing);
            } else {
                GioHang gh = new GioHang();
                gh.setNguoiDung(nguoiDung);
                gh.setSanPham(sanPham);
                gh.setSoLuong(item.getSoLuong());
                gioHangRepository.save(gh);
            }
        }
    }

    // ========== API cho user đã đăng nhập ==========

    public CartResponse getCart(NguoiDung nguoiDung) {
        List<GioHang> entries = gioHangRepository.findByNguoiDung(nguoiDung);
        return buildResponse(entries);
    }

    @Transactional
    public CartResponse addToCart(NguoiDung nguoiDung, AddToCartRequest req) {
        SanPham sp = sanPhamRepository.findById(req.getMaSanPham())
                .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm không tồn tại"));
        if (!Boolean.TRUE.equals(sp.getHienThi())) {
            throw new BadRequestException("Sản phẩm không khả dụng");
        }
        GioHang existing = gioHangRepository.findByNguoiDungAndSanPham(nguoiDung, sp).orElse(null);
        int newQty = (existing == null ? 0 : existing.getSoLuong()) + req.getSoLuong();
        if (sp.getSoLuongTon() < newQty) {
            throw new BadRequestException("Số lượng vượt quá tồn kho (" + sp.getSoLuongTon() + ")");
        }
        if (existing != null) {
            existing.setSoLuong(newQty);
            gioHangRepository.save(existing);
        } else {
            GioHang gh = new GioHang();
            gh.setNguoiDung(nguoiDung);
            gh.setSanPham(sp);
            gh.setSoLuong(req.getSoLuong());
            gioHangRepository.save(gh);
        }
        return getCart(nguoiDung);
    }

    @Transactional
    public CartResponse updateQuantity(NguoiDung nguoiDung, UUID maSanPham, int soLuong) {
        SanPham sp = sanPhamRepository.findById(maSanPham)
                .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm không tồn tại"));
        if (soLuong <= 0) {
            gioHangRepository.deleteByNguoiDungAndSanPham(nguoiDung, sp);
            return getCart(nguoiDung);
        }
        if (sp.getSoLuongTon() < soLuong) {
            throw new BadRequestException("Số lượng vượt quá tồn kho (" + sp.getSoLuongTon() + ")");
        }
        GioHang gh = gioHangRepository.findByNguoiDungAndSanPham(nguoiDung, sp)
                .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm không có trong giỏ"));
        gh.setSoLuong(soLuong);
        gioHangRepository.save(gh);
        return getCart(nguoiDung);
    }

    @Transactional
    public CartResponse removeItem(NguoiDung nguoiDung, UUID maSanPham) {
        SanPham sp = sanPhamRepository.findById(maSanPham)
                .orElseThrow(() -> new ResourceNotFoundException("Sản phẩm không tồn tại"));
        gioHangRepository.deleteByNguoiDungAndSanPham(nguoiDung, sp);
        return getCart(nguoiDung);
    }

    @Transactional
    public void clearCart(NguoiDung nguoiDung) {
        gioHangRepository.deleteAllByNguoiDung(nguoiDung.getMaNguoiDung());
    }

    private CartResponse buildResponse(List<GioHang> entries) {
        CartResponse r = new CartResponse();
        List<CartItemResponse> items = new ArrayList<>();
        BigDecimal tong = BigDecimal.ZERO;
        int tongSL = 0;
        for (GioHang gh : entries) {
            SanPham sp = gh.getSanPham();
            CartItemResponse i = new CartItemResponse();
            i.setMaGioHang(gh.getMaGioHang());
            i.setMaSanPham(sp.getMaSanPham());
            i.setTenSanPham(sp.getTenSanPham());
            i.setHinhAnh(sp.getHinhAnh());
            i.setGia(sp.getGia());
            i.setSoLuong(gh.getSoLuong());
            i.setSoLuongTon(sp.getSoLuongTon());
            BigDecimal thanhTien = sp.getGia().multiply(BigDecimal.valueOf(gh.getSoLuong()));
            i.setThanhTien(thanhTien);
            tong = tong.add(thanhTien);
            tongSL += gh.getSoLuong();
            items.add(i);
        }
        r.setItems(items);
        r.setTongTien(tong);
        r.setSoLoaiSanPham(items.size());
        r.setTongSoLuong(tongSL);
        return r;
    }
}
