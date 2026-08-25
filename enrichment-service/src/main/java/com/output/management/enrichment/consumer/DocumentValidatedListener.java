package com.output.management.enrichment.consumer;

import com.output.management.common.event.DocumentEnrichedEvent;
import com.output.management.common.event.DocumentValidatedEvent;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class DocumentValidatedListener {
    @Qualifier("customerRestClient") private final RestClient customerRestClient;
    @Qualifier("accountRestClient") private final RestClient accountRestClient;
    private final KafkaTemplate<String, DocumentEnrichedEvent> kafkaTemplate;

    @KafkaListener(topics = "document.validated", groupId = "enrichment-service")
    @CircuitBreaker(name = "downstream", fallbackMethod = "enrichmentFailed")
    @Retry(name = "downstream")
    public void enrich(DocumentValidatedEvent event) {
        Map customer = customerRestClient.get().uri("/api/customers/{id}", event.getCustomerId()).retrieve().body(Map.class);
        Map account = accountRestClient.get().uri("/api/accounts/{id}", event.getAccountId()).retrieve().body(Map.class);
        if (customer == null || account == null) throw new IllegalStateException("Critical enrichment data unavailable");
        Map<String, Object> data = new LinkedHashMap<>();
        data.put("customer", customer);
        data.put("account", account);
        data.put("enrichedXml", "<document><customer>" + event.getCustomerId() + "</customer><account>" + event.getAccountId() + "</account></document>");
        DocumentEnrichedEvent enriched = new DocumentEnrichedEvent(event.getDocumentId(), event.getCustomerId(), event.getAccountId());
        enriched.setEnrichmentData(data);
        enriched.setEnrichmentStatus("COMPLETED");
        kafkaTemplate.send("document.enriched", event.getDocumentId(), enriched);
    }

    private void enrichmentFailed(DocumentValidatedEvent event, Throwable error) {
        throw new IllegalStateException("Critical enrichment failed for document " + event.getDocumentId(), error);
    }
}
