package utils;

import java.util.UUID;

public final class TestDataGenerator {

    private TestDataGenerator() {
        // Prevent instantiation
    }

    public static String generateUniqueEmail() {
        return "beshoy"
                + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                + "@example.com";
    }
}