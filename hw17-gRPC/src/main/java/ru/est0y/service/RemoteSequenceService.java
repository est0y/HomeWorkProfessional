package ru.est0y.service;

import io.grpc.stub.StreamObserver;
import ru.est0y.generated.*;
import ru.est0y.generated.Number;

import java.util.concurrent.TimeUnit;

public class RemoteSequenceService extends RemoteServiceGrpc.RemoteServiceImplBase {
    @Override
    public void getNumbers(Range range, StreamObserver<Number> responseObserver) {
        var initialValue = range.getInitialValue();
        var finalValue = range.getFinalValue();
        for (var i = initialValue; i < finalValue + 1; i++) {
            try {
                Thread.sleep(TimeUnit.SECONDS.toMillis(2));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            responseObserver.onNext(Number.newBuilder().setValue(i).build());
        }
        responseObserver.onCompleted();
    }


}
