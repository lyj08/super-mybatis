package com.mybatis.statment;

import com.mybatis.excutor.SuperExcutor;
import com.mybatis.mapping.SuperBounSql;
import com.mybatis.mapping.SuperMappedStatement;
import com.mybatis.paramHandler.SuperParameterHandler;
import com.mybatis.resultHandler.ResultHandler;
import com.mybatis.resultHandler.ResultSetHandler;
import com.mybatis.session.SuperConfiguration;

import java.sql.*;
import java.util.List;

/**
 * <br>
 * 处理sql预编译
 * @author 永健
 * @since 2019/5/15 14:52
 */
public class SuperPrepareStatementHandler implements SuperStatementHandler
{

    private SuperConfiguration configuration;
    private ResultSetHandler resultSetHandler;
    private  SuperExcutor executor;
    private SuperMappedStatement mappedStatement;
    private SuperBounSql bounSql;
    private SuperParameterHandler parameterHandler;


    public SuperPrepareStatementHandler(ResultHandler resulHandler, SuperExcutor executor, SuperMappedStatement mappedStatement, Object parameterObject, SuperBounSql bounSql)
    {
        this.executor = executor;
        this.bounSql = bounSql;
        this.mappedStatement = mappedStatement;
        this.configuration=mappedStatement.getConfiguration();
        this.parameterHandler = this.configuration.newParameterHandler(mappedStatement, parameterObject, bounSql);
        this.resultSetHandler = this.configuration.newResultSetHandler(executor,mappedStatement,resulHandler,bounSql);
    }

    /**
     * <br>
     * 创建Statment 所有的数据库操作都在这里 这mybatis中 没有 ps.excuteQuery(sql)的方法
     */
    @Override
    public Statement prepare(Connection connection) throws SQLException
    {
        return instantiateStatement(connection);
    }

    private Statement instantiateStatement(Connection connection) throws SQLException {
        String sql =bounSql.getSql();
        return connection.prepareStatement(sql);
    }



    @Override
    public void parameterize(Statement statement) throws SQLException
    {
        parameterHandler.setParameters((PreparedStatement)statement);
    }

    @Override
    public int update(Statement statement) throws SQLException
    {
        PreparedStatement ps=(PreparedStatement) statement;
        ps.execute();
        return ps.getUpdateCount();
    }

    @Override
    public <E> List<E> query(Statement statement) throws SQLException
    {
        PreparedStatement ps=(PreparedStatement) statement;
        ps.execute();
        return resultSetHandler.handleResultSets(ps);
    }
}
