package com.APT.online.collaborative.text.editor.Repository;

import com.APT.online.collaborative.text.editor.Model.Document;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DocumentRepository extends JpaRepository<Document, String> {
}
