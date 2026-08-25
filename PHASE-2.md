# Phase 2: Enrichment and resilience

Flow: `document.validated` -> Enrichment Service -> Customer Service + Account Service -> `document.enriched`.

Customer endpoint: `GET /api/customers/{customerId}`
Account endpoint: `GET /api/accounts/{accountId}`

Enrichment uses Spring RestClient and Resilience4j circuit breaker and retry policies. Critical enrichment failures throw from the listener so Kafka's error handler retries the record before publishing it to `<topic>.DLT`.
