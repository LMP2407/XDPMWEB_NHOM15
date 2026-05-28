package com.phonestore.util;

public enum VaiTro {
    USER, ADMIN;

    public String authority() { return "ROLE_" + name(); }

    public static VaiTro from(String s) {
        if (s == null) return USER;
        try { return VaiTro.valueOf(s.toUpperCase()); }
        catch (IllegalArgumentException e) { return USER; }
    }
}
