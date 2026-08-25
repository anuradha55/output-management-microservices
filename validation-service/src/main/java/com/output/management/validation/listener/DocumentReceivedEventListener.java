package com.output.management.validation.listener;

import com.output.management.common.event.DocumentReceivedEvent;
import com.output.management.common.event.DocumentValidatedEvent;
import com.output.management.validation.service.DocumentValidationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * Listener for document ingestion events
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class DocumentReceivedEventListener {

    private final DocumentValidationService validationService;

    @KafkaListener(topics = "document.received", groupId = "validation-service")
    public void handleDocumentReceived(DocumentReceivedEvent event) {
        log.info("Received document for validation. DocumentId: {}, CorrelationId: {}",
            event.getDocumentId(), event.getCorrelationId());

        try {
            DocumentValidatedEvent validatedEvent = validationService.validateDocument(event);
            log.info("Document validation completed. DocumentId: {}, Status: {}",
                event.getDocumentId(), validatedEvent.getValidationStatus());
        } catch (Exception e) {
            log.error("Failed to validate document. DocumentId: {}", event.getDocumentId(), e);
        }
    }
}
