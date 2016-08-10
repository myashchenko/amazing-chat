package com.amazing.repository;

import com.amazing.entity.Dialog;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Nikolay Yashchenko
 */
public interface DialogRepository extends MongoRepository<Dialog, Long> {
}
