package org.example.sbgroup2.dto;

import lombok.Getter;
import lombok.Setter;
import org.example.sbgroup2.enums.Role;

@Getter
@Setter
public class SignupRequest {
    private String email;
    private String password;
    private Role role; // ADMIN or CUSTOMER (optional)
}

