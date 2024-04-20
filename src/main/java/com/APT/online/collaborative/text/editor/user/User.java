package com.APT.online.collaborative.text.editor.user;

import lombok.*;
import jakarta.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
