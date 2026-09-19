package data;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.InputStream;

public final class TestDataLoader {

    private static final String TEST_DATA_FILE =
            "data/test-data.json";

    private static final ObjectMapper OBJECT_MAPPER =
            new ObjectMapper();

    private static final TestData TEST_DATA = loadTestData();

    private TestDataLoader() {
        // Prevent instantiation
    }

    public static TestData getTestData() {

        return TEST_DATA;
    }

    private static TestData loadTestData() {

        try (InputStream inputStream =
                     TestDataLoader.class
                             .getClassLoader()
                             .getResourceAsStream(TEST_DATA_FILE)) {

            if (inputStream == null) {
                throw new IllegalStateException(
                        "Test data file not found: "
                                + TEST_DATA_FILE
                );
            }

            return OBJECT_MAPPER.readValue(
                    inputStream,
                    TestData.class
            );

        } catch (IOException e) {
            throw new IllegalStateException(
                    "Failed to load test data: "
                            + TEST_DATA_FILE,
                    e
            );
        }
    }
}