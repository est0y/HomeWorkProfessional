package ru.otus.jdbc.mapper;

import ru.otus.core.repository.DataTemplate;
import ru.otus.core.repository.executor.DbExecutor;

import java.sql.Connection;
import java.util.*;

/**
 * Сохратяет объект в базу, читает объект из базы
 */
public class DataTemplateJdbc<T> implements DataTemplate<T> {

    private final DbExecutor dbExecutor;
    private final EntitySQLMetaData entitySQLMetaData;
    private final ObjectParser<T> objectParser;

    public DataTemplateJdbc(DbExecutor dbExecutor, EntitySQLMetaData entitySQLMetaData, ObjectParser<T> objectParser) {
        this.dbExecutor = dbExecutor;
        this.entitySQLMetaData = entitySQLMetaData;
        this.objectParser = objectParser;
    }

    @Override
    public Optional<T> findById(Connection connection, long id) {
        return dbExecutor.executeSelect(connection,
                entitySQLMetaData.getSelectByIdSql(),
                List.of(id),
                (resultSet) -> objectParser.getObjects(resultSet).get(0)
        );
    }

    @Override
    public List<T> findAll(Connection connection) {
        Optional<List<T>> optional = dbExecutor.executeSelect(connection,
                entitySQLMetaData.getSelectAllSql(),
                List.of(),
                objectParser::getObjects);
        return optional.orElseGet(List::of);
    }

    @Override
    public long insert(Connection connection, T client) {
        List<Object> list = objectParser.getFieldsWithoutIdValue(client);
        return dbExecutor.executeStatement(connection, entitySQLMetaData.getInsertSql(), list);
    }

    @Override
    public void update(Connection connection, T client) {
        List<Object> list = objectParser.getFieldsWithoutIdValue(client);
        list.add(objectParser.getIdFieldValue(client));
        dbExecutor.executeStatement(connection, entitySQLMetaData.getUpdateSql(), list);
    }
}
