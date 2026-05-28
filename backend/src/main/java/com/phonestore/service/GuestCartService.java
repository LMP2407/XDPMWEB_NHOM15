package com.phonestore.service;

import com.phonestore.dto.CartItemRequest;
import com.phonestore.dto.GioHangGuestResponse;
import com.phonestore.entity.SanPham;
import com.phonestore.repository.SanPhamRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class GuestCartService {
    private static final String CART_SESSION_KEY = "guestCart";
    @Autowired private SanPhamRepository sanPhamRepository;

    @SuppressWarnings("unchecked")
    private List<CartItemRequest> getCartFromSession(HttpSession session) {
        List<CartItemRequest> cart = (List<CartItemRequest>) session.getAttribute(CART_SESSION_KEY);
        if (cart == null) {
            cart = new ArrayList<>();
            session.setAttribute(CART_SESSION_KEY, cart);
        }
        return cart;
    }

    public GioHangGuestResponse getCart(HttpSession session) {
        List<CartItemRequest> cart = getCartFromSession(session);
        GioHangGuestResponse response = new GioHangGuestResponse();
        List<GioHangGuestResponse.GuestCartItem> items = new ArrayList<>();
        BigDecimal tongTien = BigDecimal.ZERO;
        for (CartItemRequest item : cart) {
            Optional<SanPham> spOpt = sanPhamRepository.findById(item.getMaSanPham());
            if (spOpt.isPresent()) {
                SanPham sp = spOpt.get();
                GioHangGuestResponse.GuestCartItem guestItem = new GioHangGuestResponse.GuestCartItem();
                guestItem.setMaSanPham(sp.getMaSanPham().toString());
                guestItem.setTenSanPham(sp.getTenSanPham());
                guestItem.setGia(sp.getGia());
                guestItem.setSoLuong(item.getSoLuong());
                BigDecimal thanhTien = sp.getGia().multiply(BigDecimal.valueOf(item.getSoLuong()));
                guestItem.setThanhTien(thanhTien);
                items.add(guestItem);
                tongTien = tongTien.add(thanhTien);
            }
        }
        response.setItems(items);
        response.setTongTien(tongTien);
        return response;
    }

    public void addToCart(HttpSession session, CartItemRequest request) {
        List<CartItemRequest> cart = getCartFromSession(session);
        boolean found = false;
        for (CartItemRequest item : cart) {
            if (item.getMaSanPham().equals(request.getMaSanPham())) {
                item.setSoLuong(item.getSoLuong() + request.getSoLuong());
                found = true;
                break;
            }
        }
        if (!found) {
            cart.add(request);
        }
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public void updateQuantity(HttpSession session, UUID maSanPham, int soLuong) {
        List<CartItemRequest> cart = getCartFromSession(session);
        cart.removeIf(item -> item.getMaSanPham().equals(maSanPham));
        if (soLuong > 0) {
            CartItemRequest newItem = new CartItemRequest();
            newItem.setMaSanPham(maSanPham);
            newItem.setSoLuong(soLuong);
            cart.add(newItem);
        }
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public void removeFromCart(HttpSession session, UUID maSanPham) {
        List<CartItemRequest> cart = getCartFromSession(session);
        cart.removeIf(item -> item.getMaSanPham().equals(maSanPham));
        session.setAttribute(CART_SESSION_KEY, cart);
    }

    public List<CartItemRequest> getGuestCartList(HttpSession session) {
        return new ArrayList<>(getCartFromSession(session));
    }

    public void clearCart(HttpSession session) {
        session.removeAttribute(CART_SESSION_KEY);
    }
}