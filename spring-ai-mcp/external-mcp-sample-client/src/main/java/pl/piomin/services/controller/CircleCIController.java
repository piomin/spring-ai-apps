package pl.piomin.services.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/circleci")
public class CircleCIController {

    private final static Logger LOG = LoggerFactory.getLogger(CircleCIController.class);
    private final ChatClient chatClient;

    public CircleCIController(ChatClient.Builder chatClientBuilder,
                              ToolCallbackProvider tools) {
        this.chatClient = chatClientBuilder
                .defaultToolCallbacks(tools)
                .build();
    }

    @GetMapping("/count")
    String countRepositories() {
        PromptTemplate pt = new PromptTemplate("""
                How many projects in CircleCI do I have ?
                """);
        Prompt p = pt.create();
        return this.chatClient.prompt(p)
                .call()
                .content();
    }

}
