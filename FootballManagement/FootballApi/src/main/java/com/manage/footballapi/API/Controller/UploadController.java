package com.manage.footballapi.API.Controller;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

@RestController
@RequestMapping("/api/images")
@CrossOrigin
public class UploadController {

    public static  String uploadDiretory = System.getProperty("user.dir") + "/uploads";
    List<String> files = new ArrayList<String>();

    @PostMapping("/post")
    public String handleFileUpload(@RequestParam("file") MultipartFile file) {
       StringBuilder fileNames = new StringBuilder();
        Path fileNameAndPath = Paths.get(uploadDiretory, file.getOriginalFilename());
        try {
            Files.write(fileNameAndPath, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Successfully upload" + file.getOriginalFilename();
    }
}
