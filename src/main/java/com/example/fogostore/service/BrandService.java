package com.example.fogostore.service;

import com.example.fogostore.common.utils.CustomStringUtils;
import com.example.fogostore.common.utils.FileUtils;
import com.example.fogostore.common.utils.tree.Node;
import com.example.fogostore.common.utils.tree.TreeUtils;
import com.example.fogostore.dto.BrandDto;
import com.example.fogostore.builder.ResultBuilder;
import com.example.fogostore.model.Brand;
import com.example.fogostore.repository.BrandRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

public interface BrandService {
    ResultBuilder create(BrandDto brandDto);
    ResultBuilder update(BrandDto brandDto);
    ResultBuilder saveSortIndexs(List<BrandDto> brandDtos);
    ResultBuilder delete(List<Integer> ids);
    Brand getById(int id);
    Brand getBySlug(String slug);
    List<Brand> getAllActive();
    List<Integer> getChildIds(int id);
    Map<Integer, Node> getActiveTree();
}

@Service
class BrandServiceImpl implements BrandService{

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    FileUtils fileUtils;

    @Autowired
    ModelMapper modelMapper;

    private final String NOTFOUND = "Không thể tìm thấy hãng xe này!";
    private final String SELF_PARENT = "Hãng xe cha không hợp lệ!";
    private final String CAN_NOT_CREATE_CHILD = "Không thể thêm hãng xe con!";
    private final String CAN_NOT_FIND_PARENT = "Không tìm thấy hãng xe cha!";

    @Override
    public ResultBuilder saveSortIndexs(List<BrandDto> brandDtos){
        List<Integer> idList = brandDtos.stream().map(b -> b.getId()).collect(Collectors.toList());
        List<Brand> brandList = brandRepository.findByIds(idList);
        Map<Integer, Integer> mapIdSortIndex = new HashMap<>();
        for (BrandDto brandDto : brandDtos) {
            mapIdSortIndex.put(brandDto.getId(), brandDto.getSortIndex());
        }
        for (Brand brand : brandList) {
            brand.setSortIndex(mapIdSortIndex.get(brand.getId()));
        }
        brandRepository.saveAll(brandList);
        return  ResultBuilder.build().success(true).data(brandList);
    }

    public HashMap<String, String> validate(BrandDto brandDto, boolean editMode){
        HashMap<String, String> errors = new HashMap<>();

        if(editMode){
            if(brandDto.getId() == null){
                errors.put("NOTFOUND", NOTFOUND);
            } else {
                boolean existed = brandRepository.existsById(brandDto.getId());
                if(!existed){
                    errors.put("NOTFOUND", NOTFOUND);
                }
            }
        }

        Integer parentId = brandDto.getParentId();
        if(parentId != null){
            Brand parentBrand = brandRepository.findById(parentId).orElse(null);
            if(parentBrand != null){
                if(parentBrand.getParentId() != null){
                    Brand grandParentBrand = brandRepository.findById(parentBrand.getParentId()).orElse(null);
                    if(grandParentBrand.getParentId() != null){
                        boolean existed = brandRepository.existsById(grandParentBrand.getParentId());
                        if(existed){
                            errors.put("CAN_NOT_CREATE_CHILD", CAN_NOT_CREATE_CHILD);
                        }
                    }
                }
            } else if(editMode) {
                errors.put("CAN_NOT_FIND_PARENT", CAN_NOT_FIND_PARENT);
            }
        }
        return errors;
    }

    @Override
    public ResultBuilder create(BrandDto brandDto) {
        ResultBuilder result = ResultBuilder.build();
        HashMap<String, String> errors = validate(brandDto, false);
        if(errors.size() > 0) return result.success(false).errors(errors);

        String slug = CustomStringUtils.genSlug(brandDto.getName());
        Brand brand = modelMapper.map(brandDto, Brand.class);

        Brand found = brandRepository.findBySlug(slug);
        if(found != null){
            slug = slug + '-' + new Date().getTime();
        }
        brand.setSlug(slug);


        if(brandDto.getParentId() != null){
            boolean hasParent = brandRepository.findById(brandDto.getParentId()).isPresent();
            if(!hasParent) brand.setParentId(null);
        }

        brand = brandRepository.save(brand);
        return result.success(true).data(brand);
    }

    @Override
    public ResultBuilder update(BrandDto brandDto) {
        ResultBuilder result = ResultBuilder.build();
        HashMap<String, String> errors = validate(brandDto, true);
        if(errors.size() > 0) return result.success(false).errors(errors);

        Brand newBrand = modelMapper.map(brandDto, Brand.class);
        if(brandDto.getParentId() == null){
            newBrand.setParentId(null);
        } else {
            Brand parentBrand = brandRepository.findById(brandDto.getParentId()).orElse(null);
            if(parentBrand == null) {
                newBrand.setParentId(null);
            } else if(parentBrand.getId() == newBrand.getId()){
                return result.success(false).errors("SELF_PARENT", SELF_PARENT);
            }
        }

        String slug = CustomStringUtils.genSlug(brandDto.getName());
        Brand found = brandRepository.findBySlug(slug);
        if(found != null && found.getId() != brandDto.getId()){
            slug = slug + '-' + new Date().getTime();
        }
        newBrand.setSlug(slug);
        brandRepository.save(newBrand);

        return result.success(true).data(newBrand);
    }

    @Override
    public ResultBuilder delete(List<Integer> ids) {
        brandRepository.inactiveByIds(ids);
        return ResultBuilder.build().success(true).data(ids);
    }

    @Override
    public Brand getById(int id) {
        return brandRepository.findById(id).orElse(null);
    }

    @Override
    public Brand getBySlug(String slug) {
        return brandRepository.findBySlug(slug);
    }

    @Override
    public List<Brand> getAllActive() {
        return brandRepository.findAllActive();
    }

    @Override
    public List<Integer> getChildIds(int id) {
        List<Integer> idList = new ArrayList<>();
        boolean found = brandRepository.existsById(id);
        if(!found) return idList;

        List<Brand> brands = brandRepository.findAllActive();
        Map<Integer, Node> tree = TreeUtils.convertBrandListToTree(brands);
        Node node = tree.get(id);
        idList = TreeUtils.getChidrenListIdFromNode(node);
        idList.add(node.getId());
        return idList;
    }

    @Override
    public Map<Integer, Node> getActiveTree() {
        List<Brand> brands = brandRepository.findAllActive();
        Map<Integer, Node> brandTree = TreeUtils.convertBrandListToTree(brands);
        for (Brand brand : brands) {
            if(brand.getParentId() != null){
                brandTree.remove(brand.getId());
            }
        }
        return brandTree;
    }


}
