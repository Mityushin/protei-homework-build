import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class CSVContainer {

    private static final String FILE_NAME = "persons.csv";
    private List<Person> data;

    public CSVContainer() {
        data = new ArrayList<Person>();
    }

    public void readFromCsv() throws IOException {
        readFromCsv(FILE_NAME);
    }

    public void readFromCsv(String fileName) throws IOException {
        data.clear();

        InputStream is = getClass().getResourceAsStream("/" + fileName);
        CSVParser parser = CSVParser.parse(is, Charset.defaultCharset(), CSVFormat.DEFAULT);

        for (CSVRecord record : parser.getRecords()) {
            Person person = new Person()
                    .setName(record.get(0))
                    .setSex(record.get(1))
                    .setAge(Integer.parseInt(record.get(2)))
                    .setPhone(record.get(3));
            data.add(person);
        }

        parser.close();
        is.close();
    }

    public List<Person> getData() {
        return data;
    }

    public void printData() {
        System.out.println("Printing data...");
        for (Person person : data) {
            System.out.println(person);
        }
    }

    public boolean removePerson(Person person) {
        return data.remove(person);
    }

    public boolean removeAll(List<Person> persons) {
        return data.removeAll(persons);
    }

    public Person modifyPerson(Person oldPerson, Person newPerson) {
        return data.set(data.indexOf(oldPerson), newPerson);
    }

    public static void main(String[] args) throws IOException {
        CSVContainer container = new CSVContainer();
        container.readFromCsv();

        container.printData();

        List<Person> forRemoving = new ArrayList<Person>();

        for (Person person : container.getData()) {
            if (person.getName().equals("Дмитрий")) {
                Person newPerson = new Person()
                        .setName(person.getName())
                        .setSex(person.getSex())
                        .setAge(person.getAge() - 5)
                        .setPhone(person.getPhone());
                container.modifyPerson(person, newPerson);
            }
        }
        container.printData();

        for (Person person : container.getData()) {
            if (person.getAge() < 22) {
                forRemoving.add(person);
            }
        }
        container.removeAll(forRemoving);

        container.printData();
    }
}
