package com.APT.online.collaborative.text.editor.DTO;

import com.APT.online.collaborative.text.editor.Model.UserDocument;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
public class DocumentDTO {
    private String id;
    private String documentName;
    private String documentType;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private List<UserDocument> userDocuments;

    public DocumentDTO(String id, String documentName, String documentType, LocalDateTime createdAt, LocalDateTime lastModifiedAt, List<UserDocument> userDocuments) {
        this.id = id;
        this.documentName = documentName;
        this.documentType = documentType;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
        this.userDocuments = userDocuments;
    }
}