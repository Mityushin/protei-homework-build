package ru.protei.proxy;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import ru.protei.proxy.DAO.CRUD;
import ru.protei.proxy.DAO.PersonDAO;
import ru.protei.proxy.database.DBConnectionManager;
import ru.protei.proxy.model.Person;

import java.lang.reflect.Proxy;

public class Main {

    private static final Logger log = Logger.getLogger(Main.class);

    private static final Person BOY = new Person()
            .setName("Alex");

    private CRUD personDAO;

    public Main(CRUD personDAO) {
        this.personDAO = personDAO;
    }

    public void run() {
        personDAO.create(BOY);
        personDAO.update(BOY.setName("Gerda"));
    }

    public static void main(String[] args) {

        BasicConfigurator.configure();

        DBConnectionManager dbConnectionManager = new DBConnectionManager();
        PersonDAO personDAO = new PersonDAO(dbConnectionManager);

        CRUD<Person> proxyInstancePersonDAO = (CRUD<Person>) Proxy.newProxyInstance(
                Main.class.getClassLoader(),
                new Class[] {CRUD.class},
                new TimingDynamicInvocationHandler(personDAO));

        new Main(proxyInstancePersonDAO).run();
    }
}
