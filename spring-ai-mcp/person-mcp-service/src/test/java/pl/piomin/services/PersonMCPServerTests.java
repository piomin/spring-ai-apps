// File: person-mcp-service/src/test/java/pl/piomin/services/PersonMCPServerTests.java
package pl.piomin.services;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest(classes = PersonMCPServer.class)
@ActiveProfiles("test")
class PersonMCPServerTests {

    @Test
    void contextLoads() {
        // Verifies that the Spring Boot application context for PersonMCPServer starts successfully
    }
}