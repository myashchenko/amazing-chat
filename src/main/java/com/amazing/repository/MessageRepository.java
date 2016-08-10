package com.amazing.repository;

import com.amazing.entity.Message;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

/**
 * @author Nikolay Yashchenko
 */
public interface MessageRepository extends MongoRepository<Message, Long> {
    List<Message> findByDialogId(Long dialogId);
}
