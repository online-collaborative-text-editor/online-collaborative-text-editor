package com.APT.online.collaborative.text.editor.Service;

import com.APT.online.collaborative.text.editor.Model.Document;
import com.APT.online.collaborative.text.editor.Repository.DocumentRepository;
import com.APT.online.collaborative.text.editor.Utils.DocumentUtils;
import com.APT.online.collaborative.text.editor.Exception.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;

@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;

    public Document createDocument(String documentName){
        DocumentUtils.validateDocumentName(documentName);

        try {
            Document document = new Document(documentName, "text/plain", new byte[0], LocalDateTime.now(), LocalDateTime.now());
            return documentRepository.save(document);
        } catch (Exception ex) {
            throw new FileStorageException("Could not store document " + documentName + ". Please try again!", ex);
        }
    }

    public Document openDocument(String documentId) throws FileNotFoundException {
        return DocumentUtils.getDocumentById(documentRepository, documentId);
    }

    public Document renameDocument(String documentId, String newDocumentName) throws FileNotFoundException {
        Document document = DocumentUtils.getDocumentById(documentRepository, documentId);
        DocumentUtils.validateDocumentName(newDocumentName);
        document.setDocumentName(newDocumentName);
        document.setLastModifiedAt(LocalDateTime.now());
        return documentRepository.save(document);
    }

    public void deleteDocument(String documentId) throws FileNotFoundException {
        Document document = DocumentUtils.getDocumentById(documentRepository, documentId);
        documentRepository.deleteById(documentId);
    }
}