package com.output.management.enrichment.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;

@Configuration
public class RestClientConfig {
    @Bean("customerRestClient")
    RestClient customerRestClient(@Value("${clients.customer.base-url}") String baseUrl) { return RestClient.builder().baseUrl(baseUrl).build(); }
    @Bean("accountRestClient")
    RestClient accountRestClient(@Value("${clients.account.base-url}") String baseUrl) { return RestClient.builder().baseUrl(baseUrl).build(); }
}
