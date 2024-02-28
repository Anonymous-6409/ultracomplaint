package com.ultrasoft.ultracomplaint.utility;

import java.security.SecureRandom;

public class GenerateRandomThing {

    public static String generateRefString(int len) {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            int randomIndex = random.nextInt(chars.length());
            sb.append(chars.charAt(randomIndex));
        }
        return sb.toString();
    }
}
