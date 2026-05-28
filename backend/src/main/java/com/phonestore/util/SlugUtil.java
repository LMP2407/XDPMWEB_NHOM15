package com.phonestore.util;

import java.text.Normalizer;
import java.util.Locale;
import java.util.regex.Pattern;

public final class SlugUtil {
    private static final Pattern NON_LATIN = Pattern.compile("[^\\w-]");
    private static final Pattern WHITESPACE = Pattern.compile("[\\s]+");
    private static final Pattern DASH_REPEAT = Pattern.compile("-+");

    private SlugUtil() {}

    public static String slugify(String input) {
        if (input == null) return "";
        String s = input.trim().toLowerCase(Locale.ROOT);
        s = s.replace('đ', 'd').replace('Đ', 'd');
        String nowhitespace = WHITESPACE.matcher(s).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD)
                .replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        String slug = NON_LATIN.matcher(normalized).replaceAll("");
        slug = DASH_REPEAT.matcher(slug).replaceAll("-");
        return slug.replaceAll("^-|-$", "");
    }
}
