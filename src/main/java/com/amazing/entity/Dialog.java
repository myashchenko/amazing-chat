package com.amazing.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.List;

/**
 * @author Nikolay Yashchenko
 */
@Getter
@Setter
public class Dialog {
    @Id
    private Long id;
    private List<User> users;
    private String title;
}
