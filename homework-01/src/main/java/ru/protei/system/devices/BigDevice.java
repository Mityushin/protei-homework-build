package ru.protei.system.devices;

import ru.protei.system.inspection.Inspectable;
import ru.protei.system.inspection.PacketProcessingInfo;
import ru.protei.system.inspection.ServerStatus;

import java.util.HashMap;

public class BigDevice extends Device implements Inspectable {

    public ServerStatus getStatus() {
        return ServerStatus.ONLINE;
    }

    public PacketProcessingInfo getPacketProcessingInfo() {

        String inputPacket = "hello";

        HashMap<String, String> packetMap = new HashMap<String, String>();
        packetMap.put("value", inputPacket);

        PacketProcessingInfo ppInfo = new PacketProcessingInfo()
                .setInputPacket(inputPacket)
                .setPacketMap(packetMap);

        return ppInfo;
    }

}
