package com.output.management.ingestion.config;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaAdmin;

/**
 * Kafka configuration for ingestion service
 */
@Configuration
public class KafkaConfig {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Bean
    public KafkaAdmin kafkaAdmin() {
        return new KafkaAdmin(
            java.util.Collections.singletonMap(
                AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers
            )
        );
    }

    @Bean
    public NewTopic documentReceivedTopic() {
        return new NewTopic("document.received", 3, (short) 1);
    }
}
