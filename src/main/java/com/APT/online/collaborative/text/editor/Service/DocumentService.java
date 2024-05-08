package com.APT.online.collaborative.text.editor.Service;

import com.APT.online.collaborative.text.editor.Model.Document;
import com.APT.online.collaborative.text.editor.Model.UserEntity;
import com.APT.online.collaborative.text.editor.Permission;
import com.APT.online.collaborative.text.editor.Repository.DocumentRepository;
import com.APT.online.collaborative.text.editor.Repository.UserRepository;
import com.APT.online.collaborative.text.editor.Utils.DocumentUtils;
import com.APT.online.collaborative.text.editor.Exception.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.io.FileNotFoundException;
import java.time.LocalDateTime;

@Service
public class DocumentService {
    @Autowired
    private DocumentRepository documentRepository;
    @Autowired
    private UserRepository userRepository;

    public Document createDocument(String documentName, String username){
        DocumentUtils.validateDocumentName(documentName);

        try {
            UserEntity user = userRepository.findUserByUsername(username).orElse(null);
            if (user == null) {
                throw new UsernameNotFoundException("User not found with username: " + username);
            }

            Document document = new Document(documentName, "text/plain", new byte[0], LocalDateTime.now(), LocalDateTime.now());
            document.getUsers().add(user);
            Document savedDocument = documentRepository.save(document);

            user.getDocuments().add(savedDocument);
            userRepository.save(user);

            return savedDocument;
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


// Spring Security's authentication process works as follows:
// 1. The user sends a request to a secured endpoint, providing their credentials in the request.
// 2. An `AuthenticationFilter` (part of Spring Security) intercepts the request.
//    This filter extracts the credentials from the request.
// 3. The `AuthenticationFilter` creates an `Authentication` object, storing the user's credentials,
//    and passes it to the `AuthenticationManager`.
// 4. The `AuthenticationManager` validates the credentials. If the credentials are valid, the `AuthenticationManager`
//    sets the `Authentication` object as authenticated and stores the `UserDetails` in it.
// 5. The `AuthenticationFilter` then stores the `Authentication` object in the `SecurityContextHolder`.
//    This `Authentication` object is now available throughout the rest of the request's lifecycle.

// So, the user doesn't need to do anything special for their data to be stored in the `SecurityContextHolder`.
// As long as they provide valid credentials when sending a request to a secured endpoint,
// Spring Security will handle the rest.