package com.bulade.donor.common.annotation;

import org.apache.commons.lang3.StringUtils;
import java.util.function.Function;

public enum SensitiveType {

    CHINESE_NAME(s -> s.charAt(0) + "*".repeat(s.length() - 1)),

    CHINESE_NAME_ENCRYPTION(SensitiveType::maskName),

    ID_CARD(SensitiveType::maskIdNumber),

    MOBILE_PHONE(s -> s.replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2")),

    ADDRESS(s -> s.replaceAll("(\\S{8})\\S{4}(\\S*)\\S{4}", "$1****$2****")),

    NO(s -> s);

    private final Function<String, String> desensitizer;

    SensitiveType(Function<String, String> desensitizer) {
        this.desensitizer = desensitizer;
    }

    public String desensitize(String source) {
        if (StringUtils.isBlank(source)) {
            return source;
        }
        return this.desensitizer.apply(source);
    }

    public static String maskIdNumber(String idNumber) {
        if (idNumber == null || idNumber.isEmpty()) {
            return idNumber;
        }

        int length = idNumber.length();
        if (length <= 4) {
            return idNumber;
        }

        if (idNumber.length() == 18) {
            var regex = "(\\S{6})\\S*(\\S{2})";
            var replacement = "$1" + "*".repeat(Math.max(0, idNumber.length() - 10)) + "$2";
            return idNumber.replaceAll(regex, replacement);
        }

        var regex = "(\\S{2})\\S*(\\S{2})";
        var replacement = "$1" + "*".repeat(Math.max(0, idNumber.length() - 4)) + "$2";

        return idNumber.replaceAll(regex, replacement);
    }

    public static String maskName(String name) {
        if (name.length() > 2) {
            return name.charAt(0) + "*".repeat(name.length() - 2) + name.charAt(name.length() - 1);
        }
        return name.charAt(0) + "*".repeat(name.length() - 1);
    }
}
