package com.output.management.account;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/accounts")
public class AccountController {
    @GetMapping("/{accountId}")
    public ResponseEntity<Map<String, String>> getAccount(@PathVariable String accountId) {
        return ResponseEntity.ok(Map.of("accountId", accountId, "accountType", "SAVINGS", "currency", "INR"));
    }
}
