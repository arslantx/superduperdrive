package com.udacity.jwdnd.course1.cloudstorage.util;

import java.security.SecureRandom;
import java.util.Base64;

public class EncoderUtil {

    private EncoderUtil() {}
    
    public static String getRandomEncodedStr() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);
        return Base64.getEncoder().encodeToString(key);
    }
}
