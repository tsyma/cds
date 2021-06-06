package com.fishtofry.imageService.utils;

import static net.lafox.cds.utils.ImageInfoBuilder.parseImageInfoString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import net.lafox.cds.data.ImageInfo;
import net.lafox.cds.service.exceptions.ImageInfoException;
import org.junit.jupiter.api.Test;

class ImageInfoBuilderTest {

    @Test
    void that_w_is_parsed() {
        assertThat(parseImageInfoString("w111q12.png")).isEqualTo(
                ImageInfo.builder()
                        .algorithm("w")
                        .width(111)
                        .quality(12)
                        .type("png")
                        .build()
        );
    }

    @Test
    void that_h_is_parsed() {
        assertThat(parseImageInfoString("h113q14.jpg")).isEqualTo(
                ImageInfo.builder()
                        .algorithm("h")
                        .height(113)
                        .quality(14)
                        .type("jpg")
                        .build()
        );
    }

    @Test
    void that_c_is_parsed() {
        assertThat(parseImageInfoString("c11x12q13.gif")).isEqualTo(
                ImageInfo.builder()
                        .algorithm("c")
                        .width(11)
                        .height(12)
                        .quality(13)
                        .type("gif")
                        .build()
        );
    }

    @Test
    void that_c_has_error() {
        assertThatExceptionOfType(ImageInfoException.class)
                .isThrownBy(() -> parseImageInfoString("c11q13.gif"));
    }

    @Test
    void that_wrong_type() {
        assertThatExceptionOfType(ImageInfoException.class)
                .isThrownBy(() -> parseImageInfoString("Z11x12q13.gif"));
    }
}