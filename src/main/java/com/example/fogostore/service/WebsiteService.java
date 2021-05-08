package com.example.fogostore.service;

import com.example.fogostore.common.constants.ImageConstants;
import com.example.fogostore.common.utils.FileUtils;
import com.example.fogostore.builder.ResultBuilder;
import com.example.fogostore.dto.WebsiteDto;
import com.example.fogostore.model.*;
import com.example.fogostore.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public interface WebsiteService {
    ResultBuilder update(WebsiteDto websiteDto);

    WebsiteDto getThelatest();

    WebsiteDto mapModelToDto(Website website);
}

@Service
class WebsiteServiceImpl implements WebsiteService {

    @Autowired
    ImageRepository imageRepository;

    @Autowired
    WebsiteRepository websiteRepository;

    @Autowired
    FileUtils fileUtils;

    @Autowired
    ModelMapper modelMapper;

    private final String NOTFOUND = "không tìm thấy thông tin ưebiste!";
    private final String HOME_BANNER_EMPTY = "Ảnh trang chủ không được để trống!";
    private final String HOME_BANNER_EXCEED_QUANTITY = "Ảnh trang chủ không được quá 8 ảnh!";
    private final String CUSTOMER_PARTNER_IMAGES_EMPTY = "Ảnh đối tác khách hàng không được để trống!";
    private final String CUSTOMER_PARTNER_IMAGES_EXCEED_QUANTITY = "Ảnh đối tác khách hàng không quá 30 ảnh!";
    private final String SUB_BANNER_EMPTY = "Ảnh trang con không được để trống!";

    private HashMap<String, String> validate(WebsiteDto websiteDto) {
        HashMap<String, String> errors = new HashMap<>();
        Integer id = websiteDto.getId();
        if (id == null) {
            errors.put("NOTFOUND", NOTFOUND);
        } else {
            Website found = websiteRepository.findById(id).orElse(null);
            if (found == null) {
                errors.put("NOTFOUND", NOTFOUND);
            } else {
                if (websiteDto.getHomePageImages().size() == 0) {
                    errors.put("HOME_BANNER_EMPTY", HOME_BANNER_EMPTY);
                } else if (websiteDto.getHomePageImages().size() > 8) {
                    errors.put("HOME_BANNER_EXCEED_QUANTITY", HOME_BANNER_EXCEED_QUANTITY);
                }
//                if(websiteDto.getCustomerPartnerImages().size() == 0){
//                    errors.put("CUSTOMER_PARTNER_IMAGES_EMPTY", CUSTOMER_PARTNER_IMAGES_EMPTY);
//                } else if(websiteDto.getCustomerPartnerImages().size() > 30){
//                    errors.put("CUSTOMER_PARTNER_IMAGES_EXCEED_QUANTITY", CUSTOMER_PARTNER_IMAGES_EXCEED_QUANTITY);
//                }
                if (StringUtils.isEmpty(websiteDto.getSubPageImage())) {
                    errors.put("SUB_BANNER_EMPTY", SUB_BANNER_EMPTY);
                }
            }
        }

        return errors;
    }

