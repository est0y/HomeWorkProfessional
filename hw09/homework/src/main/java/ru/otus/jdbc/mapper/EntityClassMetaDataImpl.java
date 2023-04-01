package ru.otus.jdbc.mapper;

import ru.otus.crm.model.Id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class EntityClassMetaDataImpl<T> implements EntityClassMetaData<T> {
    private final Class<T> tClass;

    public EntityClassMetaDataImpl(Class<T> tClass) {
        this.tClass = tClass;
    }

    @Override
    public String getName() {
        return tClass.getSimpleName();
    }

    @Override
    public Constructor<T> getConstructor() {
        try {
            return tClass.getConstructor();
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Field getIdField() {
        Field annotatedField = null;
        for (var field : tClass.getDeclaredFields()) {
            if (field.isAnnotationPresent(Id.class)) {
                if (annotatedField != null) {
                    throw new IllegalStateException(tClass.getName() + " more than 1 id field");
                }
                annotatedField = field;
            }
        }
        if (annotatedField == null) {
            throw new IllegalStateException(tClass.getName() + " don't have field with @Id");
        }
        return annotatedField;
    }

    @Override
    public List<Field> getAllFields() {
        return List.of(tClass.getDeclaredFields());
    }

    @Override
    public List<Field> getFieldsWithoutId() {
        List<Field> noIdFields = new ArrayList<>();
        for (var field : tClass.getDeclaredFields()) {
            if (!field.isAnnotationPresent(Id.class)) {
                noIdFields.add(field);
            }
        }
        return noIdFields;
    }

    public void setFieldValue(Field field, T object, Object value) {
        Method method = getSetter(field);
        try {
            method.invoke(object, value);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public Object getFieldValue(Field field, T object) {
        Method method = getGetter(field);
        try {
            return method.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private Method getSetter(Field field) {
        String methodName = getMethodNameWithPrefix(field, "set");
        try {
            return tClass.getMethod(methodName, field.getType());
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }
    }

    private Method getGetter(Field field) {
        String methodName = getMethodNameWithPrefix(field, "get");
        try {
            return tClass.getMethod(methodName);
        } catch (NoSuchMethodException e) {
            throw new RuntimeException(e);
        }

    }

    private String getMethodNameWithPrefix(Field field, String prefix) {
        var fieldName = field.getName();
        fieldName = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
        return prefix + fieldName;
    }
}
