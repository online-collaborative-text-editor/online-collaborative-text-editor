package com.APT.online.collaborative.text.editor.Controller;

import com.APT.online.collaborative.text.editor.Model.Document;
import com.APT.online.collaborative.text.editor.Service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

import com.APT.online.collaborative.text.editor.dto.DocumentDTO;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/files")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping("/create")
    public ResponseEntity<String> createDocument(@RequestParam("documentName") String documentName, @RequestAttribute("username") String username) {
        Document document = documentService.createDocument(documentName, username);
        return ResponseEntity.status(HttpStatus.CREATED).body(document.getId());
    }

    @PutMapping("/rename/{id}")
    // Owner/Editor
    public ResponseEntity<String> renameDocument(@PathVariable("id") String documentId, @RequestBody Map<String, String> requestBody, @RequestAttribute("username") String username) throws FileNotFoundException, IllegalAccessException {
        String newDocumentName = requestBody.get("newDocumentName");
        Document document = documentService.renameDocument(documentId, newDocumentName, username);
        String message = String.format("Document with ID %s was renamed to '%s' successfully.", documentId, newDocumentName);
        return ResponseEntity.ok(message);
    }

   @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable("id") String documentId, @RequestAttribute("username") String username) throws FileNotFoundException, IllegalAccessException {
        documentService.deleteDocument(documentId, username);
        return ResponseEntity.ok("Document with ID " + documentId + " was deleted successfully.");
    }

   @GetMapping("/list/viewed")
    public ResponseEntity<List<DocumentDTO>> listViewerDocuments(@RequestAttribute("username") String username) {
        List<DocumentDTO> documents = documentService.listViewerDocuments(username);
        return ResponseEntity.ok(documents);
    }

    @GetMapping("/list/owned")
    public ResponseEntity<List<DocumentDTO>> listOwnerDocuments(@RequestAttribute("username") String username) {
        List<DocumentDTO> documents = documentService.listOwnerDocuments(username);
        return ResponseEntity.ok(documents);
    }

    @GetMapping("/list/viewer-editor")
    public ResponseEntity<List<DocumentDTO>> listViewerEditorDocuments(@RequestAttribute("username") String username) {
        List<DocumentDTO> documents = documentService.listViewerEditorDocuments(username);
        return ResponseEntity.ok(documents);
    }

    @PostMapping("/share/{id}")
    public ResponseEntity<String> shareDocument(@PathVariable("id") String documentId, @RequestParam("username") String username, @RequestParam("permission") String permission, @RequestAttribute("username") String owner) throws FileNotFoundException, IllegalAccessException {
        documentService.shareDocument(documentId, username, permission, owner);
        return ResponseEntity.ok("Document with ID " + documentId + " was shared with " + username + " successfully.");
    }
}