package com.output.management.account;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountControllerTest {
 @Test void returnsAccount() { assertEquals("INR", new AccountController().getAccount("A1").getBody().get("currency")); }
}
