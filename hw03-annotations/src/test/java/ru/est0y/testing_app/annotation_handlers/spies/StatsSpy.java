package ru.est0y.testing_app.annotation_handlers.spies;

import ru.est0y.testing_app.stats.Stats;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class StatsSpy extends Stats {
    public int failedCount = 0;
    public int passedCount = 0;
    public List<String> passedMethodNames=new ArrayList<>();
    @Override
    public void addFailed(Exception e) {
        failedCount++;
    }

    @Override
    public void addPassed(Method method) {
        passedMethodNames.add(method.getName());
        passedCount++;
    }
    @Override
    public String toString(){
        return "";
    }
}
