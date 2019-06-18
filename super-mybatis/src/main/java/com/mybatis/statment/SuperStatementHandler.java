package com.mybatis.statment;

import sun.plugin2.main.server.ResultHandler;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/5/15 14:49
 */
public interface SuperStatementHandler
{

    /**
     * <br>
     * 创建Statement的时候就编译sql
     */
    Statement prepare(Connection connection)
            throws SQLException;


    /**
     * <br>
     * 为sql设值
     */
    void parameterize(Statement statement)
            throws SQLException;


    int update(Statement statement)
            throws SQLException;

    <E> List<E> query(Statement ps)
            throws SQLException;
}
