package com.amazing.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

/**
 * @author Nikolay Yashchenko
 */
@Getter
@Setter
public class Message {
    @Id
    private Long id;
    private String text;
    private Long dialogId;
}
