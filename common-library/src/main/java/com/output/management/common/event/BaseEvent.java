package com.output.management.common.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

/**
 * Base event class for all domain events in the microservices
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class BaseEvent implements Serializable {

    private static final long serialVersionUID = 1L;

    @JsonProperty("event_id")
    private String eventId = UUID.randomUUID().toString();

    @JsonProperty("event_type")
    private String eventType;

    @JsonProperty("timestamp")
    private Instant timestamp = Instant.now();

    @JsonProperty("source_service")
    private String sourceService;

    @JsonProperty("correlation_id")
    private String correlationId = UUID.randomUUID().toString();

    @JsonProperty("version")
    private String version = "1.0";

    protected BaseEvent(String eventType, String sourceService) {
        this.eventId = UUID.randomUUID().toString();
        this.eventType = eventType;
        this.sourceService = sourceService;
        this.timestamp = Instant.now();
        this.correlationId = UUID.randomUUID().toString();
        this.version = "1.0";
    }
}
