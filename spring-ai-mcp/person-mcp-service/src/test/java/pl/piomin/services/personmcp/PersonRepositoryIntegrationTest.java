package pl.piomin.services.personmcp;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import pl.piomin.services.model.Person;
import pl.piomin.services.repository.PersonRepository;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
class PersonRepositoryIntegrationTest {

    @Autowired
    private PersonRepository personRepository;

    @BeforeEach
    void setUp() {
        personRepository.deleteAll();
    }

    @Test
    void shouldSaveAndFindPerson() {
        Person person = new Person();
        person.setFirstName("Test");
        person.setLastName("Person");
        person.setAge(25);
        person.setNationality("TestLand");

        Person saved = personRepository.save(person);
        assertThat(saved.getId()).isNotNull();

        Optional<Person> found = personRepository.findById(saved.getId());
        assertThat(found).isPresent();
        assertThat(found.get().getFirstName()).isEqualTo("Test");
    }

    @Test
    void shouldFindPersonsByNationality() {
        Person p1 = createPerson("John", "Doe", 30, "American");
        Person p2 = createPerson("Jane", "Smith", 25, "American");
        Person p3 = createPerson("Pierre", "Dupont", 35, "French");
        personRepository.saveAll(List.of(p1, p2, p3));

        List<Person> americans = personRepository.findByNationality("American");
        assertThat(americans).hasSize(2);
        assertThat(americans).extracting(Person::getFirstName)
                             .containsExactlyInAnyOrder("John", "Jane");
    }

    @Test
    void shouldReturnEmptyListForNonExistentNationality() {
        List<Person> list = personRepository.findByNationality("Atlantian");
        assertThat(list).isEmpty();
    }

    @Test
    void shouldUpdatePerson() {
        Person p = createPerson("Update", "Test", 30, "Original");
        Person saved = personRepository.save(p);
        saved.setAge(31);
        saved.setNationality("Updated");
        Person updated = personRepository.save(saved);

        assertThat(updated.getAge()).isEqualTo(31);
        assertThat(updated.getNationality()).isEqualTo("Updated");
    }

    private Person createPerson(String firstName, String lastName, int age, String nationality) {
        Person person = new Person();
        person.setFirstName(firstName);
        person.setLastName(lastName);
        person.setAge(age);
        person.setNationality(nationality);
        return person;
    }
}