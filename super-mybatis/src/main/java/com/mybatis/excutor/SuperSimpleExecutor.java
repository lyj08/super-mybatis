package com.mybatis.excutor;

import com.mybatis.mapping.SuperBounSql;
import com.mybatis.mapping.SuperMappedStatement;
import com.mybatis.resultHandler.ResultHandler;
import com.mybatis.session.SuperConfiguration;
import com.mybatis.statment.SuperStatementHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/4/19 13:14
 */
public class SuperSimpleExecutor extends SuperBaseExcutor
{

    private Connection connection;
    private SuperConfiguration configuration;

    public SuperSimpleExecutor(Connection connection, SuperConfiguration configuration)
    {
        this.connection = connection;
        this.configuration = configuration;
    }

    public int doUpdate(Object parameter) throws SQLException
    {
        return 0;
    }

    public SuperConfiguration getConfiguration()
    {
        return configuration;
    }

    public void setConfiguration(SuperConfiguration configuration)
    {
        this.configuration = configuration;
    }

    @Override
    public Connection getConnection() throws SQLException
    {
        return connection;
    }

    @Override
    protected int doUpdate(SuperMappedStatement ms, Object parameter,SuperBounSql bounSql) throws SQLException
    {
        // 创建 Statement 的时候其实就
        // statementHandler
        SuperStatementHandler handler = ms.getConfiguration().newStatementHandler(null,this,ms,parameter,bounSql);
        Statement statement = createStatement(handler);
        return handler.update(statement);
    }

    @Override
    protected <E> List<E> doQuery(ResultHandler resultHandler, SuperMappedStatement ms, Object parameter, SuperBounSql bounSql) throws SQLException
    {
        // 创建 Statement 的时候其实就
        // statementHandler
        SuperStatementHandler handler = ms.getConfiguration().newStatementHandler(resultHandler,this,ms,parameter,bounSql);
        Statement statement = createStatement(handler);
        return handler.query(statement);
    }

    private Statement createStatement(SuperStatementHandler handler) throws SQLException
    {
        // 创建Statement
        Statement statement = handler.prepare(connection);

        // 为sql设值
        handler.parameterize(statement);

        return statement;

    }


}
