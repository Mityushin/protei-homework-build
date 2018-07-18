package ru.protei.system.inspection;

import java.util.Map;

public class PacketProcessingInfo {
    private String inputPacket;
    private Map<String, String> packetMap;

    public PacketProcessingInfo() {
    }

    public PacketProcessingInfo setInputPacket(String inputPacket) {
        this.inputPacket = inputPacket;
        return this;
    }

    public PacketProcessingInfo setPacketMap(Map<String, String> packetMap) {
        this.packetMap = packetMap;
        return this;
    }

    public String getInputPacket() {
        return inputPacket;
    }

    public Map<String, String> getPacketMap() {
        return packetMap;
    }

    @Override
    public String toString() {
        return "PacketProcessingInfo{" +
                "inputPacket='" + inputPacket + '\'' +
                ", packetMap=" + packetMap +
                '}';
    }
}
