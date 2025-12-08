package pl.piomin.services.tools;

import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.ai.tool.annotation.Tool;
import org.springframework.ai.tool.annotation.ToolParam;
import org.springframework.stereotype.Service;
import pl.piomin.services.model.Person;
import pl.piomin.services.repository.PersonRepository;

import java.util.List;

@Service
public class PersonToolsV2 {

    private PersonRepository personRepository;

    public PersonToolsV2(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    @McpTool(name = "getPersonByIdV2", description = "Find person by ID")
    public Person getPersonById(
            @McpToolParam(description = "Person ID") Long id) {
        return personRepository.findById(id).orElse(null);
    }

    @McpTool(name = "getPersonsByNationalityV2", description = "Find all persons by nationality")
    public List<Person> getPersonsByNationality(
            @McpToolParam(description = "Nationality") String nationality) {
        return personRepository.findByNationality(nationality);
    }

}
