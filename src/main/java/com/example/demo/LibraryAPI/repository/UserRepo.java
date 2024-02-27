package com.example.demo.LibraryAPI.repository;

import com.example.demo.LibraryAPI.models.UserUpdated;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepo extends MongoRepository<UserUpdated, UUID> {
    @Query("{'fname':{'$regex':'(?i)?0'}}")
    List<UserUpdated> findByName(String fname);

    @Query("{'subscription' : {'$regex' : '(?i)?0'}}")
    List<UserUpdated> findBySub(String subscription);

    @Query("{'$or': [{'mobile': ?0}, {'email': ?1}]}")
    List<UserUpdated> findByMobileAndEmail(String mobile, String email);

}

