package ru.otus;

import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.ListenerPrinterConsole;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.model.Message;
import ru.otus.processor.Processor;
import ru.otus.processor.homework.ProcessorExceptionInEvenSecond;
import ru.otus.processor.homework.ProcessorSwapField11WithField12;

import java.time.LocalDateTime;
import java.util.List;

public class HomeWork {

    /*
     Реализовать to do:
       1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)
       2. Сделать процессор, который поменяет местами значения field11 и field12
       3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
             Секунда должна определяьться во время выполнения.
             Тест - важная часть задания
             Обязательно посмотрите пример к паттерну Мементо!
       4. Сделать Listener для ведения истории (подумайте, как сделать, чтобы сообщения не портились)
          Уже есть заготовка - класс HistoryListener, надо сделать его реализацию
          Для него уже есть тест, убедитесь, что тест проходит
     */

    public static void main(String[] args) {
        /*
           по аналогии с Demo.class
           из элеменов "to do" создать new ComplexProcessor и обработать сообщение
         */
        List<Processor> processors = List.of(new ProcessorSwapField11WithField12(),
                new ProcessorExceptionInEvenSecond(LocalDateTime::now));

        ComplexProcessor complexProcessor = new ComplexProcessor(processors,
                exception -> System.out.println(exception.getMessage()));
        complexProcessor.addListener(new ListenerPrinterConsole());
        var history = new HistoryListener();
        complexProcessor.addListener(history);
        var message = new Message.Builder(1L)
                .field11("11")
                .field12("12")
                .build();

        Message result = complexProcessor.handle(message);
        System.out.println("Message in history " + history.findMessageById(1L));
        System.out.println(result);
    }
}
