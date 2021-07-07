package com.example.fogostore.service;

import com.example.fogostore.common.utils.CustomStringUtils;
import com.example.fogostore.common.utils.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public interface SharedService {
    String formatEditorContent(String content);
}

@Service
class SharedServiceImpl implements SharedService {

    @Autowired
    FileUtils fileUtils;

    @Value("${web_domain}")
    private String WEB_DOMAIN;

    private String handleMatchedImageSrc(MatchResult matchResult) {
        String src = matchResult.group(1);
        if (src != null && (src.indexOf("data:image/") == 0 || src.indexOf("data:application/octet-stream") == 0) && src.indexOf(";base64,") > 0) {
//            CloudinaryUploadResult uploadResult = cloudinaryService.uploadImage(src);
//            if (uploadResult != null) {
//                return String.format("src='%s'", uploadResult.getSecureUrl());
//            }
            HashMap<String, String> uploadResult = fileUtils.saveImage(src, CustomStringUtils.genUniqueId());
            if (!StringUtils.isEmpty(uploadResult.get("fileName"))) {
                return String.format("src='%s'", WEB_DOMAIN + uploadResult.get("fileName"));
            }
        }
        return matchResult.group();
    }

    @Override
    public String formatEditorContent(String content) {
        if (StringUtils.isEmpty(content)) return "";

        Pattern pattern = Pattern.compile("src=\"(.*?)\"", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(content);
        String s = matcher.replaceAll(this::handleMatchedImageSrc);

        Pattern pattern1 = Pattern.compile("src='(.*?)'", Pattern.CASE_INSENSITIVE);
        Matcher matcher1 = pattern1.matcher(s);
        s = matcher1.replaceAll(this::handleMatchedImageSrc);
        return s;
    }

}
