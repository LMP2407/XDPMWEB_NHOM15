package com.phonestore.service;

import com.phonestore.config.JwtUtil;
import com.phonestore.dto.auth.LoginRequest;
import com.phonestore.dto.auth.LoginResponse;
import com.phonestore.dto.auth.RegisterRequest;
import com.phonestore.dto.auth.UserInfoResponse;
import com.phonestore.entity.NguoiDung;
import com.phonestore.exception.BadRequestException;
import com.phonestore.exception.UnauthorizedException;
import com.phonestore.repository.NguoiDungRepository;
import com.phonestore.security.CustomUserDetails;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired private NguoiDungRepository nguoiDungRepository;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private GuestCartService guestCartService;
    @Autowired private GioHangService gioHangService;

    public UserInfoResponse register(RegisterRequest request) {
        if (nguoiDungRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new BadRequestException("Email đã tồn tại");
        }
        NguoiDung nd = new NguoiDung();
        nd.setHoTen(request.getHoTen());
        nd.setEmail(request.getEmail());
        nd.setSoDienThoai(request.getSoDienThoai());
        nd.setMatKhau(passwordEncoder.encode(request.getMatKhau()));
        nd.setVaiTro("USER");
        nd.setHoatDong(true);
        nd = nguoiDungRepository.save(nd);
        return toUserInfo(nd);
    }

    public LoginResponse login(LoginRequest request, HttpSession session) {
        Authentication auth;
        try {
            auth = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getMatKhau()));
        } catch (AuthenticationException ex) {
            throw new UnauthorizedException("Sai email hoặc mật khẩu");
        }

        CustomUserDetails details = (CustomUserDetails) auth.getPrincipal();
        NguoiDung nguoiDung = details.getNguoiDung();
        if (!Boolean.TRUE.equals(nguoiDung.getHoatDong())) {
            throw new UnauthorizedException("Tài khoản đã bị khóa");
        }

        String token = jwtUtil.generateToken(details);

        // Merge guest cart (giữ logic giống AuthController cũ)
        if (session != null) {
            var guestCart = guestCartService.getGuestCartList(session);
            if (!guestCart.isEmpty()) {
                gioHangService.mergeGuestCart(nguoiDung.getMaNguoiDung(), guestCart);
                guestCartService.clearCart(session);
            }
        }

        return new LoginResponse(token, nguoiDung.getVaiTro(), toUserInfo(nguoiDung));
    }

    public UserInfoResponse toUserInfo(NguoiDung nd) {
        return new UserInfoResponse(nd.getMaNguoiDung(), nd.getHoTen(), nd.getEmail(),
                nd.getSoDienThoai(), nd.getVaiTro(), nd.getHoatDong());
    }
}
