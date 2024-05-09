package com.APT.online.collaborative.text.editor.Model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.APT.online.collaborative.text.editor.Permission;

@Entity
@Table(name = "user_documents")
@Getter
@Setter
@NoArgsConstructor
public class UserDocument {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "document_id")
    private Document document;

    // other fields like permissions, last access time, etc.
    private Permission permission;
}