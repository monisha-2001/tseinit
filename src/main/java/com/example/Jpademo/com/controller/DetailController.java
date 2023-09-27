package com.example.Jpademo.com.controller;

import com.example.Jpademo.com.model.Detail;
import com.example.Jpademo.com.service.DetailService;
import com.example.Jpademo.com.zipformat.ProjectGenerationService;

import jakarta.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;


@RestController
@RequestMapping("/api")
public class DetailController {
    private final DetailService detailService;
    private final ProjectGenerationService projectGenerationService;
    private final MongoTemplate mongoTemplate;

    @Autowired
    public DetailController(DetailService detailService,ProjectGenerationService projectGenerationService,MongoTemplate mongoTemplate) {
        this.detailService = detailService;
        this.projectGenerationService = projectGenerationService;
        this.mongoTemplate = mongoTemplate;
    }

    @PostMapping("/generate")
    public ResponseEntity<String> submitDetail(@RequestBody Detail detail,HttpServletResponse response) throws IOException{
       try{

           Detail details = detailService.createDetail(detail);

           projectGenerationService.generateProject(details, response);

           // Delete the previous data
           mongoTemplate.dropCollection(Detail.class);

           // Insert the new data
           mongoTemplate.insert(details);

           System.out.println(details);
           return ResponseEntity.status(HttpStatus.CREATED).body("Data saved successfully.");
       }catch (Exception e) {
           // Handle other exceptions and return a 500 Internal Server Error response
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred.");
       }
    }

    @GetMapping("/details")
    public ResponseEntity<List<Detail>> getAllDetails() {
        List<Detail> details = detailService.getAllDetails();
        return ResponseEntity.ok(details);
        // Detail latestDetails = mongoTemplate.findOne(Query.query(Criteria.where("_id").exists(true)).limit(1), Detail.class);
        // return ResponseEntity.ok(latestDetails);
    }
}
