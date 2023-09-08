package com.example.Jpademo.com.repository;

import com.example.Jpademo.com.model.Detail;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DetailRepository extends MongoRepository<Detail, ObjectId> {




}
