package com.amazing.web;

import com.amazing.entity.Dialog;
import com.amazing.entity.Message;
import com.amazing.repository.DialogRepository;
import com.amazing.repository.MessageRepository;
import com.amazing.validator.MessageValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.security.Principal;
import java.util.Calendar;
import java.util.List;

/**
 * @author Nikolay Yashchenko
 */
@Controller
public class ChatController {

    private final MessageRepository messageRepository;
    private final DialogRepository dialogRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public ChatController(MessageRepository messageRepository, DialogRepository dialogRepository,
                          SimpMessagingTemplate messagingTemplate) {
        this.messageRepository = messageRepository;
        this.dialogRepository = dialogRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new MessageValidator(dialogRepository));
    }

    @MessageMapping("/api/chat/dialogs")
    public void getByUserId(Principal principal) {
        List<Dialog> dialogs = dialogRepository.findByUsername(principal.getName());
        messagingTemplate.convertAndSendToUser(principal.getName(), "/chat/dialogs", dialogs);
    }

    @MessageMapping("/api/chat/messages")
    public void getByDialogId(Long dialogId, Principal principal) {
        Dialog dialog = dialogRepository.findOne(dialogId);
        if (dialog != null && dialog.getUnames().contains(principal.getName())) {
            List<Message> messages = messageRepository.findByDialogId(dialogId);
            messagingTemplate.convertAndSendToUser(principal.getName(), "/chat/messages", messages);
        }
    }

    @MessageMapping("/api/chat/message")
    public void sendMessage(@Validated Message message, BindingResult bindingResult, Principal principal) {
        if (!bindingResult.hasErrors() && message.getDialogId() != null) {
            Dialog dialog = dialogRepository.findOne(message.getDialogId());
            if (dialog.getUnames().contains(principal.getName())) {
                message.setDate(Calendar.getInstance());
                message.setUsername(principal.getName());
                message = messageRepository.save(message);
                for (String username : dialog.getUnames()) {
                    messagingTemplate.convertAndSendToUser(username, "/chat/" + dialog.getId() + "/message", message);
                }
            }
        }
    }
}
