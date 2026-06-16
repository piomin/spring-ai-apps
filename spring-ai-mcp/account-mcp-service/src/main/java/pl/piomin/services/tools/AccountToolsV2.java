package pl.piomin.services.tools;

import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;
import pl.piomin.services.model.Account;
import pl.piomin.services.repository.AccountRepository;

import java.util.List;

@Service
public class AccountToolsV2 {

    private AccountRepository accountRepository;

    public AccountToolsV2(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    @McpTool(name = "getAccountsByPersonIdV2", description = "Find all accounts by person ID")
    public List<Account> getAccountsByPersonId(
            @McpToolParam(description = "Person ID") Long personId) {
        return accountRepository.findByPersonId(personId);
    }
}
