package ru.est0y;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;

import ru.est0y.generated.Number;
import ru.est0y.generated.Range;
import ru.est0y.generated.RemoteServiceGrpc;
import ru.est0y.service.ClientService;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicLong;

public class GRPCClient {

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8190;
    private final AtomicLong lastServerValue = new AtomicLong();

    public static void main(String[] args) throws InterruptedException {
        var client = new GRPCClient();
        var channel = client.channel();
        var stub = RemoteServiceGrpc.newStub(channel);
        CountDownLatch latch = new CountDownLatch(1);
        client.getNumbers(stub, client.range(0, 30), latch);
        var clientService = new ClientService();
        clientService.run(
                client.lastServerValue,
                client.range(0, 50)
        );
        latch.await();
        channel.shutdown();
    }

    private ManagedChannel channel() {
        return ManagedChannelBuilder
                .forAddress(GRPCClient.SERVER_HOST, GRPCClient.SERVER_PORT)
                .usePlaintext()
                .build();
    }

    private void getNumbers(RemoteServiceGrpc.RemoteServiceStub stub,
                            Range range,
                            CountDownLatch latch) {
        stub.getNumbers(range, new StreamObserver<>() {
            @Override
            public void onNext(Number value) {
                System.out.println("Value from server:" + value.getValue());
                lastServerValue.set(value.getValue());
            }

            @Override
            public void onError(Throwable t) {
                t.printStackTrace();
            }

            @Override
            public void onCompleted() {
                System.out.println("Request completed");
                latch.countDown();
            }
        });
    }

    Range range(int initValue, int endValue) {
        return Range.newBuilder()
                .setInitialValue(initValue).
                setFinalValue(endValue).build();
    }
}
