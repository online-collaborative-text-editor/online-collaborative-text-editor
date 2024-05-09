package com.APT.online.collaborative.text.editor.dto;

import com.APT.online.collaborative.text.editor.Model.UserDocument;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DocumentDTO {
    private String id;
    private String documentName;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;

    public DocumentDTO(String id, String documentName, String documentType, LocalDateTime createdAt, LocalDateTime lastModifiedAt, List<UserDocument> userDocuments) {
        this.id = id;
        this.documentName = documentName;
        this.createdAt = createdAt;
        this.lastModifiedAt = lastModifiedAt;
    }
}