package com.akhil.trading.model;

import com.akhil.trading.domain.USER_ROLE;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name="users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fullName;
    private String email;
    @JsonProperty(access =JsonProperty.Access.WRITE_ONLY)
    private String password;
    private String mobile;

    @Embedded
    private TwoFactorAuth twoFactorAuth=new TwoFactorAuth();

    private USER_ROLE role=USER_ROLE.ROLE_CUSTOMER;

}
