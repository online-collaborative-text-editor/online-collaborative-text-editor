package com.APT.online.collaborative.text.editor.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class DocumentDTO {
    private String id;
    private String documentName;
    private LocalDateTime createdAt;
    private LocalDateTime lastModifiedAt;
    private String permission;
}