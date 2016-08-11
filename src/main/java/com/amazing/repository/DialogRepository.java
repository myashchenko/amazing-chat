package com.amazing.repository;

import com.amazing.entity.Dialog;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

/**
 * @author Nikolay Yashchenko
 */
public interface DialogRepository extends MongoRepository<Dialog, Long> {
    @Query(value = "{ 'unames' : ?0 }")
    List<Dialog> findByUsername(String username);
}
