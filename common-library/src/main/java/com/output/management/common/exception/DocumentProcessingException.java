package com.output.management.common.exception;

/**
 * Exception thrown when document processing fails
 */
public class DocumentProcessingException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private String errorCode;
    private String documentId;

    public DocumentProcessingException(String message) {
        super(message);
    }

    public DocumentProcessingException(String message, Throwable cause) {
        super(message, cause);
    }

    public DocumentProcessingException(String message, String errorCode, String documentId) {
        super(message);
        this.errorCode = errorCode;
        this.documentId = documentId;
    }

    public DocumentProcessingException(String message, Throwable cause, String errorCode, String documentId) {
        super(message, cause);
        this.errorCode = errorCode;
        this.documentId = documentId;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getDocumentId() {
        return documentId;
    }
}
