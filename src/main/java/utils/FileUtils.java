package utils;

import java.io.File;
import java.time.Duration;

public final class FileUtils {

    private static final String DOWNLOAD_DIRECTORY =
            System.getProperty("user.home")
                    + File.separator
                    + "Downloads";

    private FileUtils() {
        // Prevent instantiation
    }

    public static File waitForFileDownload(
            String fileName,
            int timeoutInSeconds) {

        return waitForFileDownload(
                DOWNLOAD_DIRECTORY,
                fileName,
                timeoutInSeconds
        );
    }

    public static File waitForFileDownload(
            String directoryPath,
            String fileName,
            int timeoutInSeconds) {

        File downloadDirectory = new File(directoryPath);

        if (!downloadDirectory.exists()) {
            throw new IllegalArgumentException(
                    "Download directory does not exist: "
                            + directoryPath
            );
        }

        long timeoutMillis =
                Duration.ofSeconds(timeoutInSeconds).toMillis();

        long endTime =
                System.currentTimeMillis() + timeoutMillis;

        while (System.currentTimeMillis() < endTime) {

            File[] files = downloadDirectory.listFiles();

            if (files != null) {

                for (File file : files) {

                    if (isMatchingDownloadedFile(file, fileName)) {
                        return file;
                    }
                }
            }

            sleep(500);
        }

        throw new RuntimeException(
                "File was not downloaded within "
                        + timeoutInSeconds
                        + " seconds: "
                        + fileName
        );
    }

    private static boolean isMatchingDownloadedFile(
            File file,
            String fileName) {

        if (!file.isFile()) {
            return false;
        }

        String actualFileName =
                file.getName().toLowerCase();

        String expectedFileName =
                fileName.toLowerCase();

        return actualFileName.contains(expectedFileName)
                && !actualFileName.endsWith(".crdownload");
    }

    private static void sleep(long milliseconds) {

        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

            throw new RuntimeException(
                    "File download wait was interrupted.",
                    e
            );
        }
    }
}