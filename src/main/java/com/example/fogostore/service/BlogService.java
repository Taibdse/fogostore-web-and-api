package com.example.fogostore.service;


import com.example.fogostore.common.constants.BlogType;
import com.example.fogostore.common.enumeration.PageType;
import com.example.fogostore.common.utils.CustomStringUtils;
import com.example.fogostore.common.utils.FileUtils;
import com.example.fogostore.builder.ResultBuilder;
import com.example.fogostore.dto.BlogDto;
import com.example.fogostore.dto.BrandDto;
import com.example.fogostore.model.Blog;
import com.example.fogostore.model.Brand;
import com.example.fogostore.model.PageMetadata;
import com.example.fogostore.repository.BlogRepository;
import com.example.fogostore.repository.PageMetadataRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


public interface BlogService {
    BlogDto getById(Integer id);

    BlogDto getBySlug(String slug);

    BlogDto toDto(Blog blog, Boolean includeDetails);

    ResultBuilder create(BlogDto blog);

    ResultBuilder update(BlogDto blog);

    ResultBuilder delete(Integer id);

    ResultBuilder deleteByIds(List<Integer> ids);

    Page<Blog> search(String blog, int page, int size);

    ResultBuilder saveSortIndexes(List<BlogDto> blogs);

    List<BlogDto> getHotBlogs();
}

@Service
class BlogServiceImpl implements BlogService {


    @Autowired
    BlogRepository blogRepository;

    @Autowired
    FileUtils fileUtils;

    @Autowired
    ModelMapper modelMapper;

    @Autowired
    PageMetadataRepository pageMetadataRepository;

    @Autowired
    SharedService sharedService;

    private final String NOTFOUND = "Không tìm thấy bài viết này";

    @Override
    public List<BlogDto> getHotBlogs() {
        List<Blog> blogs = blogRepository.findByHot();
        return blogs.stream().map(b -> toDto(b, false)).collect(Collectors.toList());
    }

    @Override
    public ResultBuilder saveSortIndexes(List<BlogDto> blogs) {
        for (BlogDto blogDto : blogs) {
            blogRepository.updateSortIndexById(blogDto.getId(), blogDto.getSortIndex());
        }
        return ResultBuilder.build().success(true).data(blogs);
    }

    @Override
    public ResultBuilder deleteByIds(List<Integer> ids) {
        blogRepository.inactivateByIds(ids);
        return ResultBuilder.build().success(true).data(ids);
    }

    @Override
    public BlogDto toDto(Blog blog, Boolean includeDetails) {
        if (blog == null) return null;
        BlogDto blogDto = modelMapper.map(blog, BlogDto.class);
        if (includeDetails) {
            blogDto.setPageMetadata(pageMetadataRepository.findByBlogId(blog.getId()));
        }
        return blogDto;
    }

    @Override
    public BlogDto getById(Integer id) {
        try {
            Blog blog = blogRepository.findById(id).orElse(null);
            if (blog == null || !blog.isActive()) return null;
            return toDto(blog, true);
        } catch (Exception ex) {
            return null;
        }
    }

    @Override
    public BlogDto getBySlug(String slug) {
        try {
            Blog blog = blogRepository.findBySlug(slug);
            if (blog == null || !blog.isActive()) return null;
            return toDto(blog, true);
        } catch (Exception ex) {
            return null;
        }
    }

    public HashMap<String, String> validate(BlogDto blog, boolean editMode) {
        HashMap<String, String> errors = new HashMap<>();

        if (editMode) {
            BlogDto found = getById(blog.getId());
            if (found == null) {
                errors.put("NOTFOUND", NOTFOUND);
                return errors;
            }
        }

        HashMap<String, String> imageErrors = fileUtils.checkImage(blog.getImage(), editMode);
        if (imageErrors.size() > 0) return imageErrors;

        return errors;
    }

    @Override
    public ResultBuilder create(BlogDto blog) {

        ResultBuilder result = ResultBuilder.build();
        HashMap<String, String> errors = validate(blog, false);
        if (errors.size() > 0) return result.success(false).errors(errors);

        Blog savedBlog = modelMapper.map(blog, Blog.class);
        String slug = CustomStringUtils.genSlug(blog.getTitle());
        Blog found = blogRepository.findBySlug(slug);
        if (found != null) {
            slug = slug + '-' + new Date().getTime();
        }
        savedBlog.setSlug(slug);
        savedBlog.setActive(true);
        savedBlog.setType(BlogType.NEWS);
        savedBlog.setContent(sharedService.formatEditorContent(savedBlog.getContent()));

        HashMap<String, String> value = fileUtils.saveImage(blog.getImage(), slug);
        savedBlog.setImage(value.get("fileName"));

        savedBlog = blogRepository.save(savedBlog);

        //save page metadata
        PageMetadata pageMetadata = blog.getPageMetadata();
        if (pageMetadata == null) pageMetadata = new PageMetadata();
        pageMetadata.setPageType(PageType.BLOG_DETAIL);
        pageMetadata.setBlogId(savedBlog.getId());
        pageMetadataRepository.save(pageMetadata);

        return result.success(true).data(savedBlog);
    }

    @Override
    public ResultBuilder update(BlogDto blog) {
        ResultBuilder result = ResultBuilder.build();
        HashMap<String, String> errors = validate(blog, true);
        if (errors.size() > 0) return result.success(false).errors(errors);

        Blog savedBlog = modelMapper.map(blog, Blog.class);
        Blog foundById = blogRepository.findById(blog.getId()).orElse(null);
        String slug = CustomStringUtils.genSlug(blog.getTitle());
        Blog found = blogRepository.findBySlug(slug);
        if (found != null && found.getId() != blog.getId()) {
            slug = slug + '-' + new Date().getTime();
        }
        savedBlog.setSlug(slug);
        savedBlog.setContent(sharedService.formatEditorContent(savedBlog.getContent()));

        String newImage = blog.getImage();
        String oldImage = foundById.getImage();
        if (fileUtils.isImageUrl(newImage) || StringUtils.isEmpty(newImage)) {
            savedBlog.setImage(oldImage);
        } else {
            HashMap<String, String> value = fileUtils.saveImage(newImage, slug);
            fileUtils.removeFile(oldImage);
            savedBlog.setImage(value.get("fileName"));
        }

        savedBlog = blogRepository.save(savedBlog);

        //save page metadata
        PageMetadata pageMetadata = blog.getPageMetadata();
        PageMetadata currentPageMetadata = pageMetadataRepository.findByBlogId(blog.getId());
        if (currentPageMetadata != null) {
            pageMetadata.setId(currentPageMetadata.getId());
        }
        pageMetadata.setPageType(PageType.BLOG_DETAIL);
        pageMetadata.setBlogId(savedBlog.getId());
        pageMetadataRepository.save(pageMetadata);

        return result.success(true).data(savedBlog);
    }

    @Override
    public ResultBuilder delete(Integer id) {
        ResultBuilder result = ResultBuilder.build();
        Blog blog = blogRepository.findById(id).orElse(null);
        if (blog == null || !blog.isActive()) {
            return result.success(false).errors("NOTFOUND", NOTFOUND);
        } else {
            blog.setActive(false);
            blog = blogRepository.save(blog);
        }
        return result.success(false).data(blog);
    }

    @Override
    public Page<Blog> search(String blog, int page, int size) {
        if (StringUtils.isEmpty(blog)) blog = "";
        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("sortIndex").ascending());
        return blogRepository.findByAdmin(blog, pageable);
    }
}
