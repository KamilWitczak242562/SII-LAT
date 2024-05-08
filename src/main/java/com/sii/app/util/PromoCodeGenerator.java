package com.sii.app.util;

import java.security.SecureRandom;

public class PromoCodeGenerator {
    private static final String CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generate() {
        StringBuilder codeBuilder = new StringBuilder();
        SecureRandom random = new SecureRandom();

        int length = random.nextInt(22) + 3;

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            codeBuilder.append(CHARACTERS.charAt(index));
        }

        return codeBuilder.toString();
    }
}
