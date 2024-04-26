package com.APT.online.collaborative.text.editor.Model;

import com.APT.online.collaborative.text.editor.Model.Document;
import com.APT.online.collaborative.text.editor.Model.UserEntity;
import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

@Entity
@Table(name = "user-document")
public class UserDocument {
    @Id
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    private String id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "document_id", nullable = false)
    private Document document;

    @Enumerated(EnumType.STRING)
    private Permission permission;
}