package com.hrsupportcentresq014.repositories;

import com.hrsupportcentresq014.entities.Award;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.time.Year;
import java.util.List;

@Repository
public interface AwardRepository extends MongoRepository<Award, String> {
    // Add custom repository methods if needed
    List<Award> findByYear(Year year);

    List<Award> findByYear(int year);

}