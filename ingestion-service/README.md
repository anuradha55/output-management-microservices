# Ingestion Service

Service responsible for receiving and ingesting documents into the system.

## Overview

The Ingestion Service:
- Exposes REST API endpoints for document upload
- Generates unique document IDs and correlation IDs
- Publishes `DocumentReceivedEvent` to Kafka topic `document.received`
- Stores document metadata in PostgreSQL

## API Endpoints

### POST /api/documents/ingest
Ingests a new document.

**Parameters:**
- `documentName` (required): Name of the document
- `documentType` (required): Type of document (PDF, Excel, CSV, etc.)
- `fileSize` (required): Size of the file in bytes
- `customerId` (required): ID of the customer
- `accountId` (required): ID of the account

**Response:**
```json
{
  "documentId": "550e8400-e29b-41d4-a716-446655440000",
  "eventId": "550e8400-e29b-41d4-a716-446655440001",
  "correlationId": "550e8400-e29b-41d4-a716-446655440002",
  "status": "Document ingestion initiated",
  "timestamp": "2024-01-15T10:30:00Z"
}
```

### GET /api/documents/health
Health check endpoint.

## Event Flow

1. Document received via REST API
2. DocumentReceivedEvent published to Kafka topic `document.received`
3. Validation Service consumes the event

## Building

```bash
mvn clean package -DskipTests
```

## Running

```bash
java -jar ingestion-service-1.0.0.jar
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
- Server port: 8081
- Kafka bootstrap servers: localhost:9092
- Database: PostgreSQL (localhost:5432)
