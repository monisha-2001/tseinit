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

//    public boolean isValid(Detail detail) {
//        return detail.getGroupId() != null && !detail.getGroupId().isEmpty() &&
//                detail.getArtifactId() != null && !detail.getArtifactId().isEmpty() &&
//                detail.getName() != null && !detail.getName().isEmpty() &&
//                detail.getDescription() != null && !detail.getDescription().isEmpty() &&
//                detail.getPackageName() != null && !detail.getPackageName().isEmpty() &&
//                detail.getBuildType() != null && !detail.getBuildType().isEmpty() &&
//                detail.getLanguage() != null && !detail.getLanguage().isEmpty() &&
//                detail.getSpringBootVersion() != null && !detail.getSpringBootVersion().isEmpty() &&
//                detail.getPackaging() != null && !detail.getPackaging().isEmpty() &&
//                detail.getJavaVersion() != null && !detail.getJavaVersion().isEmpty() &&
//                detail.getDependencies() != null && !detail.getDependencies().isEmpty() &&
//                detail.getCustomDependencies() != null && !detail.getCustomDependencies().isEmpty();
//    }

    public List<Detail> getAllDetails() {
        return detailRepository.findAll();
    }
}
