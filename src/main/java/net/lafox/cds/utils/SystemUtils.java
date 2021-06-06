package net.lafox.cds.utils;

import static org.apache.commons.io.FilenameUtils.separatorsToUnix;

public class SystemUtils {
    public static String getUserHome() {
        return separatorsToUnix(System.getProperty("user.home"));
    }
}
