package com.phonestore.controller;

import com.phonestore.config.JwtUtil;
import com.phonestore.dto.*;
import com.phonestore.dto.auth.LoginRequest;
import com.phonestore.dto.auth.LoginResponse;
import com.phonestore.dto.auth.RegisterRequest;
import com.phonestore.dto.auth.UserInfoResponse;
import com.phonestore.dto.common.ApiResponse;
import com.phonestore.entity.NguoiDung;
import com.phonestore.repository.NguoiDungRepository;
import com.phonestore.security.SecurityUtil;
import com.phonestore.service.AuthService;
import com.phonestore.service.GuestCartService;
import com.phonestore.service.GioHangService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired private NguoiDungRepository nguoiDungRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private GuestCartService guestCartService;
    @Autowired private GioHangService gioHangService;
    @Autowired private AuthService authService;
    @Autowired private SecurityUtil securityUtil;

    // ========== Endpoint mới (theo chuẩn ApiResponse + role) ==========

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<UserInfoResponse>> register(@Valid @RequestBody RegisterRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Đăng ký thành công", authService.register(request)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<LoginResponse>> login(@Valid @RequestBody LoginRequest request,
                                                            HttpSession session) {
        return ResponseEntity.ok(ApiResponse.success("Đăng nhập thành công", authService.login(request, session)));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<UserInfoResponse>> me() {
        NguoiDung nd = securityUtil.requireCurrentUser();
        return ResponseEntity.ok(ApiResponse.success(authService.toUserInfo(nd)));
    }

    // ========== Endpoint cũ (giữ nguyên) ==========

    @PostMapping("/dang-ky")
    public ResponseEntity<?> dangKy(@Valid @RequestBody DangKyRequest request) {
        if (nguoiDungRepository.findByEmail(request.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body(Map.of("loi", "Email đã tồn tại", "ma_loi", 400));
        }
        NguoiDung nd = new NguoiDung();
        nd.setHoTen(request.getHoTen());
        nd.setEmail(request.getEmail());
        nd.setSoDienThoai(request.getSoDienThoai());
        nd.setMatKhau(passwordEncoder.encode(request.getMatKhau()));
        nd = nguoiDungRepository.save(nd);
        DangKyResponse response = new DangKyResponse("Đăng ký thành công", nd.getMaNguoiDung().toString());
        return ResponseEntity.ok(response);
    }

    @PostMapping("/dang-nhap")
    public ResponseEntity<?> dangNhap(@Valid @RequestBody DangNhapRequest request, HttpSession session) {
        try {
            Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getMatKhau())
            );
            UserDetails userDetails = (UserDetails) auth.getPrincipal();
            String token = jwtUtil.generateToken(userDetails);
            NguoiDung nguoiDung = nguoiDungRepository.findByEmail(request.getEmail()).get();

            // Merge guest cart (nếu có)
            var guestCart = guestCartService.getGuestCartList(session);
            if (!guestCart.isEmpty()) {
                gioHangService.mergeGuestCart(nguoiDung.getMaNguoiDung(), guestCart);
                guestCartService.clearCart(session);
            }

            DangNhapResponse response = new DangNhapResponse(token, nguoiDung.getHoTen(), nguoiDung.getEmail());
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(401).body(Map.of("loi", "Sai email hoặc mật khẩu", "ma_loi", 401));
        }
    }
}