package ru.est0y.testing_app;

import ru.est0y.testing_app.annotation_handlers.*;
import ru.est0y.testing_app.annotations.*;
import ru.est0y.testing_app.stats.FailedMethodsStats;
import ru.est0y.testing_app.stats.Stats;
import ru.est0y.testing_app.stats.TestStats;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.*;

public class TestsRunner {

    public void run(String className) throws ClassNotFoundException {
        AnnotatedClass annotatedClass = new AnnotatedClass(Class.forName(className));
        Map<Class<? extends Annotation>, List<Method>> map = annotatedClass.annotationMethods(
                List.of(Before.class, Test.class, After.class)
        );
        BeforeMethods beforeMethods = new BeforeMethods(map.get(Before.class), new FailedMethodsStats());
        TestMethods testMethods = new TestMethods(map.get(Test.class), new TestStats());
        AfterMethods afterMethods = new AfterMethods(map.get(After.class), new FailedMethodsStats());

        runAllTests(annotatedClass, beforeMethods, testMethods, afterMethods);
        printStats(List.of(beforeMethods.getStats(), testMethods.getStats(), afterMethods.getStats()));
    }


    private void runAllTests(AnnotatedClass annotatedClass,
                             BeforeMethods beforeMethods,
                             TestMethods testMethods,
                             AfterMethods afterMethods) {
        while (true) {
            Object instance = annotatedClass.newInstance();
            try {
                if (!beforeMethods.isAllPassed(instance)) {
                    break;
                }
                testMethods.invokeNext(instance);
            } catch (NoSuchElementException e) {
                break;
            } finally {
                if (!afterMethods.isAllPassed(instance)) {
                    break;
                }
            }
        }
    }


    private void printStats(List<Stats> statistics) {
        statistics.forEach(System.out::println);
        statistics.forEach(Stats::printStackTrace);
    }

}
