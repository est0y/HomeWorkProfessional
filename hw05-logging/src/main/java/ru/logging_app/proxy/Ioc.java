package ru.logging_app.proxy;

import ru.logging_app.Log;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class Ioc<T> {
    private final T object;

    public Ioc(T object) {
        this.object = object;
    }

    public T proxy() {
        List<Method> methods = Arrays.stream(object.getClass().getMethods()).filter(method -> method.isAnnotationPresent(Log.class)).toList();
        InvocationHandler handler = new LogHandler(object);
        return (T) Proxy.newProxyInstance(Ioc.class.getClassLoader(),
                object.getClass().getInterfaces(), handler);
    }

    class LogHandler implements InvocationHandler {
        private final T myClass;
        private final Set<String> methods;

        LogHandler(T myClass) {
            this.myClass = myClass;
            this.methods = Arrays.stream(myClass.getClass().getDeclaredMethods()).filter(method -> method.isAnnotationPresent(Log.class)).map(method -> method.getName() + Arrays.toString(method.getParameterTypes()))
                    .collect(Collectors.toSet());

        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if (methods.contains(method.getName()+Arrays.toString(method.getParameterTypes()))) {
                String params;
                if (args == null) {
                    params = "";
                } else {
                    params = Arrays.stream(args).map(Object::toString).toList().toString();
                }

                System.out.println("executed method: " + method.getName() +
                        ", params: " + params);
            }
            return method.invoke(myClass, args);
        }
    }
}
