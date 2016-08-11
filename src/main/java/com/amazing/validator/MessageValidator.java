package com.amazing.validator;

import com.amazing.entity.Message;
import com.amazing.repository.DialogRepository;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Nikolay Yashchenko
 */
public class MessageValidator implements Validator {

    private final DialogRepository dialogRepository;

    public MessageValidator(DialogRepository dialogRepository) {
        this.dialogRepository = dialogRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return Message.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        Message message = (Message) o;

        if (message.getText() == null || message.getText().isEmpty()) {
            errors.rejectValue("text", "text can't be empty");
        }
    }
}
