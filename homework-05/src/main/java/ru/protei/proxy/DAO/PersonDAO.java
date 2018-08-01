package ru.protei.proxy.DAO;

import org.apache.log4j.Logger;
import ru.protei.proxy.annotation.LogTimingMetric;
import ru.protei.proxy.database.DBConnectionManager;
import ru.protei.proxy.model.Person;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonDAO implements CRUD<Person> {
    private static final Logger log = Logger.getLogger(PersonDAO.class);

    private DBConnectionManager dbConnectionManager;

    private static final String SQL_TABLE_COLUMN_ID = "ID";
    private static final String SQL_TABLE_COLUMN_NAME = "NAME";
    private static final String SQL_CREATE_TABLE_PERSON = "CREATE TABLE PERSON (" +
            "ID INTEGER NOT NULL AUTO_INCREMENT, " +
            "NAME VARCHAR(20) NOT NULL)";
    private static final String SQL_FIND_PERSON_BY_NAME = "SELECT * FROM PERSON WHERE PERSON.NAME = ?";
    private static final String SQL_CREATE_PERSON = "INSERT INTO PERSON (NAME) VALUES (?)";
    private static final String SQL_UPDATE_PERSON_NAME_BY_ID = "UPDATE PERSON SET PERSON.NAME = ? WHERE PERSON.ID = ?";
    private static final String SQL_DELETE_PERSON_BY_NAME = "DELETE FROM PERSON WHERE PERSON.NAME = ?";
    private static final String SQL_CHECK_EXISTS_BY_ID = "SELECT * FROM PERSON WHERE PERSON.ID = ?";

    public PersonDAO(DBConnectionManager dbConnectionManager) {

        this.dbConnectionManager = dbConnectionManager;

        try {
            Connection connection = this.dbConnectionManager.getConnection();

            Statement stmt = connection.createStatement();

            stmt.executeUpdate(SQL_CREATE_TABLE_PERSON);
            log.info("Table PERSON created");

        } catch (SQLException e) {
            log.fatal("Can't create table PERSON", e);
        }
    }

    @LogTimingMetric
    public boolean create(Person person) {
        try {
            Connection connection = dbConnectionManager.getConnection();

            PreparedStatement pstmt = connection.prepareStatement(SQL_CREATE_PERSON);

            pstmt.setString(1, person.getName());

            return pstmt.executeUpdate() != 0;

        } catch (SQLException e) {
            log.error("Error create");
        }
        return false;
    }

    public List<Person> findByName(Person person) {
        try {
            Connection connection = dbConnectionManager.getConnection();

            PreparedStatement pstmt = connection.prepareStatement(SQL_FIND_PERSON_BY_NAME);

            pstmt.setString(1, person.getName());

            ResultSet rs = pstmt.executeQuery();

            List<Person> resultList = new ArrayList<Person>();

            while (rs.next()) {
                Person foundPerson = new Person(rs.getInt(SQL_TABLE_COLUMN_ID));
                foundPerson.setName(rs.getString(SQL_TABLE_COLUMN_NAME));
                resultList.add(foundPerson);
            }

            return resultList;

        } catch (SQLException e) {
            log.error("Error create");
        }
        return null;
    }

    public boolean update(Person person) {
        try {
            Connection connection = dbConnectionManager.getConnection();

            PreparedStatement pstmt = connection.prepareStatement(SQL_UPDATE_PERSON_NAME_BY_ID);

            pstmt.setString(1, person.getName());
            pstmt.setString(2, String.valueOf(person.getId()));

            return pstmt.executeUpdate() != 0;

        } catch (SQLException e) {
            log.error("Error update");
        }
        return false;
    }

    public boolean delete(Person person) {
        try {
            Connection connection = dbConnectionManager.getConnection();

            PreparedStatement pstmt = connection.prepareStatement(SQL_DELETE_PERSON_BY_NAME);

            pstmt.setString(1, String.valueOf(person.getName()));

            return pstmt.executeUpdate() != 0;

        } catch (SQLException e) {
            log.error("Error delete", e);
        }
        return false;
    }

    public boolean isExists(Person person) {
        try {
            Connection connection = dbConnectionManager.getConnection();

            PreparedStatement pstmt = connection.prepareStatement(SQL_CHECK_EXISTS_BY_ID);

            pstmt.setString(1, String.valueOf(person.getId()));

            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                return true;
            }

        } catch (SQLException e) {
            log.error("Error exists", e);
        }
        return false;
    }
}
