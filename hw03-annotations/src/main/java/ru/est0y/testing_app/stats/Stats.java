package ru.est0y.testing_app.stats;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;


public abstract class Stats {
    protected   int countMethods;
    protected  final List<Method> passed = new ArrayList<>();
    protected  final List<Exception> failed = new ArrayList<>();
    public void setCountMethods(int value){
        countMethods=value;
    }
    public void addPassed(Method method) {
        passed.add(method);
    }
    public void printStackTrace(){
        failed.forEach(Throwable::printStackTrace);
    }
    public void addFailed(Exception e) {
        failed.add(e);
    }

    public int getCountMethods() {
        return countMethods;
    }

    public List<Exception> getFailed() {
        return failed;
    }

    public List<Method> getPassed() {
        return passed;
    }

    @Override
    abstract public String toString() ;
}
