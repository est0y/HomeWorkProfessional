package ru.est0y.service;

import ru.est0y.generated.Range;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class ClientService {

    public void run(AtomicLong lastServerValue, Range range) throws InterruptedException {
        long currentValue = 0;
        for (var i = range.getInitialValue(); i < range.getFinalValue() + 1; i++) {
            Thread.sleep(TimeUnit.SECONDS.toMillis(1));
            currentValue = currentValue + lastServerValue.getAndSet(0) + 1;
            System.out.println("currentValue:" + currentValue);
        }
    }


}
