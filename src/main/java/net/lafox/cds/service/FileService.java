package net.lafox.cds.service;

import static org.apache.commons.io.FileUtils.copyInputStreamToFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

import net.lafox.cds.Config;
import net.lafox.cds.utils.PasswordUtils;
import net.lafox.cds.service.exceptions.ImageIdException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileService {

    private final Config config;

    @SneakyThrows
    public synchronized String save(MultipartFile inputFile) {
        String id = createUniqueId();
        File outputFile = file(id);
        copyInputStreamToFile(inputFile.getInputStream(), outputFile);
        log.info("ID: {}; Uploaded file '{}' saved as: '{}'", id, inputFile.getOriginalFilename(), outputFile.getAbsolutePath());
        return id;
    }

    private String createUniqueId() {
        List<String> attempts = new ArrayList<>();
        while (true) {
            String id = PasswordUtils.randomAlphanumeric();
            attempts.add(id);
            if (!file(id).exists()) {
                return id;
            } else {
                log.warn("Image ID '{}' already used.", id);
            }
            if (attempts.size() > Config.CREATE_ID_MAX_ATTEMPTS) {
                throw new ImageIdException("Cant create image file because can't find not used ID for uploaded file. Attempts: " + attempts);
            }
        }
    }

    private File file(String id) {
        return new File(config.getPatchOriginal() + File.separator + id);
    }

    @SneakyThrows
    public InputStreamResource getInputStreamResource(File file) {
        return new InputStreamResource(new FileInputStream(file));
    }
}

