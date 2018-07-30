package ru.protei.serialization;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "OperatorLogger")
public class OperatorLogger extends EventLogger {

    private EventLoggerIdEnum id = EventLoggerIdEnum.OPERATOR_LOGGER;

    @Override
    public void logEvent() {
        System.out.println("Operator login.");
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof OperatorLogger)) return false;

        OperatorLogger that = (OperatorLogger) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return id != null ? id.hashCode() : 0;
    }
}
