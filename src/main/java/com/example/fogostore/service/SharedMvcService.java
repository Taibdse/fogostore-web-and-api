package com.example.fogostore.service;

import com.example.fogostore.common.enumeration.PageType;
import com.example.fogostore.common.utils.tree.Node;
import com.example.fogostore.dto.*;
import com.example.fogostore.dto.policy.BasicPolicy;
import com.example.fogostore.model.*;
import com.example.fogostore.repository.CategoryRepository;
import com.example.fogostore.repository.PageMetadataRepository;
import com.example.fogostore.repository.ShopRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Map;

public interface SharedMvcService {
    void addSharedModelAttributes(Model model, PageType pageType);
}

@Service
class SharedMvcServiceImpl implements SharedMvcService {

    @Autowired
    CategoryService categoryService;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    ShopRepository shopRepository;

    @Autowired
    WebsiteService websiteService;

    @Autowired
    PolicyService policyService;

    @Autowired
    BrandService brandService;

    @Autowired
    PageMetadataRepository pageMetadataRepository;

    @Autowired
    ViewService viewService;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public void addSharedModelAttributes(Model model, PageType pageType) {
        Shop shop = shopRepository.findLatest();
        WebsiteDto website = websiteService.getThelatest();
        List<BasicPolicy> policies = policyService.getAllActive();

        Map<Integer, Node> brandMap = brandService.getActiveTree();
        Map<Integer, Node> categoryMap = categoryService.getActiveTree();

        PageMetadataDto pageMetadata = new PageMetadataDto();

        if (pageType != null) {
            switch (pageType) {
                case PRODUCT_DETAIL:
                    ProductDto product = (ProductDto) model.asMap().get("product");
                    if (product != null) {
                        PageMetadata p = pageMetadataRepository.findByProductId(product.getId());
                        if (p != null) {
                            pageMetadata = modelMapper.map(p, PageMetadataDto.class);
                            pageMetadata.setImage(product.getMainImage());
                            pageMetadata.setProductPrice(product.getPrice());
                        };
                    }
                    break;
                case BLOG_DETAIL:
                    BlogDto blog = (BlogDto) model.asMap().get("blog");
                    if (blog != null) {
                        PageMetadata p = pageMetadataRepository.findByBlogId(blog.getId());
                        if (p != null) {
                            pageMetadata.setImage(blog.getImage());
                        };
                    }
                    break;
                case SEARCH_PRODUCT_CATEGORY:
                    CategoryDto category = (CategoryDto) model.asMap().get("category");
                    if (category != null) {
                        PageMetadata p = category.getPageMetadata();
                        if (p != null) {
                            pageMetadata = modelMapper.map(p, PageMetadataDto.class);
                        };
                    }
                    break;
                case ABOUT_US:
                case HOME:
                    List<PageMetadata> pageMetadataList = pageMetadataRepository.findByPageType(PageType.ABOUT_US);
                    if (pageMetadataList.size() > 0) {
                        pageMetadata = modelMapper.map(pageMetadataList.get(0), PageMetadataDto.class);
                    }
                    break;
            }
        }

        model.addAttribute("pageMetadata", pageMetadata);
        model.addAttribute("website", website);
        model.addAttribute("shop", shop);
        model.addAttribute("brandMap", brandMap);
        model.addAttribute("categoryMap", categoryMap);
        model.addAttribute("policies", policies);
        viewService.create();
    }
}
