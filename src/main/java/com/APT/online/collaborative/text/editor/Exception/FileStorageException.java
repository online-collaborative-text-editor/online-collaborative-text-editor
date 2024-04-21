package com.APT.online.collaborative.text.editor.Exception;

public class FileStorageException extends RuntimeException {

    private String message;

    public FileStorageException(String message, Throwable cause) {
        super(message, cause);
        this.message = message;
    }

    public FileStorageException(String s) {
        super(s);
        this.message = s;
    }
}
