package com.akhil.trading.config;

import com.akhil.trading.model.UserContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {
    @Bean
    public UserContext userContext(){
        return new UserContext();
    }
}
