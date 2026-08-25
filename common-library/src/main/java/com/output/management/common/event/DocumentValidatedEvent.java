package com.output.management.common.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Event published when a document passes validation
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class DocumentValidatedEvent extends BaseEvent {

    private static final long serialVersionUID = 1L;

    @JsonProperty("document_id")
    private String documentId;

    @JsonProperty("validation_status")
    private String validationStatus; // VALID, INVALID, PARTIAL

    @JsonProperty("validation_errors")
    private List<String> validationErrors;

    @JsonProperty("validation_warnings")
    private List<String> validationWarnings;

    @JsonProperty("validation_score")
    private Double validationScore;

    @JsonProperty("customer_id")
    private String customerId;

    @JsonProperty("account_id")
    private String accountId;

    public DocumentValidatedEvent(String documentId, String validationStatus, 
                                   String customerId, String accountId) {
        super("DOCUMENT_VALIDATED", "validation-service");
        this.documentId = documentId;
        this.validationStatus = validationStatus;
        this.customerId = customerId;
        this.accountId = accountId;
    }
}
