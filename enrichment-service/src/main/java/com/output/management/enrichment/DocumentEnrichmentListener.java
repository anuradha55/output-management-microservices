package com.output.management.enrichment;
import com.output.management.common.event.DocumentValidatedEvent;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class DocumentEnrichmentListener {
  @KafkaListener(topics = "document.validated", groupId = "enrichment-service")
  public void enrich(DocumentValidatedEvent event) {
    // Customer and account enrichment will be added in the next increment.
    System.out.println("Received validated document: " + event);
  }
}
