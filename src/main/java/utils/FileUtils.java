package utils;

import java.io.File;

public class FileUtils {

    private static final String DOWNLOAD_DIRECTORY =
            System.getProperty("user.home")
                    + File.separator
                    + "Downloads";

    public static File waitForInvoiceDownload(
            String fileName,
            int timeoutInSeconds) {

        File downloadDirectory =
                new File(DOWNLOAD_DIRECTORY);

        long endTime =
                System.currentTimeMillis()
                        + (timeoutInSeconds * 1000L);

        while (System.currentTimeMillis() < endTime) {

            File[] files =
                    downloadDirectory.listFiles();

            if (files != null) {

                for (File file : files) {

                    if (file.getName()
                            .toLowerCase()
                            .contains(fileName.toLowerCase())
                            && !file.getName()
                            .endsWith(".crdownload")) {

                        return file;
                    }
                }
            }

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }

        return null;
    }
}