package utils;

import java.util.UUID;

public class TestData {

    // Registration data
    public static final String NAME = "Beshoy Nader";
    public static final String PASSWORD = "Test@12345";

    public static final String FIRST_NAME = "Beshoy";
    public static final String LAST_NAME = "Nader";
    public static final String COMPANY = "Test Company";
    public static final String ADDRESS = "123 Test Street";
    public static final String ADDRESS_2 = "Apartment 10";
    public static final String COUNTRY = "Canada";
    public static final String STATE = "Cairo";
    public static final String CITY = "Cairo";
    public static final String ZIPCODE = "12345";
    public static final String MOBILE = "01012345678";
    public static final String EXISTING_EMAIL = "your-existing-account@email.com";
    public static final String VALID_EMAIL = "your-existing-account@email.com";

    // Generate a unique email for every registration execution
    public static String generateUniqueEmail() {
        return "beshoy" + UUID.randomUUID()
                .toString()
                .substring(0, 8)
                + "@example.com";
    }

    // Product
    public static final String PRODUCT_NAME = "Blue Top";

    // Payment
    public static final String CARD_NAME = "Beshoy Nader";
    public static final String CARD_NUMBER = "4111111111111111";
    public static final String CVC = "123";
    public static final String EXPIRATION_MONTH = "12";
    public static final String EXPIRATION_YEAR = "2030";
}