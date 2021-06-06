package net.lafox.cds.controller;

import java.io.File;
import java.util.concurrent.TimeUnit;

import net.lafox.cds.Config;
import net.lafox.cds.data.ImageInfo;
import net.lafox.cds.service.FileService;
import net.lafox.cds.service.ImageService;
import net.lafox.cds.service.SecurityService;
import net.lafox.cds.utils.MediaTypeHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.CacheControl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class DownloadController {
    private final ImageService imageService;
    private final FileService fileService;
    private final SecurityService securityService;

    @GetMapping(path = {
            "/{id:[a-zA-Z0-9]{" + Config.ID_LENGTH + "}}-{algorithm:[w]}{width}q{quality}.{type:" + Config.SUPPORTED_TYPES + "}",
            "/{id:[a-zA-Z0-9]{" + Config.ID_LENGTH + "}}-{algorithm:[h]}{height}q{quality}.{type:" + Config.SUPPORTED_TYPES + "}",
            "/{id:[a-zA-Z0-9]{" + Config.ID_LENGTH + "}}-{algorithm:[c]}{width}x{height}q{quality}.{type:" + Config.SUPPORTED_TYPES + "}"
    })
    public ResponseEntity<InputStreamResource> handleFormUpload(
            @PathVariable String id,
            @PathVariable String algorithm,
            @PathVariable Integer width,
            @PathVariable Integer height,
            @PathVariable Integer quality,
            @PathVariable String type
    ) {
        ImageInfo imageInfo = ImageInfo.builder()
                .id(id)
                .algorithm(algorithm)
                .width(width)
                .height(height)
                .type(type)
                .quality(quality)
                .build();

        securityService.validateImageFormat(imageInfo);

        File file = imageService.getFileName(imageInfo);

        CacheControl cacheControl = CacheControl
                .maxAge(3000, TimeUnit.DAYS)
                .cachePublic()
                .noTransform();

        return ResponseEntity.ok()
                .eTag(String.valueOf(file.lastModified()))
                .lastModified(file.lastModified())
                .cacheControl(cacheControl)
                .contentLength(file.length())
                .contentType(MediaTypeHelper.getMediaType(type))
                .body(fileService.getInputStreamResource(file));
    }

    @ExceptionHandler(Exception.class)
    protected ResponseEntity<?> handleException(Exception ex) {
        return ResponseEntity.notFound().build();
    }
}