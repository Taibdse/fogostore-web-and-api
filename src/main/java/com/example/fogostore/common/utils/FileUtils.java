package com.example.fogostore.common.utils;


import com.example.fogostore.common.constants.ImageConstants;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;

@Component
@Getter
public class FileUtils {

    @Value("${image_folder_path}")
    private String imageFolderPath;

    public HashMap<String, String> checkImage(String base64, boolean allowImageEmpty){
        HashMap<String, String> errors = new HashMap<>();

        if(StringUtils.isEmpty(base64)){
            if(!allowImageEmpty) errors.put("image", "Image is required");
        } else {
            if(isImageUrl(base64)) return errors;

            if(isImageOverSized(base64, 1)) errors.put("image", "Size Ảnh không được quá 1MB");
            else {
                byte[] decodedMainImage = getByteArrayFromImageDataUri(base64);
                if (!isImage(decodedMainImage)) errors.put("image", "Chỉ được upload ảnh!");
            }
        }
        return errors;
    }

    public boolean isImageUrl(String image){
        if(StringUtils.isEmpty(image)) return false;
        if(image.indexOf(ImageConstants.IMAGE_PREFIX_URL) > -1 || image.indexOf("http") > -1) return true;
        return false;
    }

    public String removeImageRootWeb(String url) {
        if(StringUtils.isEmpty(url)) return "";
        return url.replace("https://fmanracing.com", "");
    }

    public boolean isImage(byte[] decodedImage){
        InputStream is = new ByteArrayInputStream(decodedImage);
        try {
            String mimeType = URLConnection.guessContentTypeFromStream(is); //mimeType is something like "image/jpeg"
            if(mimeType.indexOf("image/") == 0) return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public byte[] getByteArrayFromImageDataUri(String dataUrl){
        String base64Image = dataUrl;
        if(dataUrl.indexOf(",") >= 0) {
            base64Image = dataUrl.split(",")[1];
        }
        byte[] decodedImg = Base64.getDecoder().decode(base64Image.getBytes(StandardCharsets.UTF_8));
        return decodedImg;
    }

    public boolean isImageOverSized(String base64, float size){
        return (((double) base64.length()) * 3 / 4) > size * 1024 * 1024;
    }

    public String getImageExtFromBase64(String base64Data){
        return base64Data.substring("data:image/".length(), base64Data.indexOf(";base64"));
    }

    public HashMap<String, String> saveImage(String dataUrl, String fileName){
        HashMap<String, String> returnValue = new HashMap<>();

        byte[] decodedImg = getByteArrayFromImageDataUri(dataUrl);

        try {
            String filePath = imageFolderPath;
            File f = new File(filePath);
            if(!f.exists()){
                f.mkdir();
            }
            fileName = fileName + "-" + new Date().getTime() + "." + getImageExtFromBase64(dataUrl);

            Path destinationFile = Paths.get(filePath, fileName);

            Files.write(destinationFile, decodedImg);
        } catch (Exception ex) {
            ex.printStackTrace();
            returnValue.put("errors", "Không thể lưu hình trên hệ thống!");
        }

        if(returnValue.size() == 0){
            returnValue.put("fileName", ImageConstants.IMAGE_PREFIX_URL + fileName);
        } else {
            returnValue.put("fileName", "");
        }

        return returnValue;
    }

    public void removeFile(String fileName){
        if(StringUtils.isEmpty(fileName)) return;

        if(fileName.indexOf(ImageConstants.IMAGE_PREFIX_URL) == 0){
            fileName = fileName.substring(ImageConstants.IMAGE_PREFIX_URL.length());
        }

        String path = imageFolderPath + "/" + fileName;
        try {
            Path fileToDeletePath = Paths.get(path);
            Files.deleteIfExists(fileToDeletePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
