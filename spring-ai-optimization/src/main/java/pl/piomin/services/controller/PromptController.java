package pl.piomin.services.controller;

import org.springframework.ai.anthropic.AnthropicChatOptions;
import org.springframework.ai.anthropic.api.AnthropicApi;
import org.springframework.ai.anthropic.api.AnthropicCacheOptions;
import org.springframework.ai.anthropic.api.AnthropicCacheStrategy;
import org.springframework.ai.anthropic.api.AnthropicCacheTtl;
import org.springframework.ai.chat.client.AdvisorParams;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.messages.MessageType;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.piomin.services.model.Person;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/prompt")
public class PromptController {

    private final ChatClient chatClient;

    public PromptController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
//                .defaultSystem("""
//                You are mostly interested in art and politics.
//                You don't know much about sport.
//                """)
//                .defaultAdvisors(AdvisorParams.ENABLE_NATIVE_STRUCTURED_OUTPUT)
                .defaultAdvisors(SimpleLoggerAdvisor.builder().build())
                .build();
    }

    @GetMapping("/persons/{profession}/{country}/{count}")
    public List<Person> returnPersons(@PathVariable String profession,
                                      @PathVariable String country,
                                      @PathVariable int count) {
        var type = new ParameterizedTypeReference<List<Person>>() {};
        PromptTemplate pt = new PromptTemplate("""
                Return a list of {count} famous {profession} from {country}.
        """);
        Prompt p = pt.create(Map.of("count", count, "profession", profession, "country", country),
                AnthropicChatOptions.builder()
                        .model("claude-sonnet-4-5")
                        .cacheOptions(AnthropicCacheOptions.builder()
                                .strategy(AnthropicCacheStrategy.SYSTEM_AND_TOOLS)
//                                .messageTypeTtl(MessageType.SYSTEM, AnthropicCacheTtl.ONE_HOUR)
                                .build())
                        .maxTokens(500)
                        .temperature(0.4)
                        .build());

        return chatClient.prompt(p)
                .call()
                .entity(type);
    }

    @GetMapping("/raw/{profession}/{count}")
    public String returnRaw(@PathVariable String profession,
                                @PathVariable int count) {

        String r1 = chat(profession, "France", count);
        String r2 = chat(profession, "Germany", count);

        return r1 + r2;
    }

    private String chat(String profession, String country, int count) {
        PromptTemplate pt = new PromptTemplate("""
                Return a list of {count} famous {profession} from {country}.
        """);
        var msg = pt.createMessage(Map.of("count", count, "profession", profession, "country", country));

        String systemMsg = """
                You are an expert in art and politics, but completely do not interested in sport...
                """;

        Prompt p1 = new Prompt(List.of(new SystemMessage(systemMsg), msg),
                AnthropicChatOptions.builder()
                        .model("claude-sonnet-4-5")
                        .cacheOptions(AnthropicCacheOptions.builder()
                                .strategy(AnthropicCacheStrategy.SYSTEM_ONLY)
                                .build())
                        .maxTokens(500)
                        .build());

        ChatResponse cr = chatClient.prompt(p1)
                .call()
                .chatResponse();

        AnthropicApi.Usage firstUsage = (AnthropicApi.Usage) cr.getMetadata()
                .getUsage().getNativeUsage();

        System.out.println("Cache creation tokens: " + firstUsage.cacheCreationInputTokens());
        System.out.println("Cache read tokens: " + firstUsage.cacheReadInputTokens());

        return cr.getResult().toString();
    }
}


