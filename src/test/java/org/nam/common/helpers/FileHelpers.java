package org.nam.common.helpers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public final class FileHelpers {

    public FileHelpers() {
        super();
    }

    public static void copyFile(String source_FilePath, String target_FilePath) {
        try {
            Files.copy(Paths.get(source_FilePath), Paths.get(target_FilePath), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return Get the path to your source directory with a / at the end
     */
    public static String getCurrentDir() {
        String current = System.getProperty("user.dir") + File.separator;
        return current;
    }
}