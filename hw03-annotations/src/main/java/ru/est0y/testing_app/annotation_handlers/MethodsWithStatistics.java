package ru.est0y.testing_app.annotation_handlers;

import ru.est0y.testing_app.stats.Stats;

import java.lang.reflect.Method;
import java.util.Collection;

public abstract class MethodsWithStatistics<T extends Collection<Method>> {
    protected final T methods;
    protected final Stats stats;

    protected MethodsWithStatistics(T methods, Stats stats) {
        this.methods = methods;
        this.stats = stats;
        this.stats.setCountMethods(methods.size());
    }

    public Stats getStats() {
        return stats;
    }
}
