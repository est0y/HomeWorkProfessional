package ru.est0y;


import io.grpc.ServerBuilder;
import ru.est0y.service.RemoteSequenceService;

import java.io.IOException;

public class GRPCServer {

    public static final int SERVER_PORT = 8190;

    public static void main(String[] args) throws IOException, InterruptedException {

        var sequenceService = new RemoteSequenceService();

        var server = ServerBuilder
                .forPort(SERVER_PORT)
                .addService(sequenceService).build();
        server.start();
        System.out.println("server waiting for client connections...");
        server.awaitTermination();
    }
}
