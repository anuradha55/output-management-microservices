package com.output.management.common.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.Map;

/**
 * Event published when a document is enriched with additional data
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DocumentEnrichedEvent extends BaseEvent {

    private static final long serialVersionUID = 1L;

    @JsonProperty("document_id")
    private String documentId;

    @JsonProperty("enrichment_data")
    private Map<String, Object> enrichmentData;

    @JsonProperty("enrichment_status")
    private String enrichmentStatus; // COMPLETED, FAILED, PARTIAL

    @JsonProperty("customer_id")
    private String customerId;

    @JsonProperty("account_id")
    private String accountId;

    @JsonProperty("enrichment_timestamp")
    private Long enrichmentTimestamp;

    public DocumentEnrichedEvent(String documentId, String customerId, String accountId) {
        super("DOCUMENT_ENRICHED", "enrichment-service");
        this.documentId = documentId;
        this.customerId = customerId;
        this.accountId = accountId;
        this.enrichmentTimestamp = System.currentTimeMillis();
    }
}
