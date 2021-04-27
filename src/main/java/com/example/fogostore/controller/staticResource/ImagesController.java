package com.example.fogostore.controller.staticResource;

import com.example.fogostore.common.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

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
    public byte[] getImage(@PathVariable String fileName){
        byte[] content = new byte[]{};
        String location = fileUtils.getImageFolderPath();
        File f = new File(location + File.separator + fileName);
        try {
            content = Files.readAllBytes(f.toPath());
        } catch (IOException e) {
            System.out.println("Can not find " + fileName);
//            e.printStackTrace();
        }
        return content;
    }
}
