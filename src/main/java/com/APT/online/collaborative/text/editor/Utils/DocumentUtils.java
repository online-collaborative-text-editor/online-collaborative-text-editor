package com.APT.online.collaborative.text.editor.Utils;

import com.APT.online.collaborative.text.editor.Exception.FileStorageException;
import com.APT.online.collaborative.text.editor.Model.Document;
import com.APT.online.collaborative.text.editor.Model.UserDocument;
import com.APT.online.collaborative.text.editor.Permission;
import com.APT.online.collaborative.text.editor.Repository.UserDocumentRepository;
import com.APT.online.collaborative.text.editor.dto.DocumentDTO;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class DocumentUtils {

    public static void validateDocumentName(String documentName) {
        String cleanedDocumentName = StringUtils.cleanPath(documentName);
        if (cleanedDocumentName.contains("..")) {
            throw new FileStorageException("Sorry! Document Name contains invalid path sequence " + cleanedDocumentName);
        }
    }

    public static List<DocumentDTO> convertToDTO(List<UserDocument> userDocuments, UserDocumentRepository userDocumentRepository) {
        List<DocumentDTO> documentDTOs = new ArrayList<>();
        for (UserDocument userDocument : userDocuments) {
            Document document = userDocument.getDocument();

            // Get the owner of the document
            String owner = userDocumentRepository.findUserDocumentByDocumentAndPermission(document, Permission.OWNER)
                    .map(userDoc -> userDoc.getUser().getUsername())
                    .orElse(null);

            // Get the list of all contributors
            List<String> contributors = userDocumentRepository.findUserDocumentsByDocumentId(document.getId())
                    .stream()
                    .map(userDoc -> userDoc.getUser().getUsername())
                    .collect(Collectors.toList());

            DocumentDTO documentDTO = new DocumentDTO(
                    document.getId(),
                    document.getDocumentName(),
                    document.getCreatedAt(),
                    document.getLastModifiedAt(),
                    userDocument.getPermission().toString(),
                    owner,
                    contributors
            );
            documentDTOs.add(documentDTO);
        }

        // Sort the documentDTOs list by lastModifiedAt attribute
        Collections.sort(documentDTOs, Comparator.comparing(DocumentDTO::getLastModifiedAt).reversed());

        return documentDTOs;
    }
}