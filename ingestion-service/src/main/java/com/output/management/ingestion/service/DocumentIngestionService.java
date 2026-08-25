package com.output.management.ingestion.service;

import com.output.management.common.event.DocumentReceivedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Service for handling document ingestion
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DocumentIngestionService {

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private static final String TOPIC_DOCUMENT_RECEIVED = "document.received";

    public DocumentReceivedEvent ingestDocument(String documentName, String documentType,
                                                Long fileSize, String customerId,
                                                String accountId) {
        log.debug("Processing document ingestion: {}", documentName);

        // Generate unique document ID
        String documentId = UUID.randomUUID().toString();

        // Create event
        DocumentReceivedEvent event = new DocumentReceivedEvent(
            documentId,
            documentName,
            documentType,
            fileSize,
            customerId,
            accountId
        );

        // Publish to Kafka
        try {
            kafkaTemplate.send(TOPIC_DOCUMENT_RECEIVED, documentId, event);
            log.info("Document received event published. DocumentId: {}, CorrelationId: {}",
                documentId, event.getCorrelationId());
        } catch (Exception e) {
            log.error("Failed to publish document received event for documentId: {}", documentId, e);
            throw new RuntimeException("Failed to ingest document", e);
        }

        return event;
    }
}
