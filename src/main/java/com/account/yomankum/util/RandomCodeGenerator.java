package com.account.yomankum.util;

import java.util.Random;

public class RandomCodeGenerator {

    private RandomCodeGenerator(){}

    private static final String CODE_CHAR = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";

    public static String generateFiveDigitsCode() {
        Random random = new Random();
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 5; i++) {
            int randomIndex = random.nextInt(CODE_CHAR.length());
            builder.append(CODE_CHAR.charAt(randomIndex));
        }
        return builder.toString();
    }
}
