# Common Library

Shared event models, domain models, and utilities for the Output Management Microservices.

## Overview

This module contains:
- Common event models (BaseEvent, DocumentReceivedEvent, DocumentValidatedEvent, DocumentEnrichedEvent)
- Domain models (Document)
- Shared exceptions
- Utility classes

## Event Models

### BaseEvent
Base abstract class for all domain events with:
- Event ID (UUID)
- Event Type
- Timestamp
- Source Service
- Correlation ID
- Version

### DocumentReceivedEvent
Published when a document is ingested by the ingestion service.

### DocumentValidatedEvent
Published when a document passes validation.

### DocumentEnrichedEvent
Published when a document is enriched with additional data.

## Building

```bash
mvn clean package -DskipTests
```

## Dependencies

- Spring Boot 3.3.1
- Jackson for JSON processing
- Lombok for boilerplate reduction
- Validation API
