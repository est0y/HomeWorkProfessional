package ru.est0y.testing_app.annotation_handlers.exceptions;

public class BeforeMethodException extends RuntimeException {
    public BeforeMethodException(String methodName, Exception e){
        super("Method with @Before: "+methodName+"() threw an exception",e);
    }

}
