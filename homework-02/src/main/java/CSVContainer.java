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
        data = new ArrayList<>();
    }

    public void readFromCsv() throws IOException {
        readFromCsv(FILE_NAME);
    }

    public void readFromCsv(String fileName) throws IOException {
        data.clear();


        try (InputStream is = getClass().getResourceAsStream("/" + fileName)) {

            CSVFormat csvFormat = CSVFormat.DEFAULT.withHeader(PersonField.class);

            try (CSVParser parser = CSVParser.parse(is, Charset.defaultCharset(), csvFormat)) {

                System.out.println(parser);
                for (CSVRecord record : parser.getRecords()) {

                    Person person = new Person();

                    if (record.isSet(PersonField.NAME.name())) {
                        person.setName(record.get(PersonField.NAME));
                    }

                    if (record.isSet(PersonField.SEX.name())) {
                        person.setSex(record.get(PersonField.SEX));
                    }

                    if (record.isSet(PersonField.AGE.name())) {
                        person.setAge(Integer.parseInt(record.get(PersonField.AGE)));
                    }

                    if (record.isSet(PersonField.PHONE.name())) {
                        person.setPhone(record.get(PersonField.PHONE));
                    }

                    data.add(person);
                }
            }
        }
    }

    public List<Person> getData() {
        return data;
    }

    public void setData(List<Person> data) {
        this.data = data;
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

}
