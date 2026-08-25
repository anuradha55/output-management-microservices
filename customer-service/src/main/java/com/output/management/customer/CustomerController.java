package com.output.management.customer;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {
    @GetMapping("/{customerId}")
    public ResponseEntity<Map<String, String>> getCustomer(@PathVariable String customerId) {
        return ResponseEntity.ok(Map.of("customerId", customerId, "customerName", "Customer " + customerId, "segment", "RETAIL"));
    }
}
