package com.example.fogostore.service;

import com.example.fogostore.builder.ResultBuilder;
import com.example.fogostore.model.Tag;
import com.example.fogostore.repository.ProductRepository;
import com.example.fogostore.repository.ProductTagRepository;
import com.example.fogostore.repository.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TagService {
    Tag getById(Integer id);
    ResultBuilder create(Tag tag);
    ResultBuilder update(Tag tag);
    ResultBuilder delete(Integer id);
    List<Tag> getAllActive();
}

@Service
class TagServiceImpl implements TagService{

    @Autowired
    TagRepository tagRepository;

    @Autowired
    ProductRepository productRepository;

    @Autowired
    ProductTagRepository productTagRepository;

    private final String NOTFOUND = "Không tìm thấy tag này";


    @Override
    public Tag getById(Integer id) {
        return tagRepository.findById(id).orElse(null);
    }

    @Override
    public ResultBuilder create(Tag tag) {
        ResultBuilder resultBuilder = ResultBuilder.build();
        tag.setActive(true);
        tag = tagRepository.save(tag);
        return resultBuilder.success(true).data(tag);
    }

    @Override
    public ResultBuilder update(Tag tag) {
        ResultBuilder resultBuilder = ResultBuilder.build();
        Tag found = getById(tag.getId());
        if(found == null){
            return resultBuilder.success(false).errors("NOTFOUND", NOTFOUND);
        } else {
            tag = tagRepository.save(tag);
        }
        return resultBuilder.success(true).data(tag);
    }

    @Override
    public ResultBuilder delete(Integer id) {
        ResultBuilder resultBuilder = ResultBuilder.build();
        Tag found = getById(id);
        if(found == null || !found.isActive()){
            return resultBuilder.success(false).errors("NOTFOUND", NOTFOUND);
        } else {
            found.setActive(false);
            tagRepository.save(found);
        }
        return resultBuilder.success(true).data(found);
    }

    @Override
    public List<Tag> getAllActive() {
        return tagRepository.findAllByActiveTrue();
    }
}
