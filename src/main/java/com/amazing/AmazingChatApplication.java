package com.amazing;

import com.amazing.config.SecurityConfig;
import com.amazing.config.WebSocketConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories
@Import({SecurityConfig.class, WebSocketConfig.class})
public class AmazingChatApplication {

    public static void main(String[] args) {
        SpringApplication.run(AmazingChatApplication.class, args);
    }
}
