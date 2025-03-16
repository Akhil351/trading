package com.akhil.trading.model;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Data
public class UserContext {
    private Long id;
    private String fullName;
    private String email;
    private Collection<? extends GrantedAuthority> authorities;
}
