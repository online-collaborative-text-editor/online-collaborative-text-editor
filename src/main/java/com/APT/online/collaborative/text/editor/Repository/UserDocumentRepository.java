package com.APT.online.collaborative.text.editor.Repository;

import com.APT.online.collaborative.text.editor.Model.UserDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserDocumentRepository extends JpaRepository<UserDocument, Long> {

    @Query("SELECT ud FROM UserDocument ud WHERE ud.user.username = :username AND ud.document.id = :documentId")
    Optional<UserDocument> findUserDocumentByUsernameAndDocumentId(@Param("username") String username, @Param("documentId") String documentId);

    @Transactional
    @Modifying
    @Query("DELETE FROM UserDocument ud WHERE ud.document.id = :documentId")
    void deleteByDocumentId(@Param("documentId") String documentId);

    @Query("SELECT ud.user.username FROM UserDocument ud WHERE ud.document.id = :documentId")
    List<String> findUsernamesByDocumentId(@Param("documentId") String documentId);
}