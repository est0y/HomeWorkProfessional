package ru.otus.jdbc.mapper;

import java.sql.ResultSet;
import java.util.List;

public interface ObjectParser<T> {
    List<T> getObjects(ResultSet resultSet);
    Object getIdFieldValue(T object);

    List<Object> getAllFieldsValue(T object);

    List<Object> getFieldsWithoutIdValue(T object);
}
