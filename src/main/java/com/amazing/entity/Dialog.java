package com.amazing.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Set;

/**
 * @author Nikolay Yashchenko
 */
@Getter
@Setter
public class Dialog {
    @Id
    private Long id;
    private Set<String> unames;
    private String title;
}
