package com.amazing.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;

/**
 * @author Nikolay Yashchenko
 */
@Getter
@Setter
public class User {
    @Id
    private Long id;
    @Indexed(unique = true)
    private String username;
    private String name;
    private String password;
    private Role role = Role.ROLE_USER;
}
