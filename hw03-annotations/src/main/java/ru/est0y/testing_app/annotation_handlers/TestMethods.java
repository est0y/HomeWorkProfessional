package ru.est0y.testing_app.annotation_handlers;

import ru.est0y.testing_app.annotation_handlers.exceptions.TestFailedException;
import ru.est0y.testing_app.stats.Stats;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;
import java.util.NoSuchElementException;

public class TestMethods extends MethodsWithStatistics<Deque<Method>> {
    public TestMethods(List<Method> methods, Stats stats) {
        super(new ArrayDeque<>(methods), stats);
    }

    public void invokeNext(Object instance) {
        Method method = null;
        try {
            method = methods.pop();
            method.invoke(instance);
            stats.addPassed(method);
        } catch (NoSuchElementException e) {
            throw e;
        } catch (IllegalAccessException | InvocationTargetException e) {
            stats.addFailed(new TestFailedException(method.getName(), e));
        }
    }
}
