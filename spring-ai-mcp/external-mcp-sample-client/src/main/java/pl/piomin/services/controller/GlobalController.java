package pl.piomin.services.controller;

import org.apache.logging.log4j.message.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.ai.chat.prompt.SystemPromptTemplate;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/global")
public class GlobalController {

    private final static Logger LOG = LoggerFactory.getLogger(CircleCIController.class);
    private final ChatClient chatClient;

    public GlobalController(ChatClient.Builder chatClientBuilder,
                            ToolCallbackProvider tools) {
        this.chatClient = chatClientBuilder
                .defaultToolCallbacks(tools)
                .build();
    }

    @GetMapping("/status/{repo}")
    String repoStatus(@PathVariable String repo) {
        SystemPromptTemplate st = new SystemPromptTemplate("""
                My username in GitHub is piomin.
                Each my project key in SonarCloud contains the prefix with my organization name and _ char.
                """);
        var stMsg = st.createMessage();

        PromptTemplate pt = new PromptTemplate("""
                When was the last commit made in my GitHub repository {repo} ?
                What is the latest analyze status in SonarCloud for that repo ?
                """);
        var usMsg = pt.createMessage(Map.of("repo", repo));

        Prompt prompt = new Prompt(List.of(usMsg, stMsg));
        return this.chatClient.prompt(prompt)
                .call()
                .content();
    }
}
