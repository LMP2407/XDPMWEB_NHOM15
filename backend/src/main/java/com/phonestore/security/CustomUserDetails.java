package com.phonestore.security;

import com.phonestore.entity.NguoiDung;
import com.phonestore.util.VaiTro;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

/**
 * UserDetails wrapper bọc {@link NguoiDung} để giữ nguyên reference entity
 * + cấp authority dạng "ROLE_USER" / "ROLE_ADMIN".
 */
public class CustomUserDetails implements UserDetails {

    private final NguoiDung nguoiDung;

    public CustomUserDetails(NguoiDung nguoiDung) { this.nguoiDung = nguoiDung; }

    public NguoiDung getNguoiDung() { return nguoiDung; }
    public UUID getMaNguoiDung() { return nguoiDung.getMaNguoiDung(); }
    public String getVaiTro() { return nguoiDung.getVaiTro(); }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(VaiTro.from(nguoiDung.getVaiTro()).authority()));
    }

    @Override public String getPassword() { return nguoiDung.getMatKhau(); }
    @Override public String getUsername() { return nguoiDung.getEmail(); }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return Boolean.TRUE.equals(nguoiDung.getHoatDong()); }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return Boolean.TRUE.equals(nguoiDung.getHoatDong()); }
}
