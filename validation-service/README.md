# Validation Service

Service responsible for validating documents received from the ingestion service.

## Overview

The Validation Service:
- Consumes `DocumentReceivedEvent` from Kafka topic `document.received`
- Performs comprehensive validation checks on document metadata
- Publishes `DocumentValidatedEvent` to Kafka topic `document.validated`
- Stores validation results in PostgreSQL

## Event Flow

1. Listens for `document.received` events from Ingestion Service
2. Performs validation:
   - Validates document name is not empty
   - Validates document type is specified
   - Validates file size is positive and within limits (100MB)
   - Validates customer ID and account ID
3. Publishes `DocumentValidatedEvent` with validation status (VALID, INVALID, PARTIAL)
4. Enrichment Service consumes the validated event

## Validation Rules

- **Document Name**: Must not be empty
- **Document Type**: Must be specified
- **File Size**: Must be > 0 and <= 100MB (recommended)
- **Customer ID**: Must not be empty
- **Account ID**: Must not be empty

## API Endpoints

### GET /api/validation/health
Health check endpoint.

## Building

```bash
mvn clean package -DskipTests
```

## Running

```bash
java -jar validation-service-1.0.0.jar
```

Or with Maven:
```bash
mvn spring-boot:run
```

## Dependencies

- Spring Boot 3.3.1
- Spring Data JPA
- Spring Kafka
- PostgreSQL Driver
- Common Library module

## Configuration

See `application.yml` for default configuration. Key settings:
- Server port: 8082
- Kafka bootstrap servers: localhost:9092
- Database: PostgreSQL (localhost:5432)
- Kafka consumer group: validation-service
