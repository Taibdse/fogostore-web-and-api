package com.example.fogostore.service;

import com.example.fogostore.common.enumeration.PageType;
import com.example.fogostore.common.utils.CustomStringUtils;
import com.example.fogostore.common.utils.FileUtils;
import com.example.fogostore.builder.ResultBuilder;
import com.example.fogostore.dto.ShopDto;
import com.example.fogostore.model.PageMetadata;
import com.example.fogostore.model.Shop;
import com.example.fogostore.repository.PageMetadataRepository;
import com.example.fogostore.repository.ShopRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

public interface ShopService {
    ShopDto getLatest();

    ResultBuilder update(ShopDto shop);
}

@Service
class ShopServiceImpl implements ShopService {

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    PageMetadataRepository pageMetadataRepository;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    FileUtils fileUtils;

    private final String NOTFOUND = "Không tìm thấy thông tin cửa hàng!";

    @Override
    public ShopDto getLatest() {
        return toDto(shopRepository.findLatest());
    }

    public HashMap<String, String> validate(ShopDto shop) {
        HashMap<String, String> errors = new HashMap<>();
        if (shop.getId() == null) {
            errors.put("NOTFOUND", NOTFOUND);
        } else {
            boolean existed = shopRepository.existsById(shop.getId());
            if (!existed) {
                errors.put("NOTFOUND", NOTFOUND);
            } else {
                HashMap<String, String> imageErrors = fileUtils.checkImage(shop.getLogo(), true);
                if (imageErrors.size() > 0) return imageErrors;
            }
        }
        return errors;
    }

    public ShopDto toDto(Shop shop) {
        if (shop == null) return null;
        ShopDto shopDto = modelMapper.map(shop, ShopDto.class);
        List<PageMetadata> pageMetadataList = pageMetadataRepository.findByPageType(PageType.ABOUT_US);
        PageMetadata pageMetadata = pageMetadataList.size() == 0 ? null : pageMetadataList.get(0);
        shopDto.setPageMetadata(pageMetadata);
        return shopDto;
    }

    @Override
    public ResultBuilder update(ShopDto shopDto) {
        ResultBuilder result = ResultBuilder.build();
        HashMap<String, String> errors = validate(shopDto);
        if (errors.size() > 0) {
            return result.success(false).errors(errors);
        }
        Shop updatedShop = modelMapper.map(shopDto, Shop.class);
        Shop found = shopRepository.findById(shopDto.getId()).orElse(null);
        String newLogo = shopDto.getLogo();
        String oldLogo = found.getLogo();

        if (fileUtils.isImageUrl(newLogo) || StringUtils.isEmpty(newLogo)) {
            updatedShop.setLogo(oldLogo);
        } else {
            String shopNameSlug = CustomStringUtils.genSlug(shopDto.getShopName());
            HashMap<String, String> value = fileUtils.saveImage(newLogo, shopNameSlug);
            updatedShop.setLogo(value.get("fileName"));
            fileUtils.removeFile(oldLogo);
        }

        updatedShop = shopRepository.save(updatedShop);

        //save page meta data
        PageMetadata pageMetadata = shopDto.getPageMetadata();
        List<PageMetadata> currentPageMetadataList = pageMetadataRepository.findByPageType(PageType.ABOUT_US);
        if(currentPageMetadataList.size() > 0){
            pageMetadata.setId(currentPageMetadataList.get(0).getId());
        }
        pageMetadata.setPageType(PageType.ABOUT_US);
        pageMetadataRepository.save(pageMetadata);

        return result.success(true).data(updatedShop);
    }
}
