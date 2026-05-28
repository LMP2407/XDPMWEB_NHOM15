package com.phonestore.service;

import com.phonestore.dto.admin.DashboardResponse;
import com.phonestore.dto.admin.TopProductResponse;
import com.phonestore.repository.DonHangRepository;
import com.phonestore.repository.NguoiDungRepository;
import com.phonestore.repository.SanPhamRepository;
import com.phonestore.util.TrangThaiDonHang;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.YearMonth;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DashboardService {

    private final DonHangRepository donHangRepository;
    private final NguoiDungRepository nguoiDungRepository;
    private final SanPhamRepository sanPhamRepository;

    public DashboardService(DonHangRepository donHangRepository,
                            NguoiDungRepository nguoiDungRepository,
                            SanPhamRepository sanPhamRepository) {
        this.donHangRepository = donHangRepository;
        this.nguoiDungRepository = nguoiDungRepository;
        this.sanPhamRepository = sanPhamRepository;
    }

    public DashboardResponse getDashboard(int topLimit) {
        DashboardResponse r = new DashboardResponse();

        r.setTongDoanhThu(nullToZero(donHangRepository.totalRevenueAllTime()));

        YearMonth ym = YearMonth.now();
        LocalDateTime from = ym.atDay(1).atStartOfDay();
        LocalDateTime to   = ym.plusMonths(1).atDay(1).atStartOfDay();
        r.setDoanhThuThangNay(nullToZero(donHangRepository.totalRevenueBetween(from, to)));

        r.setTongNguoiDung(nguoiDungRepository.count());
        r.setTongDonHang(donHangRepository.count());
        r.setTongSanPham(sanPhamRepository.countByHienThiTrue());

        r.setDonChoXacNhan(donHangRepository.countByTrangThai(TrangThaiDonHang.CHO_XAC_NHAN));
        r.setDonDangGiao(donHangRepository.countByTrangThai(TrangThaiDonHang.DANG_GIAO));
        r.setDonDaGiao(donHangRepository.countByTrangThai(TrangThaiDonHang.DA_GIAO));
        r.setDonDaHuy(donHangRepository.countByTrangThai(TrangThaiDonHang.DA_HUY));

        List<Object[]> rows = donHangRepository.topSellingProducts(PageRequest.of(0, topLimit));
        r.setTopSanPham(rows.stream().map(this::toTop).collect(Collectors.toList()));

        return r;
    }

    private TopProductResponse toTop(Object[] row) {
        UUID id = (UUID) row[0];
        String ten = (String) row[1];
        String hinh = (String) row[2];
        Long soLuong = ((Number) row[3]).longValue();
        BigDecimal doanhThu = (BigDecimal) row[4];
        return new TopProductResponse(id, ten, hinh, soLuong, doanhThu);
    }

    private BigDecimal nullToZero(BigDecimal v) { return v == null ? BigDecimal.ZERO : v; }
}
