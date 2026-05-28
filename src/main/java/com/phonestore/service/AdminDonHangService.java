package com.phonestore.service;

import com.phonestore.dto.admin.OrderResponse;
import com.phonestore.dto.common.PageResponse;
import com.phonestore.entity.ChiTietDonHang;
import com.phonestore.entity.DonHang;
import com.phonestore.exception.BadRequestException;
import com.phonestore.exception.ResourceNotFoundException;
import com.phonestore.mapper.DonHangMapper;
import com.phonestore.repository.ChiTietDonHangRepository;
import com.phonestore.repository.DonHangRepository;
import com.phonestore.util.TrangThaiDonHang;
import com.phonestore.util.TrangThaiThanhToan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AdminDonHangService {

    private final DonHangRepository donHangRepository;
    private final ChiTietDonHangRepository chiTietRepo;
    private final DonHangMapper mapper;

    public AdminDonHangService(DonHangRepository donHangRepository,
                               ChiTietDonHangRepository chiTietRepo,
                               DonHangMapper mapper) {
        this.donHangRepository = donHangRepository;
        this.chiTietRepo = chiTietRepo;
        this.mapper = mapper;
    }

    public PageResponse<OrderResponse> list(String keyword, String trangThai, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("ngayDat").descending());
        Page<DonHang> result = donHangRepository.adminSearch(keyword, trangThai, pageable);
        List<OrderResponse> mapped = result.getContent().stream()
                .map(dh -> mapper.toResponse(dh, null))
                .collect(Collectors.toList());
        return PageResponse.of(result, mapped);
    }

    public OrderResponse detail(UUID id) {
        DonHang dh = donHangRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn hàng"));
        List<ChiTietDonHang> items = chiTietRepo.findByDonHang_MaDonHang(id);
        return mapper.toResponse(dh, items);
    }

    @Transactional
    public OrderResponse confirm(UUID id) {
        return transition(id, TrangThaiDonHang.DANG_GIAO);
    }

    @Transactional
    public OrderResponse shipping(UUID id) {
        return transition(id, TrangThaiDonHang.DANG_GIAO);
    }

    @Transactional
    public OrderResponse delivered(UUID id) {
        DonHang dh = transitionEntity(id, TrangThaiDonHang.DA_GIAO);
        // tự động đánh dấu thanh toán nếu là COD
        if ("cod".equalsIgnoreCase(dh.getPhuongThucTT())) {
            dh.setTrangThaiTT(TrangThaiThanhToan.DA_THANH_TOAN);
            donHangRepository.save(dh);
        }
        return detail(id);
    }

    @Transactional
    public OrderResponse cancel(UUID id) {
        return transition(id, TrangThaiDonHang.DA_HUY);
    }

    @Transactional
    public OrderResponse updatePaymentStatus(UUID id, String trangThaiTT) {
        if (!TrangThaiThanhToan.isValid(trangThaiTT)) {
            throw new BadRequestException("Trạng thái thanh toán không hợp lệ");
        }
        DonHang dh = donHangRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn hàng"));
        dh.setTrangThaiTT(trangThaiTT);
        donHangRepository.save(dh);
        return detail(id);
    }

    private OrderResponse transition(UUID id, String to) {
        transitionEntity(id, to);
        return detail(id);
    }

    private DonHang transitionEntity(UUID id, String to) {
        DonHang dh = donHangRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy đơn hàng"));
        if (!TrangThaiDonHang.canTransition(dh.getTrangThai(), to)) {
            throw new BadRequestException("Không thể chuyển trạng thái từ '" + dh.getTrangThai() + "' sang '" + to + "'");
        }
        dh.setTrangThai(to);
        return donHangRepository.save(dh);
    }
}
