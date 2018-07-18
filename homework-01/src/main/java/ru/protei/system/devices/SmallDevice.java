package ru.protei.system.devices;

import ru.protei.system.inspection.ServerStatus;

public class SmallDevice extends Device {

    public ServerStatus getStatus() {
        return ServerStatus.ONLINE;
    }

}
