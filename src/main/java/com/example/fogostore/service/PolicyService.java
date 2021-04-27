package com.example.fogostore.service;

import com.example.fogostore.common.utils.CustomStringUtils;
import com.example.fogostore.builder.ResultBuilder;
import com.example.fogostore.dto.policy.BasicPolicy;
import com.example.fogostore.model.Policy;
import com.example.fogostore.repository.PolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.Date;
import java.util.List;

public interface PolicyService {
    ResultBuilder create(Policy policy);

    ResultBuilder update(Policy policy);

    ResultBuilder deleteById(Integer id);

    List<BasicPolicy> getAllActive();

    Policy getById(Integer id);

    Policy getBySlug(String slug);
}

@Service
class PolicyServiceImpl implements PolicyService {

    @Autowired
    PolicyRepository policyRepository;

//    @Autowired
//    EntityManager entityManager;

    private final String NOT_FOUND = "không tìm thấy chính sách này!";


    @Override
    public ResultBuilder create(Policy policy) {
        String slug = CustomStringUtils.genSlug(policy.getName());
        if (policyRepository.existsBySlug(slug)) {
            slug += "-" + new Date().getTime();
        }
        policy.setSlug(slug);
        policy.setActive(true);
        policy = policyRepository.save(policy);

        return ResultBuilder.build().data(policy).success(true);
    }

    @Override
    public ResultBuilder update(Policy policy) {
        ResultBuilder result = ResultBuilder.build();
        if (policy.getId() == null) {
            return result.errors("NOTFOUND", NOT_FOUND).success(false);
        }
        Policy found = policyRepository.findById(policy.getId()).orElse(null);
        if (found == null || !found.isActive()) {
            return result.errors("NOTFOUND", NOT_FOUND).success(false);
        }
        String slug = CustomStringUtils.genSlug(policy.getName());
        Policy foundBySlug = policyRepository.findBySlugEquals(slug);
        if (foundBySlug != null && foundBySlug.getId() != policy.getId()) {
            slug += "-" + new Date().getTime();
        }
        policy.setSlug(slug);
        policy = policyRepository.save(policy);


        return result.data(policy).success(true);
    }

    @Override
    public List<BasicPolicy> getAllActive() {
        return policyRepository.findAllActive();
    }

//    public List<Policy> getBasicActiveEntityList(String slug){
//        Query query = entityManager.createNativeQuery("select id, name, type, slug, active from policy where active = true");
//        return (List<Policy>) query.getResultList();
//    }

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
            result.errors("NOTFOUND", NOT_FOUND).success(false);
        } else {
            found.setActive(false);
            policyRepository.save(found);
        }
        result.data(found).success(true);
        return result;
    }
}
