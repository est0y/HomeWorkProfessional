package ru.est0y.testing_app.annotation_handlers.exceptions;

public class CreateInstanceException extends RuntimeException{
    public CreateInstanceException(String className, Exception e){
        super("Instance of "+className+" class has not been created");
    }
}
