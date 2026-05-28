package com.phonestore.service;

import com.phonestore.dto.admin.OrderResponse;
import com.phonestore.dto.common.PageResponse;
import com.phonestore.dto.user.CheckoutRequest;
import com.phonestore.entity.*;
import com.phonestore.exception.BadRequestException;
import com.phonestore.exception.ForbiddenException;
import com.phonestore.exception.ResourceNotFoundException;
import com.phonestore.mapper.DonHangMapper;
import com.phonestore.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserDonHangService {

    private final GioHangRepository gioHangRepository;
    private final SanPhamRepository sanPhamRepository;
    private final DonHangRepository donHangRepository;
    private final ChiTietDonHangRepository chiTietRepo;
    private final DiaChiRepository diaChiRepository;
    private final DonHangMapper mapper;

    public UserDonHangService(GioHangRepository gioHangRepository,
                              SanPhamRepository sanPhamRepository,
                              DonHangRepository donHangRepository,
                              ChiTietDonHangRepository chiTietRepo,
                              DiaChiRepository diaChiRepository,
                              DonHangMapper mapper) {
        this.gioHangRepository = gioHangRepository;
        this.sanPhamRepository = sanPhamRepository;
        this.donHangRepository = donHangRepository;
        this.chiTietRepo = chiTietRepo;
        this.diaChiRepository = diaChiRepository;
        this.mapper = mapper;
    }

    @Transactional
    public OrderResponse checkout(NguoiDung me, CheckoutRequest req) {
        List<GioHang> cartItems = gioHangRepository.findByNguoiDung(me);
        if (cartItems.isEmpty()) throw new BadRequestException("Giỏ hàng trống");

        // Validate tồn kho TRƯỚC khi trừ kho
        for (GioHang gh : cartItems) {
            SanPham sp = gh.getSanPham();
            if (sp.getSoLuongTon() < gh.getSoLuong()) {
                throw new BadRequestException("Sản phẩm '" + sp.getTenSanPham() + "' không đủ tồn kho");
            }
        }

        // Tạo địa chỉ giao hàng
        DiaChi diaChi = new DiaChi();
        diaChi.setNguoiDung(me);
        diaChi.setHoTen(req.getHoTen());
        diaChi.setSoDienThoai(req.getSoDienThoai());
        diaChi.setSoNha(req.getDiaChi());
        diaChi.setPhuongXa(req.getPhuongXa());
        diaChi.setQuanHuyen(req.getQuanHuyen());
        diaChi.setTinhThanh(req.getTinhThanh());
        diaChi = diaChiRepository.save(diaChi);

        BigDecimal tong = BigDecimal.ZERO;
        List<ChiTietDonHang> chiTiet = new ArrayList<>();
        for (GioHang gh : cartItems) {
            SanPham sp = gh.getSanPham();
            BigDecimal lineTotal = sp.getGia().multiply(BigDecimal.valueOf(gh.getSoLuong()));
            tong = tong.add(lineTotal);
            ChiTietDonHang ct = new ChiTietDonHang();
            ct.setSanPham(sp);
            ct.setSoLuong(gh.getSoLuong());
            ct.setDonGia(sp.getGia());
            chiTiet.add(ct);
            sp.setSoLuongTon(sp.getSoLuongTon() - gh.getSoLuong());
            sanPhamRepository.save(sp);
        }

        DonHang dh = new DonHang();
        dh.setNguoiDung(me);
        dh.setDiaChi(diaChi);
        dh.setTongTien(tong);
        dh.setPhuongThucTT(req.getPhuongThucTT());
        dh.setGhiChu(req.getGhiChu());
        dh = donHangRepository.save(dh);

        for (ChiTietDonHang ct : chiTiet) {
            ct.setDonHang(dh);
            chiTietRepo.save(ct);
        }

        // clear cart
        gioHangRepository.deleteAllByNguoiDung(me.getMaNguoiDung());

        return mapper.toResponse(dh, chiTiet);
    }

    public PageResponse<OrderResponse> myOrders(NguoiDung me, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DonHang> result = donHangRepository.findByNguoiDung_MaNguoiDungOrderByNgayDatDesc(
                me.getMaNguoiDung(), pageable);
        List<OrderResponse> mapped = result.getContent().stream()
                .map(dh -> mapper.toResponse(dh, null))
                .collect(Collectors.toList());
        return PageResponse.of(result, mapped);
    }

    public OrderResponse myOrderDetail(NguoiDung me, UUID maDonHang) {
        DonHang dh = donHangRepository.findById(maDonHang)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn hàng"));
        if (!dh.getNguoiDung().getMaNguoiDung().equals(me.getMaNguoiDung())) {
            throw new ForbiddenException("Đơn hàng không thuộc về bạn");
        }
        return mapper.toResponse(dh, chiTietRepo.findByDonHang_MaDonHang(maDonHang));
    }
}
