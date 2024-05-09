package com.APT.online.collaborative.text.editor.Controller;

import com.APT.online.collaborative.text.editor.Model.Document;
import com.APT.online.collaborative.text.editor.Service.DocumentService;
import com.APT.online.collaborative.text.editor.dto.DocumentRequestDto;
import com.APT.online.collaborative.text.editor.dto.RenameDocumentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

import com.APT.online.collaborative.text.editor.DTO.DocumentDTO;

import java.util.List;

@RestController
@RequestMapping("/api/files")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping("/create")
    public ResponseEntity<String> createDocument(@RequestBody DocumentRequestDto documentRequestDto) {
        String documentName = documentRequestDto.getDocumentName();
        String username = documentRequestDto.getUsername();
        Document document = documentService.createDocument(documentName, username);
        return ResponseEntity.status(HttpStatus.CREATED).body("Document with ID " + document.getId() + " was created successfully.");
    }

    @PutMapping("/rename/{id}")
    // Owner/Editor
    public ResponseEntity<String> renameDocument(@PathVariable("id") String documentId, @RequestBody RenameDocumentRequest renameDocumentRequest, @RequestAttribute("username") String username) throws FileNotFoundException, IllegalAccessException {
        Document document = documentService.renameDocument(documentId, renameDocumentRequest.getNewDocumentName(), username);
        String message = String.format("Document with ID %s was renamed to '%s' successfully.", documentId, renameDocumentRequest.getNewDocumentName());
        return ResponseEntity.ok(message);
    }

   @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable("id") String documentId, @RequestAttribute("username") String username) throws FileNotFoundException, IllegalAccessException {
        documentService.deleteDocument(documentId, username);
        return ResponseEntity.ok("Document with ID " + documentId + " was deleted successfully.");
    }

   @GetMapping("/list/viewer")
    public ResponseEntity<List<DocumentDTO>> listViewerDocuments(@RequestAttribute("username") String username) {
        List<DocumentDTO> documents = documentService.listViewerDocuments(username);
        return ResponseEntity.ok(documents);
    }

    @GetMapping("/list/owner")
    public ResponseEntity<List<DocumentDTO>> listOwnerDocuments(@RequestAttribute("username") String username) {
        List<DocumentDTO> documents = documentService.listOwnerDocuments(username);
        return ResponseEntity.ok(documents);
    }

    @GetMapping("/list/viewer-editor")
    public ResponseEntity<List<DocumentDTO>> listViewerEditorDocuments(@RequestAttribute("username") String username) {
        List<DocumentDTO> documents = documentService.listViewerEditorDocuments(username);
        return ResponseEntity.ok(documents);
    }

    // Share a document with a user by granting him certain permissions
    @PostMapping("/share/{id}")
    public ResponseEntity<String> shareDocument(@PathVariable("id") String documentId, @RequestParam("username") String username, @RequestParam("permission") String permission, @RequestAttribute("username") String owner) throws FileNotFoundException, IllegalAccessException {
        documentService.shareDocument(documentId, username, permission, owner);
        return ResponseEntity.ok("Document with ID " + documentId + " was shared with " + username + " successfully.");
    }
}