package ru.protei.serialization;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "AdminLogger")
public class AdminLogger extends EventLogger {

    @XmlElement(name = "id")
    private EventLoggerIdEnum id = EventLoggerIdEnum.ADMIN_LOGGER;

    @Override
    public void logEvent() {
        System.out.println("Admin login.");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AdminLogger)) return false;

        AdminLogger that = (AdminLogger) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
