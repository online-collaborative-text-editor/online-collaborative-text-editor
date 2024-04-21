package com.APT.online.collaborative.text.editor.Model;

import com.APT.online.collaborative.text.editor.Permission;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

import jakarta.persistence.*;

@Entity
@Table(name = "user_document")
public class UserDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @Enumerated(EnumType.STRING)
    @Column(name = "permission", nullable = false)
    private Permission permission;

    // getters and setters
}
