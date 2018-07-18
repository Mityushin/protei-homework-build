package ru.protei.watcher;

import ru.protei.watcher.service.PacketProcessingService;
import ru.protei.watcher.service.StatusService;

import java.util.Map;

public class Controller {

    private StatusService statusService;
    private PacketProcessingService packetProcessingService;

    public Controller(StatusService statusService, PacketProcessingService packetProcessingService) {
        this.statusService = statusService;
        this.packetProcessingService = packetProcessingService;
    }

    private boolean isAuthorized(String request) {
        return true;
    }

    private Map<String, String> getStatus() {
        return statusService.getStatus();
    }

    private Map<String, String> getPacketProcessingInfo() {
        return packetProcessingService.getPacketProcessingInfo();
    }

    public String resolveRequest(String request) {

        if (!isAuthorized(request)) {
            return null;
        }

        int command = Integer.parseInt(request);

        switch (command) {
            case 0 : {
                return getStatus().toString();
            }
            case 1 : {
                return getPacketProcessingInfo().toString();
            }
            case 2 : {
                StringBuilder builder = new StringBuilder();

                builder.append(getStatus());
                builder.append(getPacketProcessingInfo());

                return builder.toString();
            }
        }

        return null;
    }
}
