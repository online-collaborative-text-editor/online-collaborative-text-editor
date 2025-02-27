package com.APT.online.collaborative.text.editor.dto;

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
    private String permission;
    private String owner;
    private List<String> contributors;
}