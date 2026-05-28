package com.phonestore.security;

import com.phonestore.entity.NguoiDung;
import com.phonestore.exception.UnauthorizedException;
import com.phonestore.repository.NguoiDungRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * Helper truy xuất user hiện tại từ SecurityContext.
 */
@Component
public class SecurityUtil {

    @Autowired private NguoiDungRepository nguoiDungRepository;

    public Optional<CustomUserDetails> currentUserDetails() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated()) return Optional.empty();
        Object principal = auth.getPrincipal();
        if (principal instanceof CustomUserDetails cud) return Optional.of(cud);
        return Optional.empty();
    }

    public NguoiDung requireCurrentUser() {
        CustomUserDetails details = currentUserDetails()
                .orElseThrow(() -> new UnauthorizedException("Vui lòng đăng nhập"));
        // load lại để chắc chắn entity managed (nếu dùng trong service @Transactional)
        return nguoiDungRepository.findById(details.getMaNguoiDung())
                .orElseThrow(() -> new UnauthorizedException("Người dùng không tồn tại"));
    }

    public boolean isAdmin() {
        return currentUserDetails().map(d -> "ADMIN".equalsIgnoreCase(d.getVaiTro())).orElse(false);
    }
}
