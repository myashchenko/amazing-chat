package com.amazing.repository;

import com.amazing.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author Nikolay Yashchenko
 */
public interface UserRepository extends MongoRepository<User, Long> {
    User findByUsername(String username);
}
