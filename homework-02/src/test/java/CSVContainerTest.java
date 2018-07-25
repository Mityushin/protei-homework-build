import org.junit.Before;
import org.junit.BeforeClass;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class CSVContainerTest {

    private static CSVContainer container;
    private static List<Person> persons;
    private static Person testPerson;

    @BeforeClass
    public static void setUpClass() {
        testPerson = new Person()
                .setName("Дмитрий")
                .setSex("м")
                .setAge(20)
                .setPhone("+1(234)567-89-01");

        persons = new ArrayList<>();

        persons.add(testPerson);

        persons.add(new Person()
                .setName("Иван")
                .setSex("м")
                .setAge(30)
                .setPhone("+2(345)678-90-12")
        );
        persons.add(new Person()
                .setName("Маргарита")
                .setSex("ж")
                .setAge(25)
                .setPhone("+3(456)789-01-23")
        );

        container = new CSVContainer();
    }

    @Before
    public void setUp() {
        container.setData(new ArrayList<>(persons));
    }

    @org.junit.Test
    public void readFromCsvWithFileName() throws IOException {
        final String FILE_NAME = "test.csv";
        container.readFromCsv(FILE_NAME);

        Iterator<Person> iter = persons.iterator();
        for (Person person : container.getData()) {
            if (!person.equals(iter.next())) {
                fail("Found difference between data from test and data from test.csv");
            }
        }
    }

    @org.junit.Test
    public void removePerson() {
        container.removePerson(testPerson);

        assertFalse(container.getData().contains(testPerson));
    }

    @org.junit.Test
    public void removeAll() {
        List<Person> forRemoving = new ArrayList<>();

        for (Person person : container.getData()) {
            if (person.getAge() < 30) {
                forRemoving.add(person);
            }
        }
        assertTrue(container.removeAll(forRemoving));

        List<Person> data = container.getData();

        for (Person person : forRemoving) {
            assertFalse("Found unextracted data", data.contains(person));
        }
    }

    @org.junit.Test
    public void modifyPerson() {
        Person newPerson = new Person(testPerson)
                .setAge(testPerson.getAge() + 100);

        container.modifyPerson(testPerson, newPerson);

        assertFalse(container.getData().contains(testPerson));
        assertTrue(container.getData().contains(newPerson));
    }
}