package com.APT.online.collaborative.text.editor.Model;

import jakarta.persistence.Embeddable;
import java.io.Serializable;

//In this code, UserDocumentId is a class that represents the composite key of user_id and document_id. The @Embeddable annotation is used to indicate that this class is used as an embedded class in the UserDocument entity. The @EmbeddedId annotation is used in the UserDocument entity to indicate that the id field is a composite primary key.
@Embeddable
public class UserDocumentId implements Serializable {
    private Long userId;
    private Long documentId;

    // getters and setters
}