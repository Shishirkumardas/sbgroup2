package org.example.sbgroup2.models;

import jakarta.persistence.*;
import lombok.Data;
import org.example.sbgroup2.enums.Role;


@Entity
@Table(name = "users")
@Data
public class User {
    @Id
    private String id;

    private String email;
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role; // ADMIN or CUSTOMER
}

