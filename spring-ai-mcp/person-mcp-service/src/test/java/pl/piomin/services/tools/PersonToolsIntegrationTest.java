package pl.piomin.services.tools;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import pl.piomin.services.model.Person;
import pl.piomin.services.repository.PersonRepository;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class PersonToolsIntegrationTest {

    @Autowired
    private PersonTools personTools;

    @Autowired
    private PersonRepository personRepository;

    @BeforeEach
    void setUp() {
        personRepository.deleteAll();
    }

    @Test
    void shouldFindPersonByIdUsingMcpTool() {
        Person p = new Person();
        p.setFirstName("John");
        p.setLastName("Doe");
        p.setAge(30);
        p.setNationality("American");
        Person saved = personRepository.save(p);

        Person found = personTools.getPersonById(saved.getId());
        assertThat(found).isNotNull();
        assertThat(found.getId()).isEqualTo(saved.getId());
        assertThat(found.getFirstName()).isEqualTo("John");
        assertThat(found.getNationality()).isEqualTo("American");
    }

    @Test
    void shouldReturnNullWhenPersonNotFound() {
        Person result = personTools.getPersonById(999L);
        assertThat(result).isNull();
    }

    @Test
    void shouldFindPersonsByNationalityUsingMcpTool() {
        Person a1 = new Person();
        a1.setFirstName("Alice");
        a1.setLastName("A");
        a1.setAge(25);
        a1.setNationality("X");
        Person a2 = new Person();
        a2.setFirstName("Alan");
        a2.setLastName("B");
        a2.setAge(28);
        a2.setNationality("X");
        Person b = new Person();
        b.setFirstName("Bob");
        b.setLastName("C");
        b.setAge(30);
        b.setNationality("Y");
        personRepository.saveAll(List.of(a1, a2, b));

        List<Person> xList = personTools.getPersonsByNationality("X");
        assertThat(xList).hasSize(2);
        assertThat(xList).extracting(Person::getFirstName)
                         .containsExactlyInAnyOrder("Alice", "Alan");
    }

    @Test
    void shouldReturnEmptyListWhenNoPersonsWithNationality() {
        List<Person> empty = personTools.getPersonsByNationality("Z");
        assertThat(empty).isEmpty();
    }

    @Test
    void shouldHandleNullNationalityGracefully() {
        List<Person> result = personTools.getPersonsByNationality(null);
        assertThat(result).isNotNull();
    }

}