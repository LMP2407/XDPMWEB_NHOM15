package com.phonestore.util;

import java.util.Set;

public final class TrangThaiDonHang {
    public static final String CHO_XAC_NHAN = "cho_xac_nhan";
    public static final String DANG_GIAO    = "dang_giao";
    public static final String DA_GIAO      = "da_giao";
    public static final String DA_HUY       = "da_huy";

    public static final Set<String> ALL = Set.of(CHO_XAC_NHAN, DANG_GIAO, DA_GIAO, DA_HUY);

    private TrangThaiDonHang() {}

    public static boolean isValid(String s) { return s != null && ALL.contains(s); }

    /** Kiểm tra chuyển trạng thái có hợp lệ không */
    public static boolean canTransition(String from, String to) {
        if (from == null || to == null) return false;
        if (DA_GIAO.equals(from) || DA_HUY.equals(from)) return false; // final
        return switch (from) {
            case CHO_XAC_NHAN -> DANG_GIAO.equals(to) || DA_HUY.equals(to);
            case DANG_GIAO    -> DA_GIAO.equals(to)   || DA_HUY.equals(to);
            default -> false;
        };
    }
}
