import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

public class CSVContainerTest {

    private CSVContainer container;
    private List<Person> persons;
    private Person testPerson;

    private Field field;

    public CSVContainerTest() throws Exception {
        container = new CSVContainer();

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

        field = container.getClass().getDeclaredField("data");
        field.setAccessible(true);
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
    public void removePerson() throws IllegalAccessException {
        List<Person> compareList = new ArrayList<Person>(persons);
        List<Person> injectIntoContainerList = new ArrayList<Person>(persons);

        field.set(container, injectIntoContainerList);

        compareList.remove(testPerson);
        container.removePerson(testPerson);

        Iterator<Person> iter = compareList.iterator();
        for (Person person : container.getData()) {
            if (!person.equals(iter.next())) {
                fail("Found difference in removing");
            }
        }
    }

    @org.junit.Test
    public void removeAll() throws IllegalAccessException {
        List<Person> compareList = new ArrayList<>(persons);
        List<Person> injectIntoContainerList = new ArrayList<>(persons);

        field.set(container, injectIntoContainerList);

        List<Person> forRemoving = new ArrayList<>();

        for (Person person : compareList) {
            if (person.getAge() < 30) {
                forRemoving.add(person);
            }
        }
        compareList.removeAll(forRemoving);

        forRemoving.clear();
        for (Person person : container.getData()) {
            if (person.getAge() < 30) {
                forRemoving.add(person);
            }
        }
        container.removeAll(forRemoving);

        Iterator<Person> iter = compareList.iterator();
        for (Person person : container.getData()) {
            if (!person.equals(iter.next())) {
                fail("Found difference in removing all");
            }
        }
    }

    @org.junit.Test
    public void modifyPerson() throws IllegalAccessException {
        List<Person> compareList = new ArrayList<>(persons);
        List<Person> injectIntoContainerList = new ArrayList<>(persons);

        field.set(container, injectIntoContainerList);

        Person newPerson = new Person()
                .setName(testPerson.getName())
                .setSex(testPerson.getSex())
                .setAge(testPerson.getAge() + 100)
                .setPhone(testPerson.getPhone());

        compareList.set(compareList.indexOf(testPerson), newPerson);
        container.modifyPerson(testPerson, newPerson);

        Iterator<Person> iter = compareList.iterator();
        for (Person person : container.getData()) {
            if (!person.equals(iter.next())) {
                fail("Found difference in removing all");
            }
        }
    }
}