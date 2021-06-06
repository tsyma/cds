package net.lafox.cds.service;

import java.io.File;

import net.lafox.cds.Config;
import net.lafox.cds.data.ImageInfo;
import net.lafox.cds.service.exceptions.ImageNotFoundException;
import net.lafox.cds.service.exceptions.UnsupportedAlgorithmException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import net.coobird.thumbnailator.Thumbnails;
import net.coobird.thumbnailator.geometry.Positions;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ImageService {

    private final Config config;

    public File getFileName(ImageInfo imageInfo) {
        String fileName = config.getPatchConverted() + File.separator + imageInfo;
        File file = new File(fileName);
        return file.exists() ? file : convert(imageInfo, file);
    }

    @SneakyThrows
    private File convert(ImageInfo imageInfo, File converted) {
        File original = new File(config.getPatchOriginal() + File.separator + imageInfo.getId());
        if (!original.exists()) {
            throw new ImageNotFoundException("Original Image File '" + original.getAbsolutePath() + "' not exists.");
        }
        log.info("Convert '{}' -> '{}'.", original.getAbsolutePath(), converted.getAbsolutePath());

        switch (imageInfo.getAlgorithm()) {

            case "w":
                Thumbnails.of(original).outputFormat(imageInfo.getType())
                        .width(imageInfo.getWidth())
                        .outputQuality(0.01 * imageInfo.getQuality())
                        .toFile(converted);
                break;

            case "h":
                Thumbnails.of(original).outputFormat(imageInfo.getType())
                        .height(imageInfo.getHeight())
                        .outputQuality(0.01 * imageInfo.getQuality())
                        .toFile(converted);
                break;

            case "c":
                Thumbnails.of(original).outputFormat(imageInfo.getType())
                        .crop(Positions.CENTER_RIGHT)
                        .width(imageInfo.getWidth())
                        .height(imageInfo.getHeight())
                        .outputQuality(0.01 * imageInfo.getQuality())
                        .toFile(converted);
                break;

            default:
                throw new UnsupportedAlgorithmException(imageInfo.getAlgorithm());

        }
        return converted;
    }

}