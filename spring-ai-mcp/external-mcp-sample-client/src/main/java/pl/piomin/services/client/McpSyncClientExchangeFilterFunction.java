package pl.piomin.services.client;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientRequest;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.ExchangeFilterFunction;
import org.springframework.web.reactive.function.client.ExchangeFunction;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@Component
public class McpSyncClientExchangeFilterFunction implements ExchangeFilterFunction {

    @Value("${mcp.token}")
    private String token;

    @Override
    public Mono<ClientResponse> filter(ClientRequest request, ExchangeFunction next) {

            var requestWithToken = ClientRequest.from(request)
                    .headers(headers -> headers.setBearerAuth(token))
                    .build();
            return next.exchange(requestWithToken);

    }

}
