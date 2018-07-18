package ru.protei.watcher;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import ru.protei.watcher.service.PacketProcessingService;
import ru.protei.watcher.service.StatusService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ApplicationServer {
    private static final Logger log = Logger.getLogger(ApplicationServer.class);

    private static final String QUIT_QUERY = "quit()";
    private static final String HELP_QUERY = "help()";
    private static final String HELP_BODY = "Input command number\n" +
            "0 - get online info\n" +
            "1 - get packet processing info\n" +
            "2 - get all info\n" +
            "quit() - quit from program\n" +
            "help() - print this message\n";

    private Controller controller;

    public ApplicationServer(Controller controller) {
        this.controller = controller;
    }

    public static void main(String[] args) {
        BasicConfigurator.configure();

        StatusService statusService = new StatusService();
        PacketProcessingService pPService = new PacketProcessingService();

        Controller controller = new Controller(statusService, pPService);

        new ApplicationServer(controller).run();
    }

    private void run() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            String clientRequest;

            try {
                log.info(HELP_BODY);

                clientRequest = reader.readLine();
                log.info("Get: " + clientRequest);

                if (clientRequest.equals(QUIT_QUERY)) {
                    break;
                }

                if (clientRequest.equals(HELP_QUERY)) {
                    continue;
                }

                String serverResponse = controller.resolveRequest(clientRequest);
                log.info("Send: " + serverResponse);

            } catch (IOException e) {
                log.fatal("Failed to read from input", e);
                log.info("Send: -1");
            }
        }

        try {
            reader.close();
            log.info("Close reader successfully");
        } catch (IOException e) {
            log.fatal("Failed to close reader", e);
        }
    }
}
