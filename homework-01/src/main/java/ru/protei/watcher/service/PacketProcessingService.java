package ru.protei.watcher.service;

import ru.protei.system.devices.Device;
import ru.protei.system.devices.DevicePool;
import ru.protei.system.inspection.Inspectable;

import java.util.HashMap;
import java.util.Map;

public class PacketProcessingService {

    public Map<String, String> getPacketProcessingInfo() {
        Map<String, String> packetProcessingInfo = new HashMap<>();

        for (Device device : DevicePool.getDevices()) {
            if (device instanceof Inspectable) {
                packetProcessingInfo.put(
                        Device.class.getName() + device.hashCode(),
                        ((Inspectable) device).getPacketProcessingInfo().toString()
                );
            }
        }

        return packetProcessingInfo;
    }
}
