package com.example.Jpademo.com.service;



import com.example.Jpademo.com.model.Detail;
import com.example.Jpademo.com.repository.DetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DetailService {

    private final DetailRepository detailRepository;


    @Autowired
    public DetailService(DetailRepository detailRepository) {
        this.detailRepository = detailRepository;
    }

    public Detail createDetail(Detail detail) {
        return detailRepository.save(detail);
    }

    public List<Detail> getAllDetails() {
        return detailRepository.findAll();
    }
}
