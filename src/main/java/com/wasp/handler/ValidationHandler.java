package com.wasp.handler;

import java.util.function.Predicate;
import java.util.regex.Pattern;

public class ValidationHandler {
    public static final Predicate<String> SITE_NAME_VALIDATION = siteName -> !siteName.trim().isEmpty();
    public static final Predicate<String> URL_VALIDATION = url -> {
        String urlRegex = "^(https?://)?([a-zA-Z0-9]+([\\-\\.][a-zA-Z0-9]+)*\\.[a-zA-Z]{2,63}(:[0-9]{1,5})?)(/[a-zA-Z0-9\\-\\._\\?\\,\\'/\\\\\\+&amp;%\\$#\\=~]*)?$";
        Pattern pattern = Pattern.compile(urlRegex);
        return pattern.matcher(url).matches();
    };

    public static final Predicate<String> USERNAME_VALIDATION = username -> !username.trim().isEmpty();
    public static final Predicate<String> EMAIL_VALIDATION = email -> {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern pattern = Pattern.compile(emailRegex);
        return pattern.matcher(email).matches();
    };
}
