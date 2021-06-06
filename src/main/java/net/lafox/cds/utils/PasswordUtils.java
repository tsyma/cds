package net.lafox.cds.utils;

import static org.apache.commons.lang3.RandomStringUtils.random;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

import net.lafox.cds.Config;
import org.apache.commons.lang3.RandomStringUtils;

public class PasswordUtils {

    public static String randomAlphanumeric() {
        return RandomStringUtils.random(Config.ID_LENGTH, true, true);

    }
}
