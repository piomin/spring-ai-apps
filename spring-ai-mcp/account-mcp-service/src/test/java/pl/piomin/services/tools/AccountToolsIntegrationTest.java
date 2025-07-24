package pl.piomin.services.tools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import pl.piomin.services.model.Account;
import pl.piomin.services.repository.AccountRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class AccountToolsIntegrationTest {

    @Autowired
    private AccountTools accountTools;

    @Autowired
    private AccountRepository accountRepository;

    @BeforeEach
    void setUp() {
        accountRepository.deleteAll();
    }

    @Test
    void shouldFindAccountsByPersonId() {
        // Given
        Account account1 = new Account();
        account1.setPersonId(1L);
        account1.setNumber("ACC001");
        account1.setBalance(1000);

        Account account2 = new Account();
        account2.setPersonId(1L);
        account2.setNumber("ACC002");
        account2.setBalance(2000);

        Account account3 = new Account();
        account3.setPersonId(2L);
        account3.setNumber("ACC003");
        account3.setBalance(1500);

        accountRepository.saveAll(List.of(account1, account2, account3));

        // When
        List<Account> accountsForPerson1 = accountTools.getAccountsByPersonId(1L);
        List<Account> accountsForPerson2 = accountTools.getAccountsByPersonId(2L);

        // Then
        assertThat(accountsForPerson1).hasSize(2);
        assertThat(accountsForPerson1)
            .extracting(Account::getNumber)
            .containsExactlyInAnyOrder("ACC001", "ACC002");
        assertThat(accountsForPerson2).hasSize(1);
        assertThat(accountsForPerson2.get(0).getNumber()).isEqualTo("ACC003");
    }

    @Test
    void shouldReturnEmptyListWhenNoAccounts() {
        List<Account> accounts = accountTools.getAccountsByPersonId(999L);
        assertThat(accounts).isEmpty();
    }

    @Test
    void shouldHandleNullPersonIdGracefully() {
        List<Account> accounts = accountTools.getAccountsByPersonId(null);
        assertThat(accounts).isNotNull();
    }
}