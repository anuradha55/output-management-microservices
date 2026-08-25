# Enrichment Service

Consumes `document.validated` events and is the extension point for customer and account enrichment.

Run dependencies with `docker compose up -d`, then build from the repository root with `mvn clean test`.

Health: `/actuator/health`.
