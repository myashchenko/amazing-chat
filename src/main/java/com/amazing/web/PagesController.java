package com.amazing.web;

import com.amazing.CounterService;
import com.amazing.entity.User;
import com.amazing.repository.UserRepository;
import com.amazing.validator.UserValidator;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.ServletException;
import java.util.Collections;

/**
 * @author Nikolay Yashchenko
 */
@Controller
public class PagesController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CounterService counterService;

    @Autowired
    public PagesController(UserRepository userRepository, PasswordEncoder passwordEncoder,
                           CounterService counterService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.counterService = counterService;
    }

    @InitBinder
    public void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.addValidators(new UserValidator());
    }

    @GetMapping(value = "/chat")
    public String chat() {
        return "chat";
    }

    @GetMapping(value = "/")
    public String index() {
        return "index";
    }

    @PostMapping(value = "/users")
    public String createUser(@Validated @ModelAttribute User user, BindingResult bindingResult,
                             HttpServletRequest request) throws ServletException {
        if (bindingResult.hasErrors()) {
            return "redirect:/";
        }
        String originalPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(originalPassword));
        user.setId(counterService.getNextSequence("user"));
        userRepository.save(user);
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(user.getUsername(), null,
                        Collections.singletonList(new SimpleGrantedAuthority(user.getRole().name()))));
        return "redirect:/chat";
    }
}
