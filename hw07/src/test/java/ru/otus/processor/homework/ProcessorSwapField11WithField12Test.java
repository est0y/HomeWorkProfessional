package ru.otus.processor.homework;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProcessorSwapField11WithField12Test {

    @Test
    void process() {
       var message=new Message.Builder(1).field11("11").field12("12").build();
       var changedMessage=new ProcessorSwapField11WithField12().process(message);
        Assertions.assertEquals("12",changedMessage.getField11());
        Assertions.assertEquals("11",changedMessage.getField12());
    }
}