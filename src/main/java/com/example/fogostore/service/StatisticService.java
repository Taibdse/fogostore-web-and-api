package com.example.fogostore.service;

import com.example.fogostore.dto.statistic.*;
import com.example.fogostore.repository.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public interface StatisticService {

    ProductStatisticDto getProductStatistics();

    StatisticDto getStatistics();
}

@Service
class StatisticServiceImpl implements StatisticService {

    @Autowired
    BlogRepository blogRepository;


    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    PolicyRepository policyRepository;

    @Autowired
    ModelMapper modelMapper;

    @Override
    public ProductStatisticDto getProductStatistics() {
        ProductStatisticDto productStatisticDto = new ProductStatisticDto();
        Integer discountTotal = productRepository.countDiscount();
        Integer total = productRepository.countAll();
        Integer hotTotal = productRepository.countHot();
        Integer inStockTotal = productRepository.countInStock();
        Integer outOfStockTotal = total - inStockTotal;

        productStatisticDto.setDiscountTotal(discountTotal);
        productStatisticDto.setTotal(total);
        productStatisticDto.setHotTotal(hotTotal);
        productStatisticDto.setInStockTotal(inStockTotal);
        productStatisticDto.setOutOfStockTotal(outOfStockTotal);

        return productStatisticDto;
    }

    @Override
    public StatisticDto getStatistics() {
        StatisticDto statisticDto = new StatisticDto();
        ProductStatisticDto productStatisticDto = getProductStatistics();

        Integer totalBlogs = blogRepository.countAllActive();
        BlogStatisticDto blogStatisticDto = new BlogStatisticDto();
        Integer totalHotBlogs = blogRepository.countAllHot();
        blogStatisticDto.setTotal(totalBlogs);
        blogStatisticDto.setHotTotal(totalHotBlogs);

        Integer totalPolicy = policyRepository.countAllActive();
        PolicyStatisticDto policyStatisticDto = new PolicyStatisticDto();
        policyStatisticDto.setTotal(totalPolicy);

        Integer totalCategory = categoryRepository.countAllActive();
        CategoryStatisticDto categoryStatisticDto = new CategoryStatisticDto();
        categoryStatisticDto.setTotal(totalCategory);

        statisticDto.setProductStatistic(productStatisticDto);
        statisticDto.setBlogStatistic(blogStatisticDto);
        statisticDto.setCategoryStatistic(categoryStatisticDto);
        statisticDto.setPolicyStatistic(policyStatisticDto);

        return statisticDto;
    }
}
