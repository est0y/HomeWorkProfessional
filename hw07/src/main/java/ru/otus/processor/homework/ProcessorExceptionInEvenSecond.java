package ru.otus.processor.homework;

import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.LocalDateTime;

import static java.lang.Thread.sleep;

public class ProcessorExceptionInEvenSecond implements Processor {
    private final DateTimeProvider dateTimeProvider;


    public ProcessorExceptionInEvenSecond(DateTimeProvider dateTimeProvider) {
        this.dateTimeProvider = dateTimeProvider;
    }

    @Override
    public Message process(Message message) {
        var second = dateTimeProvider.getDate().getSecond();
        if (second % 2 != 0) {
            try {
                sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        throw new RuntimeException(String.format("Выбрашено исключение на %d секунде",dateTimeProvider.getDate().getSecond()));
    }
}
