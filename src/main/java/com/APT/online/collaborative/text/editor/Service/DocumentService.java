package com.APT.online.collaborative.text.editor.Service;

import com.APT.online.collaborative.text.editor.Model.Document;
import com.APT.online.collaborative.text.editor.Model.UserDocument;
import com.APT.online.collaborative.text.editor.Model.UserEntity;
import com.APT.online.collaborative.text.editor.Permission;
import com.APT.online.collaborative.text.editor.Repository.DocumentRepository;
import com.APT.online.collaborative.text.editor.Repository.UserDocumentRepository;
import com.APT.online.collaborative.text.editor.Repository.UserRepository;
import com.APT.online.collaborative.text.editor.Utils.DocumentUtils;
import com.APT.online.collaborative.text.editor.Exception.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.APT.online.collaborative.text.editor.dto.DocumentDTO;

import java.io.FileNotFoundException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserDocumentRepository userDocumentRepository;

    public Document createDocument(String documentName, String username) {
        DocumentUtils.validateDocumentName(documentName);

        try {
            UserEntity user = userRepository.findUserByUsername(username).orElse(null);
            if (user == null) {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }

            Document document = new Document(documentName, "text/plain", new byte[0], LocalDateTime.now(), LocalDateTime.now());
            Document savedDocument = documentRepository.save(document);

            UserDocument userDocument = new UserDocument();
            userDocument.setUser(user);
            userDocument.setDocument(savedDocument);
            userDocument.setPermission(Permission.OWNER);
            userDocumentRepository.save(userDocument);

            return savedDocument;
        } catch (Exception ex) {
            throw new FileStorageException("Could not store document " + documentName + ". Please try again!", ex);
        }
    }

    public Document renameDocument(String documentId, String newDocumentName, String username) throws FileNotFoundException, IllegalAccessException {
        UserDocument userDocument = userDocumentRepository.findUserDocumentByUsernameAndDocumentId(username, documentId)
                .orElseThrow(() -> new FileNotFoundException("No document found with id: " + documentId + " for user: " + username));

        if (!(userDocument.getPermission() == Permission.OWNER || userDocument.getPermission() == Permission.EDITOR)) {
            throw new IllegalAccessException("User does not have permission to rename the document");
        }

        Document document = userDocument.getDocument();
        DocumentUtils.validateDocumentName(newDocumentName);
        document.setDocumentName(newDocumentName);
        document.setLastModifiedAt(LocalDateTime.now());
        return documentRepository.save(document);
    }

    public void deleteDocument(String documentId, String username) throws FileNotFoundException, IllegalAccessException {
        UserDocument userDocument = userDocumentRepository.findUserDocumentByUsernameAndDocumentId(username, documentId)
                .orElseThrow(() -> new FileNotFoundException("No document found with id: " + documentId + " for user: " + username));

        if (userDocument.getPermission() != Permission.OWNER) {
            throw new IllegalAccessException("User does not have permission to delete the document");
        }

        userDocumentRepository.deleteByDocumentId(documentId);
        documentRepository.deleteById(documentId);
    }

    public List<DocumentDTO> listOwnerDocuments(String username) {
        List<UserDocument> ownerDocuments = getUserDocumentsByPermission(username, Permission.OWNER);
        return DocumentUtils.convertToDTO(ownerDocuments);
    }

    public List<DocumentDTO> listSharedWithMeDocuments(String username) {
        List<UserDocument> viewerDocuments = getUserDocumentsByPermission(username, Permission.VIEWER);
        List<UserDocument> editorDocuments = getUserDocumentsByPermission(username, Permission.EDITOR);
        viewerDocuments.addAll(editorDocuments);
        return DocumentUtils.convertToDTO(viewerDocuments);
    }

    public void shareDocument(String documentId, String username, String permission, String owner) throws FileNotFoundException, IllegalAccessException {
        UserEntity user = userRepository.findUserByUsername(username).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }

        UserDocument userDocument = userDocumentRepository.findUserDocumentByUsernameAndDocumentId(owner, documentId)
                .orElseThrow(() -> new FileNotFoundException("No document found with id: " + documentId + " for user: " + owner));

        if (userDocument.getPermission() != Permission.OWNER) {
            throw new IllegalAccessException("User does not have permission to share the document");
        }

        UserDocument newSharedUserDocument = new UserDocument();
        newSharedUserDocument.setUser(user);
        newSharedUserDocument.setDocument(userDocument.getDocument());
        newSharedUserDocument.setPermission(Permission.valueOf(permission));
        userDocumentRepository.save(newSharedUserDocument);
    }

    // Helper Function
    private List<UserDocument> getUserDocumentsByPermission(String username, Permission permission) {
        UserEntity user = userRepository.findUserByUsername(username).orElse(null);
        if (user == null) {
            throw new UsernameNotFoundException("User not found with username: " + username);
        }
        return userDocumentRepository.findUserDocumentsByUsernameAndPermission(username, permission);
    }
}