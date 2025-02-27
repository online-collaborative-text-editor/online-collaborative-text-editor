package com.APT.online.collaborative.text.editor.Repository;

import com.APT.online.collaborative.text.editor.Model.Document;
import com.APT.online.collaborative.text.editor.Model.UserDocument;
import com.APT.online.collaborative.text.editor.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
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

    @Query("SELECT ud FROM UserDocument ud WHERE ud.user.username = :username AND ud.permission = :permission")
    List<UserDocument> findUserDocumentsByUsernameAndPermission(@Param("username") String username, @Param("permission") Permission permission);

    @Query("SELECT ud FROM UserDocument ud WHERE ud.document = :document AND ud.permission = :permission")
    Optional<UserDocument> findUserDocumentByDocumentAndPermission(@Param("document") Document document, @Param("permission") Permission permission);

    @Query("SELECT ud FROM UserDocument ud WHERE ud.document.id = :documentId")
    List<UserDocument> findUserDocumentsByDocumentId(@Param("documentId") String documentId);
}