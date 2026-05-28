package com.phonestore.service;

import com.phonestore.dto.CartItemRequest;
import com.phonestore.dto.TaoDonHangRequest;
import com.phonestore.dto.TraCuuDonHangResponse;
import com.phonestore.entity.*;
import com.phonestore.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DonHangService {
    @Autowired private NguoiDungRepository nguoiDungRepository;
    @Autowired private DiaChiRepository diaChiRepository;
    @Autowired private DonHangRepository donHangRepository;
    @Autowired private ChiTietDonHangRepository chiTietDonHangRepository;
    @Autowired private SanPhamRepository sanPhamRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    @Transactional
    public DonHang taoDonHang(TaoDonHangRequest request) {
        NguoiDung nguoiDung = nguoiDungRepository.findBySoDienThoai(request.getSoDienThoai())
                .orElseGet(() -> {
                    NguoiDung newUser = new NguoiDung();
                    newUser.setHoTen(request.getHoTen());
                    newUser.setSoDienThoai(request.getSoDienThoai());
                    newUser.setEmail(request.getSoDienThoai() + "@guest.com");
                    newUser.setMatKhau(passwordEncoder.encode(UUID.randomUUID().toString()));
                    newUser.setHoatDong(true);
                    return nguoiDungRepository.save(newUser);
                });

        DiaChi diaChi = new DiaChi();
        diaChi.setNguoiDung(nguoiDung);
        diaChi.setHoTen(request.getHoTen());
        diaChi.setSoDienThoai(request.getSoDienThoai());
        diaChi.setSoNha(request.getDiaChi());
        diaChi.setTinhThanh(request.getTinhThanh());
        diaChi = diaChiRepository.save(diaChi);

        // BUG FIX: validate ALL items stock availability BEFORE deducting any inventory.
        // Previously, stock was decremented inside the loop so if item N failed,
        // items 0..N-1 already had their stock reduced — corrupting inventory.
        List<SanPham> sanPhamList = new ArrayList<>();
        for (CartItemRequest item : request.getItems()) {
            SanPham sanPham = sanPhamRepository.findById(item.getMaSanPham())
                    .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại: " + item.getMaSanPham()));
            if (sanPham.getSoLuongTon() < item.getSoLuong()) {
                throw new RuntimeException("Sản phẩm " + sanPham.getTenSanPham() + " không đủ hàng");
            }
            sanPhamList.add(sanPham);
        }

        // All items validated — now calculate total and deduct stock
        BigDecimal tongTien = BigDecimal.ZERO;
        List<ChiTietDonHang> chiTietList = new ArrayList<>();
        for (int i = 0; i < request.getItems().size(); i++) {
            CartItemRequest item = request.getItems().get(i);
            SanPham sanPham = sanPhamList.get(i);
            BigDecimal thanhTien = sanPham.getGia().multiply(BigDecimal.valueOf(item.getSoLuong()));
            tongTien = tongTien.add(thanhTien);
            ChiTietDonHang ct = new ChiTietDonHang();
            ct.setSanPham(sanPham);
            ct.setSoLuong(item.getSoLuong());
            ct.setDonGia(sanPham.getGia());
            chiTietList.add(ct);
            sanPham.setSoLuongTon(sanPham.getSoLuongTon() - item.getSoLuong());
            sanPhamRepository.save(sanPham);
        }

        DonHang donHang = new DonHang();
        donHang.setNguoiDung(nguoiDung);
        donHang.setDiaChi(diaChi);
        donHang.setTongTien(tongTien);
        donHang.setPhuongThucTT(request.getPhuongThucTT());
        donHang.setGhiChu(request.getGhiChu());
        donHang = donHangRepository.save(donHang);

        for (ChiTietDonHang ct : chiTietList) {
            ct.setDonHang(donHang);
            chiTietDonHangRepository.save(ct);
        }
        return donHang;
    }

    public TraCuuDonHangResponse traCuuDonHang(String maDonHangStr, String soDienThoai) {
        UUID maDonHang = UUID.fromString(maDonHangStr);
        DonHang donHang = donHangRepository.findByMaDonHangAndNguoiDung_SoDienThoai(maDonHang, soDienThoai)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn hàng"));
        List<ChiTietDonHang> chiTietList = chiTietDonHangRepository.findByDonHang_MaDonHang(maDonHang);

        TraCuuDonHangResponse response = new TraCuuDonHangResponse();
        TraCuuDonHangResponse.DonHangInfo donHangInfo = new TraCuuDonHangResponse.DonHangInfo();
        donHangInfo.setMaDonHang(donHang.getMaDonHang().toString());
        donHangInfo.setHoTenNguoiNhan(donHang.getDiaChi().getHoTen());
        donHangInfo.setSoDienThoaiNhan(donHang.getDiaChi().getSoDienThoai());
        donHangInfo.setDiaChi(donHang.getDiaChi().getSoNha() + ", " + donHang.getDiaChi().getTinhThanh());
        donHangInfo.setTongTien(donHang.getTongTien());
        donHangInfo.setTrangThai(donHang.getTrangThai());
        donHangInfo.setTrangThaiTT(donHang.getTrangThaiTT());
        donHangInfo.setNgayDat(donHang.getNgayDat());
        response.setDonHang(donHangInfo);

        List<TraCuuDonHangResponse.ChiTietDonHangInfo> chiTietInfoList = new ArrayList<>();
        for (ChiTietDonHang ct : chiTietList) {
            TraCuuDonHangResponse.ChiTietDonHangInfo info = new TraCuuDonHangResponse.ChiTietDonHangInfo();
            info.setTenSanPham(ct.getSanPham().getTenSanPham());
            info.setSoLuong(ct.getSoLuong());
            info.setDonGia(ct.getDonGia());
            info.setThanhTien(ct.getDonGia().multiply(BigDecimal.valueOf(ct.getSoLuong())));
            chiTietInfoList.add(info);
        }
        response.setChiTiet(chiTietInfoList);
        return response;
    }
}
