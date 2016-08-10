package com.amazing.web;

import com.amazing.entity.Message;
import com.amazing.repository.MessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * @author Nikolay Yashchenko
 */
@Controller
@Secured({"ROLE_ADMIN", "ROLE_USER"})
public class ChatController {

    private final MessageRepository messageRepository;

    @Autowired
    public ChatController(MessageRepository messageRepository) {
        this.messageRepository = messageRepository;
    }

    @MessageMapping("/api/chat/messages")
    @SendTo("/chat/messages")
    public List<Message> getByDialogId(Long dialogId) {
        return messageRepository.findByDialogId(dialogId);
    }
}
