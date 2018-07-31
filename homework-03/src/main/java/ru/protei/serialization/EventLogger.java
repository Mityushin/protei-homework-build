package ru.protei.serialization;

public abstract class EventLogger {

    protected EventLoggerIdEnum id = EventLoggerIdEnum.NOT_SPECIFIED;

    public abstract void logEvent();
}
