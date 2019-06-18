package com.mybatis.auto.config;

import java.sql.Connection;
import java.sql.PreparedStatement;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/4/19 14:22
 */
public class SuperDatasource
{
    private Connection connection;
    private PreparedStatement preparedStatement;

    public Connection getConnection()
    {
        return connection;
    }

    public void setConnection(Connection connection)
    {
        this.connection = connection;
    }

    public PreparedStatement getPreparedStatement()
    {
        return preparedStatement;
    }

    public void setPreparedStatement(PreparedStatement preparedStatement)
    {
        this.preparedStatement = preparedStatement;
    }
}
