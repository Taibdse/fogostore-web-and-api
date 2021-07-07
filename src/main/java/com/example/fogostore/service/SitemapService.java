package com.example.fogostore.service;

import com.example.fogostore.common.constants.PageSize;
import com.example.fogostore.common.constants.ProductSortBy;
import com.example.fogostore.dto.policy.BasicPolicy;
import com.example.fogostore.model.*;
import com.example.fogostore.repository.*;
import com.redfin.sitemapgenerator.ChangeFreq;
import com.redfin.sitemapgenerator.W3CDateFormat;
import com.redfin.sitemapgenerator.WebSitemapGenerator;
import com.redfin.sitemapgenerator.WebSitemapUrl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public interface SitemapService {
    void genSitemap();
}

@Service
class SitemapServiceImpl implements SitemapService{

    @Value("${sitemap_location}")
    private String sitemapLocation;

    @Value("${web_domain}")
    private String domainName;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    BlogRepository blogRepository;

    @Autowired
    PolicyRepository policyRepository;

    private WebSitemapUrl createSitemapUrl(String url, Date lastMod){
        try {
            return new WebSitemapUrl.Options(url)
                    .lastMod(lastMod).priority(1.0).changeFreq(ChangeFreq.WEEKLY).build();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void genSitemap() {
        WebSitemapGenerator wsg = null;
        W3CDateFormat dateFormat = new W3CDateFormat(W3CDateFormat.Pattern.MINUTE);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        List<String> productSortByList = ProductSortBy.getList();
        try {
            wsg = WebSitemapGenerator.builder(domainName, new File(sitemapLocation)).dateFormat(dateFormat).build();
            wsg.addUrl(createSitemapUrl(domainName, new Date()));
            wsg.addUrl(createSitemapUrl(domainName + "/trang-chu", new Date()));
            wsg.addUrl(createSitemapUrl(domainName + "/ve-chung-toi", new Date()));

            List<Product> products = productRepository.findAllActiveWithoutPagination();

            for (Product product : products) {
                String url = domainName + "/san-pham/" + product.getSlug();
                wsg.addUrl(createSitemapUrl(url, new Date()));
            }

            Integer countDiscountProduct = productRepository.countDiscount();
            for(int i = 1; i <= Math.ceil((double) countDiscountProduct / PageSize.PRODUCT_PAGE_SIZE); i++){
                String url = domainName + "/khuyen-mai?" + "?page=" + i;
                wsg.addUrl(createSitemapUrl(url, new Date()));
            }

            List<Category> categories = categoryRepository.findAllActive();
            for (Category category : categories) {
                Integer countProducts = productRepository.countByCategoryId(category.getId());
                for(int i = 1; i <= Math.ceil((double) countProducts / PageSize.PRODUCT_PAGE_SIZE); i++){
                    String url = domainName + "/danh-muc/" + category.getSlug() + "?page=" + i;
                    for (String productSortBy : productSortByList) {
                        String sortUrl = url + "&sortBy=" + productSortBy;
                        wsg.addUrl(createSitemapUrl(sortUrl, new Date()));
                    }
                }
            }

            List<Brand> brands = brandRepository.findAllActive();
            for (Brand brand : brands) {
                Integer countProducts = productRepository.countByCategoryId(brand.getId());
                for(int i = 1; i <= Math.ceil((double) countProducts / PageSize.PRODUCT_PAGE_SIZE); i++){
                    String url = domainName + "/hang-xe/" + brand.getSlug() + "?page=" + i;
                    for (String productSortBy : productSortByList) {
                        String sortUrl = url + "&sortBy=" + productSortBy;
                        wsg.addUrl(createSitemapUrl(sortUrl, new Date()));
                    }
                }
            }

            List<Blog> blogs = blogRepository.findAllActive();
            for (Blog blog : blogs) {
                String url = domainName + "/bai-viet/" + blog.getSlug();
                wsg.addUrl(createSitemapUrl(url, blog.getUpdatedAt()));
            }
            for(int i = 1; i <= Math.ceil((double) blogs.size() / PageSize.DEFAULT_PAGE_SIZE); i++){
                String url = domainName + "/bai-viet?page=" + i;
                wsg.addUrl(createSitemapUrl(url, new Date()));
            }

            List<BasicPolicy> policies = policyRepository.findAllActive();
            for (BasicPolicy policy : policies) {
                String url = domainName + "/chinh-sach/" + policy.getSlug();
                wsg.addUrl(createSitemapUrl(url, new Date()));
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        wsg.write();
        System.out.println("done gen sitemap");
    }
}
