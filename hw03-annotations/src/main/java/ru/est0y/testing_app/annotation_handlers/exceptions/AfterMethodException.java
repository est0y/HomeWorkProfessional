package ru.est0y.testing_app.annotation_handlers.exceptions;

public class AfterMethodException extends RuntimeException{
    public AfterMethodException (String methodName, Exception e){
        super("Method with @After: "+methodName+"() threw an exception",e);
    }
}
