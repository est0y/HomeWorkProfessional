package ru.est0y.testing_app.annotation_handlers;

import ru.est0y.testing_app.annotation_handlers.exceptions.CreateInstanceException;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class AnnotatedClass {
    private final Class<?> annotatedClass;

    public AnnotatedClass(Class<?> annotatedClass) {
        this.annotatedClass = annotatedClass;
    }

    public Object newInstance() {
        try {
            return annotatedClass.getConstructor().newInstance();
        } catch (Exception e) {
            throw new CreateInstanceException(annotatedClass.getName(), e);
        }
    }

    public Map<Class<? extends Annotation>, List<Method>>
    annotationMethods(List<Class<? extends Annotation>> annotations) {
        Map<Class<? extends Annotation>, List<Method>> map = emptyMap(annotations);
        Arrays.stream(annotatedClass.getDeclaredMethods()).forEach(method -> {
            method.setAccessible(true);
            annotations.stream().filter(method::isAnnotationPresent).forEach(
                    annotation -> addAnnotatedMethod(map, annotation, method)
            );
        });
        return map;
    }

    private Map<Class<? extends Annotation>, List<Method>>
    emptyMap(List<Class<? extends Annotation>> annotations) {
        Map<Class<? extends Annotation>, List<Method>> map = new HashMap<>();
        annotations.forEach(annotation -> map.put(annotation, new ArrayList<>()));
        return map;
    }

    private void addAnnotatedMethod(Map<Class<? extends Annotation>, List<Method>> map,
                                    Class<? extends Annotation> annotation, Method method) {
        List<Method> methods = map.get(annotation);
        methods.add(method);
    }
}
