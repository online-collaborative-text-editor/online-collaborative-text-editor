package com.APT.online.collaborative.text.editor.Utils;

import com.APT.online.collaborative.text.editor.Exception.FileStorageException;
import com.APT.online.collaborative.text.editor.Model.Document;
import org.springframework.util.StringUtils;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.FileNotFoundException;

public class DocumentUtils {

    public static void validateDocumentName(String documentName) {
        String cleanedDocumentName = StringUtils.cleanPath(documentName);
        if (cleanedDocumentName.contains("..")) {
            throw new FileStorageException("Sorry! Document Name contains invalid path sequence " + cleanedDocumentName);
        }
    }

    public static Document getDocumentById(JpaRepository<Document, String> repository, String documentId) throws FileNotFoundException {
        return repository.findById(documentId)
                .orElseThrow(() -> new FileNotFoundException("Document not found with id " + documentId));
    }
}