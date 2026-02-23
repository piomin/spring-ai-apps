package pl.piomin.services.controller;

import org.springframework.ai.anthropic.AnthropicChatOptions;
import org.springframework.ai.anthropic.api.AnthropicApi;
import org.springframework.ai.anthropic.api.AnthropicCacheOptions;
import org.springframework.ai.anthropic.api.AnthropicCacheStrategy;
import org.springframework.ai.chat.messages.SystemMessage;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system")
public class SpringController {

    @Autowired
    ChatModel chatModel;

    @GetMapping
    public void test() {
        // Create system content that will be reused multiple times
        String largeSystemPrompt = "You are an expert software architect specializing in distributed systems...";

// First request - creates cache
        ChatResponse firstResponse = chatModel.call(
                new Prompt(
                        List.of(
                                new SystemMessage(largeSystemPrompt),
                                new UserMessage("What is microservices architecture?")
                        ),
                        AnthropicChatOptions.builder()
                                .model("claude-sonnet-4-5")
                                .cacheOptions(AnthropicCacheOptions.builder()
                                        .strategy(AnthropicCacheStrategy.SYSTEM_ONLY)
                                        .build())
                                .maxTokens(500)
                                .build()
                )
        );

        // Access cache-related token usage
        AnthropicApi.Usage firstUsage = (AnthropicApi.Usage) firstResponse.getMetadata()
                .getUsage().getNativeUsage();

        System.out.println("Cache creation tokens: " + firstUsage.cacheCreationInputTokens());
        System.out.println("Cache read tokens: " + firstUsage.cacheReadInputTokens());

// Second request with same system prompt - reads from cache
        ChatResponse secondResponse = chatModel.call(
                new Prompt(
                        List.of(
                                new SystemMessage(largeSystemPrompt),
                                new UserMessage("What are the benefits of event sourcing?")
                        ),
                        AnthropicChatOptions.builder()
                                .model("claude-sonnet-4-5")
                                .cacheOptions(AnthropicCacheOptions.builder()
                                        .strategy(AnthropicCacheStrategy.SYSTEM_ONLY)
                                        .build())
                                .maxTokens(500)
                                .build()
                )
        );

        AnthropicApi.Usage secondUsage = (AnthropicApi.Usage) secondResponse.getMetadata()
                .getUsage().getNativeUsage();

        System.out.println("Cache creation tokens: " + secondUsage.cacheCreationInputTokens()); // Should be 0
        System.out.println("Cache read tokens: " + secondUsage.cacheReadInputTokens());
    }
}
