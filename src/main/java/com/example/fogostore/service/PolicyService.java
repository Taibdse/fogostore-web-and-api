package com.example.fogostore.service;

import com.example.fogostore.common.utils.CustomStringUtils;
import com.example.fogostore.builder.ResultBuilder;
import com.example.fogostore.dto.policy.BasicPolicy;
import com.example.fogostore.model.Category;
import com.example.fogostore.model.Policy;
import com.example.fogostore.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface PolicyService {
    ResultBuilder create(Policy policy);

    ResultBuilder update(Policy policy);

    ResultBuilder deleteById(Integer id);

    List<BasicPolicy> getAllActive();

    Policy getById(Integer id);

    Policy getBySlug(String slug);

    String getSlugById(Integer id);
}

@Service
class PolicyServiceImpl implements PolicyService {

    @Autowired
    PolicyRepository policyRepository;

    @Autowired
    SharedService sharedService;

    private final String NOT_FOUND = "không tìm thấy chính sách này!";
    private final String SLUG_EXISTED = "Slug đã tồn tại!";

    @Override
    public String getSlugById(Integer id) {
        return policyRepository.findSlugById(id);
    }

    public HashMap<String, String> validate(Policy policy, Boolean editMode) {
        HashMap<String, String> errors = new HashMap<>();

        Policy foundBySlug = policyRepository.findBySlugEquals(policy.getSlug());
        if (editMode) {
            if (policy.getId() == null) {
                errors.put("NOTFOUND", NOT_FOUND);
            } else {
                boolean existed = policyRepository.existsById(policy.getId());
                if (!existed) {
                    errors.put("NOTFOUND", NOT_FOUND);
                } else if (foundBySlug != null && !foundBySlug.getId().equals(policy.getId())) {
                    errors.put("SLUG_EXISTED", SLUG_EXISTED);
                }
            }
        } else {
            if (foundBySlug != null) {
                errors.put("SLUG_EXISTED", SLUG_EXISTED);
            }
        }
        return errors;
    }

    @Override
    public ResultBuilder create(Policy policy) {
        ResultBuilder result = new ResultBuilder();
        HashMap<String, String> errors = validate(policy, false);
        if (errors.size() > 0) {
            return result.success(false).errors(errors);
        }

        policy.setActive(true);
        policy.setContent(sharedService.formatEditorContent(policy.getContent()));
        policy = policyRepository.save(policy);

        return result.data(policy).success(true);
    }

    @Override
    public ResultBuilder update(Policy policy) {
        ResultBuilder result = new ResultBuilder();
        HashMap<String, String> errors = validate(policy, true);
        if (errors.size() > 0) {
            return result.success(false).errors(errors);
        }

        policy.setContent(sharedService.formatEditorContent(policy.getContent()));
        policy = policyRepository.save(policy);

        return result.data(policy).success(true);
    }

    @Override
    public List<BasicPolicy> getAllActive() {
        return policyRepository.findAllActive();
    }


    @Override
    public Policy getById(Integer id) {
        return policyRepository.findById(id).orElse(null);
    }

    @Override
    public Policy getBySlug(String slug) {
        return policyRepository.findBySlugEquals(slug);
    }

    @Override
    public ResultBuilder deleteById(Integer id) {
        ResultBuilder result = ResultBuilder.build();
        Policy found = policyRepository.findById(id).orElse(null);
        if (found == null || !found.isActive()) {
            return result.errors("NOTFOUND", NOT_FOUND).success(false);
        } else {
            found.setActive(false);
            policyRepository.save(found);
        }
        return result.data(found).success(true);
    }
}
