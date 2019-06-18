package com.mybatis.resultHandler;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * <br>
 *
 * @desr
 * @Author 永健
 * @since 2019-05-17 16:43
 */
public interface ResultSetHandler
{
    <E> List<E> handleResultSets(Statement stmt) throws SQLException;
}
