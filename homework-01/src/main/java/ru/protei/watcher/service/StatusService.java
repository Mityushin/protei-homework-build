package ru.protei.watcher.service;

import ru.protei.system.devices.Device;
import ru.protei.system.devices.DevicePool;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatusService {

    public Map<String, String> getStatus() {
        Map<String, String> statusInfo = new HashMap<>();

        for (Device device : DevicePool.getDevices()) {
            statusInfo.put(Device.class.getName() + device.hashCode(), device.getStatus().name());
        }

        return statusInfo;
    }
}
