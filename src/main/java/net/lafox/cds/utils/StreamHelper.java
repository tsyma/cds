package net.lafox.cds.utils;

import static org.apache.commons.lang3.StringUtils.isBlank;

import java.util.regex.Pattern;
import java.util.stream.Stream;

public class StreamHelper {
    public static final Pattern LIST_SEPARATOR = Pattern.compile("\\s*[,;|\\s]\\s*");

    public static Stream<String> splitAsStream(String list) {
        if (isBlank(list)) {
            return Stream.empty();
        }
        return LIST_SEPARATOR.splitAsStream(list);
    }
}
