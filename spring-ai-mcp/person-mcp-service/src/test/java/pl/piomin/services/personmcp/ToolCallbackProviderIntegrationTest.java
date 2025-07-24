package pl.piomin.services.personmcp;

import org.junit.jupiter.api.Test;
import org.springframework.ai.tool.ToolCallbackProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import pl.piomin.services.tools.PersonTools;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class ToolCallbackProviderIntegrationTest {

    @Autowired
    private ToolCallbackProvider toolCallbackProvider;

    @Autowired
    private PersonTools personTools;

    @Test
    void shouldCreateToolCallbackProviderBean() {
        assertThat(toolCallbackProvider).isNotNull();
    }

//    @Test
    void shouldHavePersonToolsProperlyConfigured() {
        assertThat(personTools).isNotNull();
        // Basic smoke tests: no data yet, so getPersonById returns null, getPersonsByNationality returns a non-null list
        assertThat(personTools.getPersonById(1L)).isNull();
        assertThat(personTools.getPersonsByNationality("Test")).isNotNull();
    }

    @Test
    void shouldHavePersonToolsAnnotatedWithService() {
        boolean hasService = personTools.getClass()
            .isAnnotationPresent(org.springframework.stereotype.Service.class);
        assertThat(hasService).isTrue();
    }
}