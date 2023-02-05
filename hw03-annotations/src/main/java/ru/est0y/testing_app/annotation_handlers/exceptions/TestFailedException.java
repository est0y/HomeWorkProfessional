package ru.est0y.testing_app.annotation_handlers.exceptions;

public class TestFailedException extends RuntimeException{
    public TestFailedException(String methodName, Exception e){
        super("Method with @Test: "+methodName+"() threw an exception",e);
    }
}
