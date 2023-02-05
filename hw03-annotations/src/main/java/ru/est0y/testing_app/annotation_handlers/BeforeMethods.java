package ru.est0y.testing_app.annotation_handlers;

import ru.est0y.testing_app.stats.Stats;
import ru.est0y.testing_app.annotation_handlers.exceptions.BeforeMethodException;

import java.lang.reflect.Method;
import java.util.List;

public class BeforeMethods extends MethodsWithStatistics<List<Method>> {
    public BeforeMethods(List<Method> methods, Stats stats) {
        super(methods, stats);
    }

    public boolean isAllPassed(Object instance) {
        for (Method method : methods) {
            try {
                method.invoke(instance);
                stats.addPassed(method);
            } catch (Exception e) {
                stats.addFailed(new BeforeMethodException(method.getName(), e));
                return false;
            }
        }
        return true;
    }
}
