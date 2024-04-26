package com.APT.online.collaborative.text.editor.Controller;

import com.APT.online.collaborative.text.editor.Model.Document;
import com.APT.online.collaborative.text.editor.Service.DocumentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;

@RestController
@RequestMapping("/api/files")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @PostMapping("/create")
    public ResponseEntity<String> createDocument(@RequestParam("documentName") String documentName) {
        Document document = documentService.createDocument(documentName);
        return ResponseEntity.status(HttpStatus.CREATED).body("Document with ID " + document.getId() + " was created successfully.");
    }

    @GetMapping("/open/{id}")
    public ResponseEntity<Document> openDocument(@PathVariable("id") String documentId) throws FileNotFoundException {
        Document document = documentService.openDocument(documentId);
        return ResponseEntity.ok(document);
    }

    @PutMapping("/rename/{id}")
    public ResponseEntity<String> renameDocument(@PathVariable("id") String documentId, @RequestBody String newDocumentName) throws FileNotFoundException {
        Document document = documentService.renameDocument(documentId, newDocumentName);
        return ResponseEntity.ok("Document with ID " + documentId + " was renamed to " + newDocumentName + " successfully.");
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDocument(@PathVariable("id") String documentId) throws FileNotFoundException {
        documentService.deleteDocument(documentId);
        return ResponseEntity.ok("Document with ID " + documentId + " was deleted successfully.");
    }
}