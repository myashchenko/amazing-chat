package com.amazing.validator;

import com.amazing.entity.User;
import com.amazing.repository.UserRepository;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * @author Nikolay Yashchenko
 */
public class UserValidator implements Validator {

    private static final int MIN_USERNAME_SIZE = 4;
    private static final int MIN_PASSWORD_SIZE = 5;

    private final UserRepository userRepository;

    public UserValidator(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return User.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        User user = (User) o;
        if (StringUtils.isEmpty(user.getUsername()) || user.getUsername().length() < MIN_USERNAME_SIZE) {
            errors.rejectValue("username", "username is incorrect");
        } else if (userRepository.findByUsername(user.getUsername()) != null) {
            errors.rejectValue("username", "user already exists");
        }
        if (StringUtils.isEmpty(user.getPassword()) || user.getPassword().length() < MIN_PASSWORD_SIZE) {
            errors.rejectValue("password", "password is incorrect");
        }
    }
}
