package ru.protei.serialization;

import javax.xml.bind.annotation.*;

@XmlTransient
public abstract class EventLogger {

    protected EventLoggerIdEnum id = EventLoggerIdEnum.NOT_SPECIFIED;

    public abstract void logEvent();
}
