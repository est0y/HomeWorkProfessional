package ru.est0y.testing_app.annotation_handlers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.est0y.testing_app.annotation_handlers.spies.StatsSpy;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class AfterMethodsTest {
    static class WithMethods {
        public void method1() {
        }

        public void failed() {
            throw new RuntimeException();
        }

        public void method2() {
        }
    }

    List<Method> shuffledMethods() {
        List<Method> methods = Arrays.asList(
                WithMethods.class.getDeclaredMethods()
        );
        Collections.shuffle(methods);
        return methods;
    }

    @Test
    void isAllPassed() {
        StatsSpy statsSpy = new StatsSpy();
        BeforeMethods beforeMethods = new BeforeMethods(shuffledMethods(), statsSpy);
       Assertions.assertFalse(beforeMethods.isAllPassed(new WithMethods()));
    }
}