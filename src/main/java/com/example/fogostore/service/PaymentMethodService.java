package com.example.fogostore.service;

import com.example.fogostore.model.*;
import com.example.fogostore.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

public interface PaymentMethodService {
    List<PaymentMethod> getAll();
}

@Service
class PaymentMethodServiceImpl implements PaymentMethodService {

    @Autowired
    PaymentMethodRepository paymentMethodRepository;


    @Override
    public List<PaymentMethod> getAll() {
        return paymentMethodRepository.findAll();
    }
}
