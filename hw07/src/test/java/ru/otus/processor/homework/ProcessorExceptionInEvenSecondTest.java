package ru.otus.processor.homework;

import org.junit.jupiter.api.Test;
import org.mockito.internal.util.Timer;
import ru.otus.model.Message;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;

class ProcessorExceptionInEvenSecondTest {
    double getMethodTimeInSec(Runnable method) {
        double start = System.nanoTime();
        method.run();
        return (System.nanoTime() - start) / 1000_000_000;
    }

    LocalDateTime getLocalDateTimeWithSecond(int second) {
        return LocalDateTime.of(2023, Month.DECEMBER, 1, 10, 10, second);
    }


    @Test
    void process() {
        var message=mock(Message.class);
        LocalDateTime oddSecond = getLocalDateTimeWithSecond(1);
        var executionTime = getMethodTimeInSec(() -> assertThrows(RuntimeException.class,
                () -> new ProcessorExceptionInEvenSecond(() -> oddSecond).process(message)));
        assertTrue(executionTime >= 1);
        LocalDateTime evenSecond = getLocalDateTimeWithSecond(2);
        executionTime = getMethodTimeInSec(() -> assertThrows(RuntimeException.class,
                () -> new ProcessorExceptionInEvenSecond(() -> evenSecond).process(message)));
        assertTrue(executionTime < 1);
    }
}