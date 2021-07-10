package com.example.fogostore.service;


import com.example.fogostore.builder.ResultBuilder;
import com.example.fogostore.dto.socialPlugin.SocialPluginDto;
import com.example.fogostore.form.socialPlugin.SocialPluginForm;
import com.example.fogostore.form.socialPlugin.SocialPluginSearchForm;
import com.example.fogostore.model.SocialPlugin;
import com.example.fogostore.repository.SocialPluginRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;


public interface SocialPluginService {
    SocialPluginDto getById(Integer id);

    SocialPluginDto toDto(SocialPlugin socialPlugin);

    SocialPlugin toEntity(SocialPluginForm socialPluginForm);

    HashMap<String, String> validate(SocialPluginForm socialPluginForm, Boolean editMode);

    ResultBuilder create(SocialPluginForm socialPluginForm);

    ResultBuilder update(SocialPluginForm socialPluginForm);

    ResultBuilder delete(Integer id);

    ResultBuilder deleteByIds(List<Integer> ids);

    Page<SocialPluginDto> search(SocialPluginSearchForm socialPluginSearchForm);

    ResultBuilder saveSortIndexes(List<SocialPluginForm> socialPlugins);

    List<SocialPluginDto> getShow();
}

@Service
class SocialPluginServiceImpl implements SocialPluginService {


    @Autowired
    SocialPluginRepository socialPluginRepository;


    @Autowired
    ModelMapper modelMapper;


    @Autowired
    SharedService sharedService;


    @Override
    public SocialPluginDto getById(Integer id) {
        return toDto(socialPluginRepository.findById(id).orElse(null));
    }

    @Override
    public SocialPluginDto toDto(SocialPlugin socialPlugin) {
        if (socialPlugin == null) return null;
        SocialPluginDto dto = modelMapper.map(socialPlugin, SocialPluginDto.class);
        return dto;
    }

    @Override
    public SocialPlugin toEntity(SocialPluginForm socialPluginForm) {
        if (socialPluginForm == null) return null;
        SocialPlugin entity = modelMapper.map(socialPluginForm, SocialPlugin.class);
        return entity;
    }

    @Override
    public HashMap<String, String> validate(SocialPluginForm socialPluginForm, Boolean editMode) {
        HashMap<String, String> errors = new HashMap<>();

        if (socialPluginForm.getPosition() == null) {
            errors.put("POSITION_EMPTY", "Vui lòng chọn vị trí cho social plugin!");
        }

        if (StringUtils.isEmpty(socialPluginForm.getName())) {
            errors.put("NAME_EMPTY", "Vui lòng tên cho social plugin!");
        }

        if (StringUtils.isEmpty(socialPluginForm.getCode())) {
            errors.put("CODE_EMPTY", "Vui lòng code cho social plugin!");
        }

        if (editMode && !socialPluginRepository.existsById(socialPluginForm.getId())) {
            errors.put("NOT_FOUND", "Không tìm thấy social plugin này!");
        }

        return errors;
    }

    @Override
    public ResultBuilder create(SocialPluginForm socialPluginForm) {
        HashMap<String, String> errors = validate(socialPluginForm, false);
        if (!errors.isEmpty()) {
            return ResultBuilder.build().success(false).errors(errors);
        }
        SocialPlugin socialPlugin = toEntity(socialPluginForm);
        socialPlugin.setActive(true);
        socialPlugin = socialPluginRepository.save(socialPlugin);
        return ResultBuilder.build().success(true).data(socialPlugin);
    }

    @Override
    public ResultBuilder update(SocialPluginForm socialPluginForm) {
        HashMap<String, String> errors = validate(socialPluginForm, true);
        if (!errors.isEmpty()) {
            return ResultBuilder.build().success(false).errors(errors);
        }
        SocialPlugin socialPlugin = toEntity(socialPluginForm);
        socialPlugin = socialPluginRepository.save(socialPlugin);
        return ResultBuilder.build().success(true).data(socialPlugin);
    }

    @Override
    public ResultBuilder delete(Integer id) {
        List<Integer> ids = new ArrayList<>();
        ids.add(id);
        socialPluginRepository.inactivateByIds(ids);
        return ResultBuilder.build().success(true).data(id);
    }

    @Override
    public ResultBuilder deleteByIds(List<Integer> ids) {
        socialPluginRepository.inactivateByIds(ids);
        return ResultBuilder.build().success(true).data(ids);
    }

    @Override
    public Page<SocialPluginDto> search(SocialPluginSearchForm socialPluginSearchForm) {
        String searchValue = Optional.ofNullable(socialPluginSearchForm.getSearchValue()).orElse("");
        Integer page = Optional.ofNullable(socialPluginSearchForm.getPage()).orElse(1);
        Integer size = Optional.ofNullable(socialPluginSearchForm.getSize()).orElse(10);

        Pageable pageable = PageRequest.of(page - 1, size, Sort.by("sortIndex").ascending());
        return socialPluginRepository.search(searchValue, pageable).map(new Function<SocialPlugin, SocialPluginDto>() {
            @Override
            public SocialPluginDto apply(SocialPlugin socialPlugin) {
                return toDto(socialPlugin);
            }
        });
    }

    @Override
    public ResultBuilder saveSortIndexes(List<SocialPluginForm> socialPlugins) {
        for (SocialPluginForm socialPlugin : socialPlugins) {
            socialPluginRepository.updateSortIndexById(socialPlugin.getId(), socialPlugin.getSortIndex());
        }
        return ResultBuilder.build().success(true).data(socialPlugins);
    }

    @Override
    public List<SocialPluginDto> getShow() {
        return socialPluginRepository.findAllShow().stream().map(this::toDto).collect(Collectors.toList());
    }
}
