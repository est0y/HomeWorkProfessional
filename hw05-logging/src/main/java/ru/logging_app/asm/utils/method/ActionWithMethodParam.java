package ru.logging_app.asm.utils.method;

import org.objectweb.asm.Type;

@FunctionalInterface
public interface ActionWithMethodParam {
    void execute( Type type,int params);
}
