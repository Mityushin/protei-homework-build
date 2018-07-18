package ru.protei.system.devices;

import java.util.ArrayList;
import java.util.List;

public class DevicePool {
    private static List<Device> devices;


    public static List<Device> getDevices() {
        if (devices == null) {
            devices = new ArrayList<>();

            devices.add(new BigDevice());
            devices.add(new SmallDevice());
        }

        return devices;
    }
}
