package com.example.fogostore.controller.staticResource;

import com.example.fogostore.common.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/images")
public class ImagesController {

    @Autowired
    FileUtils fileUtils;

    @GetMapping(
            value = "/{fileName:.+}",
            produces = {MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE}
    )
    public byte[] getImage(@PathVariable String fileName) {
        return fileUtils.findImageFile(fileName);
    }
}
