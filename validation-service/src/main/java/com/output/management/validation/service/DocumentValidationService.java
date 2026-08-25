package com.output.management.validation.service;

import com.output.management.common.event.DocumentReceivedEvent;
import com.output.management.common.event.DocumentValidatedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service for validating documents
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentValidationService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC_DOCUMENT_VALIDATED = "document.validated";

    public DocumentValidatedEvent validateDocument(DocumentReceivedEvent event) {
        log.debug("Starting validation for document: {}", event.getDocumentId());

        // Perform validation checks
        List<String> validationErrors = new ArrayList<>();
        List<String> validationWarnings = new ArrayList<>();
        double validationScore = 100.0;

        // Example validation rules
        if (event.getFileSize() == null || event.getFileSize() <= 0) {
            validationErrors.add("File size must be greater than 0");
            validationScore -= 10.0;
        }

        if (event.getDocumentName() == null || event.getDocumentName().trim().isEmpty()) {
            validationErrors.add("Document name cannot be empty");
            validationScore -= 10.0;
        }

        if (event.getDocumentType() == null || event.getDocumentType().trim().isEmpty()) {
            validationErrors.add("Document type cannot be empty");
            validationScore -= 10.0;
        }

        if (event.getCustomerId() == null || event.getCustomerId().trim().isEmpty()) {
            validationErrors.add("Customer ID cannot be empty");
            validationScore -= 10.0;
        }

        if (event.getAccountId() == null || event.getAccountId().trim().isEmpty()) {
            validationErrors.add("Account ID cannot be empty");
            validationScore -= 10.0;
        }

        // Check file size limits (example: 100MB limit)
        if (event.getFileSize() != null && event.getFileSize() > 100 * 1024 * 1024) {
            validationWarnings.add("File size exceeds recommended limit of 100MB");
            validationScore -= 5.0;
        }

        // Determine validation status
        String validationStatus;
        if (validationErrors.isEmpty()) {
            validationStatus = "VALID";
        } else if (validationErrors.size() <= 2) {
            validationStatus = "PARTIAL";
        } else {
            validationStatus = "INVALID";
        }

        // Create validated event
        DocumentValidatedEvent validatedEvent = new DocumentValidatedEvent(
            event.getDocumentId(),
            validationStatus,
            event.getCustomerId(),
            event.getAccountId()
        );

        validatedEvent.setValidationErrors(validationErrors);
        validatedEvent.setValidationWarnings(validationWarnings);
        validatedEvent.setValidationScore(Math.max(0, validationScore));
        validatedEvent.setCorrelationId(event.getCorrelationId());

        // Publish to Kafka
        try {
            kafkaTemplate.send(TOPIC_DOCUMENT_VALIDATED, event.getDocumentId(), validatedEvent);
            log.info("Document validated event published. DocumentId: {}, Status: {}, CorrelationId: {}",
                event.getDocumentId(), validationStatus, event.getCorrelationId());
        } catch (Exception e) {
            log.error("Failed to publish document validated event for documentId: {}", event.getDocumentId(), e);
            throw new RuntimeException("Failed to publish validation result", e);
        }

        return validatedEvent;
    }
}
