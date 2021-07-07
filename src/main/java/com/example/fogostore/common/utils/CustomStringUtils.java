package com.example.fogostore.common.utils;

import org.springframework.util.StringUtils;

import java.text.Normalizer;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.UUID;
import java.util.regex.Pattern;

public class CustomStringUtils {
    public static String getPriceFormatted(Double price) {
        if (price == null) return "";
        Locale locale = new Locale("vi", "VN");
        NumberFormat formatCurrency = NumberFormat.getCurrencyInstance(locale);
        return formatCurrency.format(price);
    }

    public static String genSlug(String val) {
        if (StringUtils.isEmpty(val)) return "";

        val = val.replaceAll("đ", "d");
        val = val.replaceAll("Đ", "d");

        Pattern NONLATIN = Pattern.compile("[^\\w-]");
        Pattern WHITESPACE = Pattern.compile("[\\s]");
        Pattern EDGESDHASHES = Pattern.compile("(^-|-$)");

        String nowhitespace = WHITESPACE.matcher(val).replaceAll("-");
        String normalized = Normalizer.normalize(nowhitespace, Normalizer.Form.NFD);
        String slug = NONLATIN.matcher(normalized).replaceAll("");
        slug = EDGESDHASHES.matcher(slug).replaceAll("");
        slug = slug.replaceAll("-{2,}", "-");
        return slug.toLowerCase(Locale.ENGLISH);
    }

    public static boolean isValidEmail(String email) {
        if (StringUtils.isEmpty(email)) return false;
        String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
        return email.matches(regex);
    }

    public static boolean isValidPhone(String phone) {
        if (StringUtils.isEmpty(phone)) return false;
        String regex = "^\\d+$";
        return phone.matches(regex);
    }

    public static String genUniqueId() {
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

}
