package com.example.fogostore.service;


import com.example.fogostore.model.View;
import com.example.fogostore.repository.ViewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

public interface ViewService {
    void create();
    List<View> getViewsPerDayInMonth(String monthYearStr);
}

@Service
class ViewServiceImpl implements ViewService{

    @Autowired
    ViewRepository viewRepository;

    @Override
    public void create() {
        Date now = new Date();
        View found = viewRepository.findByCreatedAt(now);
        if(found != null){
            found.setViews(found.getViews() + 1);
            viewRepository.save(found);
        } else {
            View view = new View();
            view.setViews(1);
            viewRepository.save(view);
        }
    }

    @Override
    public List<View> getViewsPerDayInMonth(String monthYearStr) {
        return viewRepository.findByMonthYear(monthYearStr);
    }
}
