package com.output.management.customer;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomerControllerTest {
 @Test void returnsCustomer() { assertEquals("RETAIL", new CustomerController().getCustomer("C1").getBody().get("segment")); }
}
