package com.APT.online.collaborative.text.editor.Model;

import com.APT.online.collaborative.text.editor.Permission;

import lombok.*;
import jakarta.persistence.*;
import org.hibernate.annotations.GenericGenerator;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@ToString

@Entity
@Table(name = "user_document")
public class UserDocument {
    @EmbeddedId
    private UserDocumentId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id")
    private UserEntity zzzzzzzzzzzzzzzzzzzzzzzzzzzz;

    @ManyToOne
    @MapsId("documentId")
    @JoinColumn(name = "document_id")
    private Document document;

    private String permission; // Viewer - Editor - Owner

    // getters and setters
}