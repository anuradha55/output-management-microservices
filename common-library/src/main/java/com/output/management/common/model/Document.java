package com.output.management.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

/**
 * Document domain model shared across services
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Document {

    private String documentId;
    private String documentName;
    private String documentType;
    private Long fileSize;
    private String customerId;
    private String accountId;
    private String status; // RECEIVED, VALIDATED, ENRICHED, PROCESSED, FAILED
    private Instant createdAt;
    private Instant updatedAt;
    private Map<String, String> metadata;
    private Map<String, Object> enrichmentData;
}
