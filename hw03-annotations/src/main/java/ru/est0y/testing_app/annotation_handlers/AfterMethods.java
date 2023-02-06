package ru.est0y.testing_app.annotation_handlers;

import ru.est0y.testing_app.stats.Stats;
import ru.est0y.testing_app.annotation_handlers.exceptions.AfterMethodException;

import java.lang.reflect.Method;
import java.util.List;

public class AfterMethods extends MethodsWithStatistics<List<Method>> {

    public AfterMethods(List<Method> methods, Stats stats) {
        super(methods, stats);
    }

    public boolean isAllPassed(Object instance) {
        AfterMethodException exception = null;
        for (Method method : methods) {
            try {
                method.invoke(instance);
            } catch (Exception e) {
                exception = new AfterMethodException(method.getName(), e);
                stats.addFailed(exception);
            }
        }
        if (exception != null) {
            return false;
        }
        return true;
    }
}
