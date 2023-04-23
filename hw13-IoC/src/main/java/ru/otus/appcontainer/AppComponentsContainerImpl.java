package ru.otus.appcontainer;

import javassist.bytecode.ClassFile;
import lombok.NonNull;
import lombok.SneakyThrows;
import org.reflections.Reflections;
import org.reflections.scanners.Scanner;
import ru.otus.appcontainer.api.AppComponent;
import ru.otus.appcontainer.api.AppComponentsContainer;
import ru.otus.appcontainer.api.AppComponentsContainerConfig;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class AppComponentsContainerImpl implements AppComponentsContainer {

    private final List<Object> appComponents = new ArrayList<>();
    private final Map<String, Object> appComponentsByName = new HashMap<>();

    public AppComponentsContainerImpl(Class<?> initialConfigClass) {
        processConfig(initialConfigClass);
    }

    public AppComponentsContainerImpl(Class<?>... initialConfigClasses) {
        if (initialConfigClasses.length == 0) {
            throw new IllegalArgumentException("Expected 1 or more configuration classes, but received 0");
        }
        Arrays.stream(initialConfigClasses).
                sorted(Comparator.comparingInt(
                        config -> config.getAnnotation(AppComponentsContainerConfig.class).order()
                )).forEach(this::processConfig);
    }

    public AppComponentsContainerImpl(String path) {
        this(new Reflections(path).getTypesAnnotatedWith(AppComponentsContainerConfig.class).toArray(new Class<?>[0]));
    }

    @SneakyThrows
    private void processConfig(Class<?> configClass) {
        checkConfigClass(configClass);
        // You code here...
        var instance = configClass.getConstructor().newInstance();
        Arrays.stream(configClass.getMethods())
                .filter(method -> method.isAnnotationPresent(AppComponent.class))
                .sorted(Comparator.comparingInt(method -> method.getAnnotation(AppComponent.class).order()))
                .forEach(method -> addComponent(instance, method));
    }

    @SneakyThrows
    private void addComponent(Object instance, Method method) {
        var params = Arrays.stream(method.getParameterTypes()).map(this::getAppComponent).toArray();
        var component = method.invoke(instance, params);
        String componentName = method.getAnnotation(AppComponent.class).name();
        if (appComponentsByName.containsKey(componentName)) {
            throw new IllegalStateException(String.format("More 1 component with name: s%", componentName));
        }
        appComponents.add(component);
        appComponentsByName.put(componentName, component);
    }

    private void checkConfigClass(Class<?> configClass) {
        if (!configClass.isAnnotationPresent(AppComponentsContainerConfig.class)) {
            throw new IllegalArgumentException(String.format("Given class is not config %s", configClass.getName()));
        }
    }

    @Override
    public <C> C getAppComponent(Class<C> componentClass) {
        List<Object> components = appComponents.stream().filter(object -> componentClass.isAssignableFrom(object.getClass())).toList();
        if (components.size() == 0) {
            throw new IllegalArgumentException(String.format("Component with type %s not found", componentClass.getName()));
        }
        if (components.size() > 1) {
            throw new IllegalArgumentException(String.format("More than 1 components: %s", components.toString()));
        }
        return (C) components.get(0);
    }

    @Override
    public <C> C getAppComponent(String componentName) {
        var component = appComponentsByName.get(componentName);
        if (component == null) {
            throw new IllegalArgumentException(String.format("Component with name %s not found", componentName));
        }
        return (C) component;
    }
}
