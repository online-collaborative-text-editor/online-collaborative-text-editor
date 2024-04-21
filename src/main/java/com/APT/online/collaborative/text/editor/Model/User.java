package com.APT.online.collaborative.text.editor.Model;

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
    private Long id;
    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}
