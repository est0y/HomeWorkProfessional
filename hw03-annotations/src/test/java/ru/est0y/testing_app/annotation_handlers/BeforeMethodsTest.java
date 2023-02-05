package ru.est0y.testing_app.annotation_handlers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.est0y.testing_app.annotation_handlers.spies.StatsSpy;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class BeforeMethodsTest {
    static class WithMethods {
        public void method1() {
        }

        public void failed() {
            throw new RuntimeException();
        }

        public void method2() {
        }
    }

    List<Method> methodsInOrder() throws NoSuchMethodException {
        Method passed1 = WithMethods.class.getMethod("method1");
        Method failed = WithMethods.class.getMethod("failed");
        Method passed2 = WithMethods.class.getMethod("method2");
        return List.of(passed1, failed, passed2);
    }

    @Test
    void isAllPassed() {
        StatsSpy statsSpy = new StatsSpy();
        BeforeMethods beforeMethods = new BeforeMethods(shuffledMethods(), statsSpy);
        Assertions.assertFalse(beforeMethods.isAllPassed(new WithMethods()));
    }

    List<Method> shuffledMethods() {
        List<Method> methods = Arrays.asList(
                WithMethods.class.getDeclaredMethods()
        );
        Collections.shuffle(methods);
        return methods;
    }

    @Test
    void correctStats() throws NoSuchMethodException {
        StatsSpy statsSpy = new StatsSpy();
        BeforeMethods beforeMethods = new BeforeMethods(methodsInOrder(), statsSpy);
        beforeMethods.isAllPassed(new WithMethods());
        Assertions.assertEquals(1, statsSpy.failedCount);
        Assertions.assertEquals(1, statsSpy.passedCount);
    }
}