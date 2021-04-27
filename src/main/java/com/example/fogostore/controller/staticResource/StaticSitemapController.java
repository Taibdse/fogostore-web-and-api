package com.example.fogostore.controller.staticResource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/sitemap.xml")
public class StaticSitemapController {

    @Value("${sitemap_location}")
    String sitemapLocation;

    private final String sitemapFilename = "sitemap.xml";

    @GetMapping(
            value = "",
            produces = {MediaType.APPLICATION_XML_VALUE}
    )
    public byte[] getXmlSitemap(){
        byte[] content = new byte[]{};
        File f = new File(sitemapLocation + File.separator + sitemapFilename);
        try {
            content = Files.readAllBytes(f.toPath());
        } catch (IOException e) {
            System.out.println("Can not find sitemap.xml");
//            e.printStackTrace();
        }
        return content;
    }
}
