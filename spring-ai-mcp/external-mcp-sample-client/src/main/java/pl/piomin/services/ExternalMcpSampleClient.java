package pl.piomin.services;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.reactive.function.client.WebClient;
import pl.piomin.services.client.McpSyncClientExchangeFilterFunction;

@SpringBootApplication
public class ExternalMcpSampleClient {

    public static void main(String[] args) {
        SpringApplication.run(ExternalMcpSampleClient.class, args);
    }

    @Bean
    WebClient.Builder webClientBuilder(McpSyncClientExchangeFilterFunction filterFunction) {
        return WebClient.builder()
                .filter(filterFunction);
    }
}
