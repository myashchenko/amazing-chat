package com.amazing.entity;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Calendar;

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
    private String username;
    private Calendar date;
}