    @Override
    public ResultBuilder update(WebsiteDto websiteDto) {
        ResultBuilder result = new ResultBuilder();
        HashMap<String, String> errors = validate(websiteDto);
        if (errors.size() > 0) {
            return result.success(false).errors(errors);
        }

        Website website = modelMapper.map(websiteDto, Website.class);
        websiteRepository.save(website);

        List<Image> subPagesImages = imageRepository.findByType(ImageConstants.SUBPAGES_BANNER);
        List<Image> homePageImages = imageRepository.findByType(ImageConstants.HOMEPAGE_BANNER);
        List<Image> customerPartnerImages = imageRepository.findByType(ImageConstants.CUSTOMER_PARTNER);
        List<Image> logoWebsiteImages = imageRepository.findByType(ImageConstants.LOGO_WEBSITE);


        List<Image> deletedImages = new ArrayList<>();
        homePageImages.stream().forEach(image -> {
            if (!websiteDto.getHomePageImages().contains(image.getUrl())) {
                deletedImages.add(image);
            }
        });
        customerPartnerImages.stream().forEach(image -> {
            if (!websiteDto.getCustomerPartnerImages().contains(image.getUrl())) {
                deletedImages.add(image);
            }
        });
        if (subPagesImages.size() > 0) {
            if (!subPagesImages.get(0).getUrl().equals(websiteDto.getSubPageImage())) {
                deletedImages.add(subPagesImages.get(0));
            }
        }
        if (logoWebsiteImages.size() > 0) {
            if (!logoWebsiteImages.get(0).getUrl().equals(websiteDto.getLogoWebsiteImage())) {
                deletedImages.add(logoWebsiteImages.get(0));
            }
        }

        imageRepository.deleteAll(deletedImages);
        deletedImages.stream().forEach(image -> fileUtils.removeFile(image.getUrl()));

        int index = 1;
        for (String homePageImage : websiteDto.getHomePageImages()) {
            Image found = homePageImages.stream().filter(image -> {
                return image.getUrl().equals(homePageImage);
            }).findFirst().orElse(null);
            if (found == null) {
                Image newImage = saveImageFile(homePageImage, ImageConstants.HOMEPAGE_BANNER, index++);
                imageRepository.save(newImage);
            }
        }

//        for (String customerPartnerImage : websiteDto.getCustomerPartnerImages()) {
//            Image found = customerPartnerImages.stream().filter(image -> {
//                return image.getUrl().equals(customerPartnerImage);
//            }).findFirst().orElse(null);
//            if (found == null) {
//                Image image = saveImageFile(customerPartnerImage, ImageConstants.CUSTOMER_PARTNER, index++);
//                imageRepository.save(image);
//            }
//        }

        if (subPagesImages.size() == 0 || !subPagesImages.get(0).getUrl().equals(websiteDto.getSubPageImage())) {
            Image subImage = saveImageFile(websiteDto.getSubPageImage(), ImageConstants.SUBPAGES_BANNER, index);
            imageRepository.save(subImage);
        }

        if (logoWebsiteImages.size() == 0 || !logoWebsiteImages.get(0).getUrl().equals(websiteDto.getLogoWebsiteImage())) {
            Image logoWebsiteImage = saveImageFile(websiteDto.getLogoWebsiteImage(), ImageConstants.LOGO_WEBSITE, index);
            imageRepository.save(logoWebsiteImage);
        }

        return result.success(true).data(websiteDto);
    }

    public Image saveImageFile(String dataUrl, String type, int index) {
        Image image = new Image();
        image.setType(type);
        image.setSortIndex(index);
        if (fileUtils.isImageUrl(dataUrl) || StringUtils.isEmpty(dataUrl)) {
            image.setUrl(dataUrl);
        } else {
            HashMap<String, String> value = fileUtils.saveImage(dataUrl, type.toLowerCase() + "-" + index);
            image.setUrl(value.get("fileName"));
        }
        return image;
    }

    @Override
    public WebsiteDto getThelatest() {
        Website website = websiteRepository.findTheLatest();
        return mapModelToDto(website);
    }

    @Override
    public WebsiteDto mapModelToDto(Website website) {
        if (website == null) return null;
        WebsiteDto websiteDto = modelMapper.map(website, WebsiteDto.class);
        List<Image> homePageImages = imageRepository.findByType(ImageConstants.HOMEPAGE_BANNER);
        List<Image> subPageImages = imageRepository.findByType(ImageConstants.SUBPAGES_BANNER);
        List<Image> customerPartnerImages = imageRepository.findByType(ImageConstants.CUSTOMER_PARTNER);
        List<Image> logoWebsiteImages = imageRepository.findByType(ImageConstants.LOGO_WEBSITE);

        List<String> homePageImagesList = homePageImages.stream().map(image -> image.getUrl()).collect(Collectors.toList());
        List<String> subPageImageList = subPageImages.stream().map(image -> image.getUrl()).collect(Collectors.toList());
        List<String> customerPartnersList = customerPartnerImages.stream().map(image -> image.getUrl()).collect(Collectors.toList());
        List<String> logoWebsiteList = logoWebsiteImages.stream().map(image -> image.getUrl()).collect(Collectors.toList());

        websiteDto.setHomePageImages(homePageImagesList);
        websiteDto.setSubPageImage(subPageImageList.size() > 0 ? subPageImageList.get(0) : "");
        websiteDto.setCustomerPartnerImages(customerPartnersList);
        websiteDto.setLogoWebsiteImage(logoWebsiteList.size() > 0 ? logoWebsiteList.get(0) : "");

        return websiteDto;
    }
}
