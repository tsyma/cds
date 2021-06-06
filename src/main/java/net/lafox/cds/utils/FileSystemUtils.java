package net.lafox.cds.utils;

import java.io.File;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FileSystemUtils {
    public static void mkdirIfNotExists(String dirName) {
        File path = new File(dirName);
        if (!path.exists()) {
            if (path.mkdirs()) {
                log.info("Directory '{}' was created.", dirName);
            }
        }
    }
}
