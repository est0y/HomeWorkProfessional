package ru.est0y.testing_app.annotation_handlers;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import ru.est0y.testing_app.annotation_handlers.spies.StatsSpy;

import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

class TestMethodsTest {
    public static class WithMethods {
        public void method1() {
        }

        public void failedMethod() {
            throw new RuntimeException();
        }

        public void method2() {
        }
    }

    @Test
    void invokeNext() {
        StatsSpy stats = new StatsSpy();
        List<Method> methods = Arrays.asList(WithMethods.class.getDeclaredMethods());
        Collections.shuffle(methods);
        TestMethods testMethods = new TestMethods(methods, stats);
        methods.forEach(method -> testMethods.invokeNext(new WithMethods()));
        Assertions.assertEquals(2, stats.passedCount);
        Assertions.assertEquals(1, stats.failedCount);
        Assertions.assertTrue(stats.passedMethodNames.contains("method1"));
        Assertions.assertTrue(stats.passedMethodNames.contains("method2"));
    }
}