package com.electronics.pgdata.auth.helper;

import java.util.Random;

public class VerificationCodeGenerator {

    public static String generateVerificationCode() {
        Random random = new Random();
        int code = 10_000 + random.nextInt(900_000);
        return String.valueOf(code);
    }
}
