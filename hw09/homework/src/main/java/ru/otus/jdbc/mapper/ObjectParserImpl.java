package ru.otus.jdbc.mapper;

import ru.otus.crm.model.Id;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class ObjectParserImpl<T> implements ObjectParser<T> {
    private final EntityClassMetaData<T> entityClassMetaData;

    public ObjectParserImpl(EntityClassMetaData<T> entityClassMetaData) {

        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public List<T> getObjects(ResultSet resultSet) {
        List<T> list = new ArrayList<>();
        var constructor = entityClassMetaData.getConstructor();
        T instance = null;
        try {
            while (resultSet.next()) {
                instance = constructor.newInstance();
                T finalInstance = instance;
                entityClassMetaData.getAllFields().forEach(field -> {
                    try {
                        String name = field.isAnnotationPresent(Id.class) ? "id" : field.getName();
                        entityClassMetaData.setFieldValue(field, finalInstance, resultSet.getObject(name));
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
            list.add(instance);
        } catch (SQLException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return list;
    }

    @Override
    public Object getIdFieldValue(T object) {
        var field = entityClassMetaData.getIdField();
        return entityClassMetaData.getFieldValue(field, object);
    }

    @Override
    public List<Object> getAllFieldsValue(T object) {
        return getValues(entityClassMetaData.getAllFields(), object);
    }

    @Override
    public List<Object> getFieldsWithoutIdValue(T object) {
        return getValues(entityClassMetaData.getFieldsWithoutId(), object);
    }

    private List<Object> getValues(List<Field> fields, T object) {
        List<Object> list = new ArrayList<>();
        fields.forEach(field -> {
            var value = entityClassMetaData.getFieldValue(field, object);
            list.add(value);
        });
        return list;
    }
}
