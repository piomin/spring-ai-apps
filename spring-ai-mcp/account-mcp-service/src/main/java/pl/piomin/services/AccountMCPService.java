package pl.piomin.services;

import io.modelcontextprotocol.spec.McpSchema;
import org.springaicommunity.mcp.annotation.McpArg;
import org.springaicommunity.mcp.annotation.McpPrompt;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.ai.tool.method.MethodToolCallbackProvider;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.piomin.services.tools.AccountTools;

import java.util.List;

@SpringBootApplication
public class AccountMCPService {

    public static void main(String[] args) {
        SpringApplication.run(AccountMCPService.class, args);
    }

    @Bean
    public ToolCallbackProvider tools(AccountTools accountTools) {
        return MethodToolCallbackProvider.builder()
                .toolObjects(accountTools)
                .build();
    }

    @McpPrompt(
            name = "accounts-by-person-id",
            description = "Get accounts by person ID")
    public McpSchema.GetPromptResult greeting(
            @McpArg(name = "personId", description = "Person ID", required = true)
            int personId) {

        String message = "How many accounts has person with " + personId + " ID ?";

        return new McpSchema.GetPromptResult(
                "Get accounts by person ID",
                List.of(new McpSchema.PromptMessage(McpSchema.Role.USER, new McpSchema.TextContent(message)))
        );
    }
}
