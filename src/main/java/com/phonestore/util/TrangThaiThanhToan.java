package com.phonestore.util;

import java.util.Set;

public final class TrangThaiThanhToan {
    public static final String CHUA_THANH_TOAN = "chua_thanh_toan";
    public static final String DA_THANH_TOAN   = "da_thanh_toan";
    public static final String HOAN_TIEN       = "hoan_tien";

    public static final Set<String> ALL = Set.of(CHUA_THANH_TOAN, DA_THANH_TOAN, HOAN_TIEN);

    private TrangThaiThanhToan() {}
    public static boolean isValid(String s) { return s != null && ALL.contains(s); }
}
