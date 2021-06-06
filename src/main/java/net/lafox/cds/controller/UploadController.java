package net.lafox.cds.controller;

import javax.servlet.http.HttpServletRequest;

import net.lafox.cds.service.FileService;
import net.lafox.cds.service.SecurityService;
import net.lafox.cds.service.exceptions.WrongTokenException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class UploadController {
    private final SecurityService securityService;
    private final FileService fileService;


    @PostMapping(path = "/uploadImage", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public Object handleFormUpload(@RequestParam MultipartFile file, HttpServletRequest request) {
        securityService.validateToken(request);
        securityService.validateIpAddress(request);
        return fileService.save(file);
    }

    @ExceptionHandler(value = {WrongTokenException.class})
    protected ResponseEntity<Object> handleEntityNotFound(Exception ex) {
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }
}