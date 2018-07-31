package ru.protei.serialization;

import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.json.JettisonMappedXmlDriver;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

import static org.junit.Assert.*;

public class UserTest {

    private static final String FILE_NAME = "test.xml";
    private static final String FILE_NAME_ADMIN_XML = "test_admin.xml";
    private static final String FILE_NAME_ADMIN_JSON = "test_admin.json";
    private static final String FILE_NAME_OPERATOR_XML = "test_operator.xml";
    private static final String FILE_NAME_OPERATOR_JSON = "test_operator.json";

    private static final User ADMIN = new User<String>()
            .setId("1")
            .setName("admin")
            .setPassword("password")
            .setFolders(Arrays.asList("folder1", "folder2"))
            .setCreator(null)
            .setEventLogger(new AdminLogger());

    private static final User OPERATOR = new User<Integer>()
            .setId(1)
            .setName("operator")
            .setPassword("password")
            .setFolders(Arrays.asList("folder31", "folder32"))
            .setCreator(ADMIN)
            .setEventLogger(new OperatorLogger());


    @BeforeClass
    public static void setUpClass() throws Exception {
        JAXBContext context = JAXBContext.newInstance(User.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream()) {

            marshaller.marshal(ADMIN, byteStream);

            try (OutputStream outputStream = new FileOutputStream(FILE_NAME_ADMIN_XML)) {
                byteStream.writeTo(outputStream);
            }
        }

        try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream()) {

            marshaller.marshal(OPERATOR, byteStream);

            try (OutputStream outputStream = new FileOutputStream(FILE_NAME_OPERATOR_XML)) {
                byteStream.writeTo(outputStream);
            }
        }

        XStream xstream = new XStream(new JettisonMappedXmlDriver());

        try (PrintWriter writer = new PrintWriter(FILE_NAME_ADMIN_JSON, "UTF-8")) {
                writer.print(xstream.toXML(ADMIN));
        }

        try (PrintWriter writer = new PrintWriter(FILE_NAME_OPERATOR_JSON, "UTF-8")) {
                writer.print(xstream.toXML(OPERATOR));
        }
    }

    @AfterClass
    public static void tearDown() throws Exception {
        Files.delete(Paths.get(FILE_NAME_ADMIN_XML));
        Files.delete(Paths.get(FILE_NAME_OPERATOR_XML));

        Files.delete(Paths.get(FILE_NAME_ADMIN_JSON));
        Files.delete(Paths.get(FILE_NAME_OPERATOR_JSON));
    }

    @Test
    public void serializeJAXB() throws JAXBException, IOException {

        JAXBContext context = JAXBContext.newInstance(User.class);
        Marshaller marshaller = context.createMarshaller();
        marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

        try (ByteArrayOutputStream byteStream = new ByteArrayOutputStream()) {

            marshaller.marshal(ADMIN, byteStream);

            try (OutputStream outputStream = new FileOutputStream(FILE_NAME)) {
                byteStream.writeTo(outputStream);
            }
        }

        File file = new File(FILE_NAME);
        assertTrue(FileUtils.contentEquals(file, new File(FILE_NAME_ADMIN_XML)));
        assertFalse(FileUtils.contentEquals(file, new File(FILE_NAME_OPERATOR_XML)));

        Files.delete(Paths.get(FILE_NAME));
    }

    @Test
    public void deserializeJAXB() throws JAXBException, IOException {

        JAXBContext context = JAXBContext.newInstance(User.class);
        Unmarshaller unmarshaller = context.createUnmarshaller();

        try (InputStream inputStream = UserTest.class.getResourceAsStream("/" + FILE_NAME_ADMIN_XML)) {

            User user = (User) unmarshaller.unmarshal(inputStream);
            assertEquals(ADMIN, user);
            assertNotEquals(OPERATOR, user);
        }

        try (InputStream inputStream = UserTest.class.getResourceAsStream("/" + FILE_NAME_OPERATOR_XML)) {

            User user = (User) unmarshaller.unmarshal(inputStream);
            assertEquals(OPERATOR, user);
            assertNotEquals(ADMIN, user);
        }
    }

    @Test
    public void serializeXStream() throws IOException {
//        XStream xstream = new XStream(new JsonHierarchicalStreamDriver());
        XStream xstream = new XStream(new JettisonMappedXmlDriver());

        xstream.autodetectAnnotations(true);
        try (PrintWriter writer = new PrintWriter(FILE_NAME, "UTF-8")) {
            writer.print(xstream.toXML(ADMIN));
        }

        File file = new File(FILE_NAME);
        assertTrue(FileUtils.contentEquals(file, new File(FILE_NAME_ADMIN_JSON)));
        assertFalse(FileUtils.contentEquals(file, new File(FILE_NAME_OPERATOR_JSON)));

        Files.delete(Paths.get(FILE_NAME));
    }

    @Test
    public void deserializeXStream() {

        XStream xstream = new XStream(new JettisonMappedXmlDriver());



        User user = (User) xstream.fromXML(new File(FILE_NAME_ADMIN_JSON));
        assertEquals(user, ADMIN);
        assertNotEquals(user, OPERATOR);

        user = (User) xstream.fromXML(new File(FILE_NAME_OPERATOR_JSON));
        assertEquals(user, OPERATOR);
        assertNotEquals(user, ADMIN);
    }

}