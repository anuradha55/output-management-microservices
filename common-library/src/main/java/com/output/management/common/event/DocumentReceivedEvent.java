package com.output.management.common.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Event published when a document is received by the ingestion service
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DocumentReceivedEvent extends BaseEvent {

    private static final long serialVersionUID = 1L;

    @JsonProperty("document_id")
    private String documentId;

    @JsonProperty("document_name")
    private String documentName;

    @JsonProperty("document_type")
    private String documentType;

    @JsonProperty("file_size")
    private Long fileSize;

    @JsonProperty("customer_id")
    private String customerId;

    @JsonProperty("account_id")
    private String accountId;

    @JsonProperty("upload_timestamp")
    private Long uploadTimestamp;

    @JsonProperty("metadata")
    private Map<String, String> metadata;

    public DocumentReceivedEvent(String documentId, String documentName, String documentType,
                                 Long fileSize, String customerId, String accountId) {
        super("DOCUMENT_RECEIVED", "ingestion-service");
        this.documentId = documentId;
        this.documentName = documentName;
        this.documentType = documentType;
        this.fileSize = fileSize;
        this.customerId = customerId;
        this.accountId = accountId;
        this.uploadTimestamp = System.currentTimeMillis();
    }
}
