package net.lafox.cds.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.lafox.cds.Config;
import net.lafox.cds.data.ImageInfo;
import net.lafox.cds.service.exceptions.ImageInfoException;

public class ImageInfoBuilder {
    private final static Pattern PATTERN_W = Pattern.compile("([w])(\\d+)q(\\d+).(" + Config.SUPPORTED_TYPES + ")");
    private final static Pattern PATTERN_H = Pattern.compile("([h])(\\d+)q(\\d+).(" + Config.SUPPORTED_TYPES + ")");
    private final static Pattern PATTERN_C = Pattern.compile("([c])(\\d+)x(\\d+)q(\\d+).(" + Config.SUPPORTED_TYPES + ")");

    public static ImageInfo parseImageInfoString(String str) {

        Matcher matcher = PATTERN_W.matcher(str);
        if (matcher.find()) {
            return ImageInfo.builder()
                    .algorithm(matcher.group(1))
                    .width(Integer.parseInt(matcher.group(2)))
                    .quality(Integer.parseInt(matcher.group(3)))
                    .type(matcher.group(4))
                    .build();
        }


        matcher = PATTERN_H.matcher(str);
        if (matcher.find()) {
            return ImageInfo.builder()
                    .algorithm(matcher.group(1))
                    .height(Integer.parseInt(matcher.group(2)))
                    .quality(Integer.parseInt(matcher.group(3)))
                    .type(matcher.group(4))
                    .build();
        }

        matcher = PATTERN_C.matcher(str);
        if (matcher.find()) {
            return ImageInfo.builder()
                    .algorithm(matcher.group(1))
                    .width(Integer.parseInt(matcher.group(2)))
                    .height(Integer.parseInt(matcher.group(3)))
                    .quality(Integer.parseInt(matcher.group(4)))
                    .type(matcher.group(5))
                    .build();
        }

        throw new ImageInfoException(str);

    }

}
