package ru.otus.jdbc.mapper;

import java.lang.reflect.Field;
import java.util.stream.Collectors;

public class EntitySQLMetaDataImpl implements EntitySQLMetaData {
    private final EntityClassMetaData<?> entityClassMetaData;

    public EntitySQLMetaDataImpl(EntityClassMetaData<?> entityClassMetaData) {
        this.entityClassMetaData = entityClassMetaData;
    }

    @Override
    public String getSelectAllSql() {
        return String.format("select * from %s;", entityClassMetaData.getName());
    }

    @Override
    public String getSelectByIdSql() {
        return String.format("select * from %s where id=?", entityClassMetaData.getName());
    }

    @Override
    public String getInsertSql() {
        return String.format("insert into %s  values(default,%s)", entityClassMetaData.getName(), withOutIdWildCards());

    }

    @Override
    public String getUpdateSql() {
        return String.format("update %s set %s where id=?", entityClassMetaData.getName(), fieldsForUpdate());
    }

    private String fieldsForUpdate() {
        return entityClassMetaData.getFieldsWithoutId().stream().map(Field::getName).map(field -> field.concat("=?")).collect(Collectors.joining(","));
    }

    private String withOutIdWildCards() {
        return entityClassMetaData.getFieldsWithoutId().stream().map(Field::getName).map(field -> "?").collect(Collectors.joining(","));
    }
}
