package com.output.management.ingestion.controller;

import com.output.management.common.event.DocumentReceivedEvent;
import com.output.management.ingestion.service.DocumentIngestionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * REST Controller for document ingestion
 */
@Slf4j
@RestController
@RequestMapping("/documents")
@RequiredArgsConstructor
public class DocumentIngestionController {

    private final DocumentIngestionService ingestionService;

    @PostMapping("/ingest")
    public ResponseEntity<Map<String, Object>> ingestDocument(
            @RequestParam String documentName,
            @RequestParam String documentType,
            @RequestParam Long fileSize,
            @RequestParam String customerId,
            @RequestParam String accountId) {

        log.info("Ingesting document: {} for customer: {}", documentName, customerId);
        
        DocumentReceivedEvent event = ingestionService.ingestDocument(
            documentName, documentType, fileSize, customerId, accountId
        );

        Map<String, Object> response = new HashMap<>();
        response.put("documentId", event.getDocumentId());
        response.put("eventId", event.getEventId());
        response.put("correlationId", event.getCorrelationId());
        response.put("status", "Document ingestion initiated");
        response.put("timestamp", event.getTimestamp());

        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    @GetMapping("/health")
    public ResponseEntity<Map<String, String>> health() {
        Map<String, String> response = new HashMap<>();
        response.put("service", "ingestion-service");
        response.put("status", "UP");
        return ResponseEntity.ok(response);
    }
}
