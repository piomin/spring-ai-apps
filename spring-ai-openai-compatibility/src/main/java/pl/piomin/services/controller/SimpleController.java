package pl.piomin.services.controller;

import org.springframework.ai.chat.client.AdvisorParams;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/simple")
public class SimpleController {

    private final ChatClient chatClient;

    public SimpleController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder
                .defaultAdvisors(SimpleLoggerAdvisor.builder().build())
                .build();
    }

    @GetMapping("/{country}")
    public String ping(@PathVariable String country) {
        PromptTemplate pt = new PromptTemplate("""
                What's the capital of {country} ?
                Describe the history of that city briefly.
        """);

        return chatClient.prompt(pt.create(Map.of("country", country)))
                .call()
                .content();
    }
}
