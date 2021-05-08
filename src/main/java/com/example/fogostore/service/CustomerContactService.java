package com.example.fogostore.service;

import com.example.fogostore.builder.ResultBuilder;
import com.example.fogostore.common.utils.FileUtils;
import com.example.fogostore.dto.CustomerContactDto;
import com.example.fogostore.form.CustomerContactForm;
import com.example.fogostore.model.CustomerContact;
import com.example.fogostore.repository.CustomerContactRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

public interface CustomerContactService {
    CustomerContact getById(Integer id);

    CustomerContactDto toDto(CustomerContact customerContact);

    ResultBuilder create(CustomerContactForm customerContactForm);

    ResultBuilder update(CustomerContactForm customerContactForm);

    ResultBuilder delete(Integer id);

    Page<CustomerContact> search(String searchValue, int page, int size);

}

@Service
class CustomerContactServiceImpl implements CustomerContactService {


    @Autowired
    CustomerContactRepository customerContactRepository;

    @Autowired
    FileUtils fileUtils;

    @Autowired
    ModelMapper modelMapper;


    @Override
    public CustomerContact getById(Integer id) {
        return null;
    }

    @Override
    public CustomerContactDto toDto(CustomerContact customerContact) {
        return null;
    }

    @Override
    public ResultBuilder create(CustomerContactForm customerContactForm) {
        return null;
    }

    @Override
    public ResultBuilder update(CustomerContactForm customerContactForm) {
        return null;
    }

    @Override
    public ResultBuilder delete(Integer id) {
        return null;
    }

    @Override
    public Page<CustomerContact> search(String searchValue, int page, int size) {
        return null;
    }
}
