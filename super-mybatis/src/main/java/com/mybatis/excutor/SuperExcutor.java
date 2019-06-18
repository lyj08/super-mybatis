package com.mybatis.excutor;

import com.mybatis.mapping.SuperMappedStatement;
import com.mybatis.resultHandler.ResultHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/4/19 13:10
 */
public interface SuperExcutor
{

    <T> List<T> query(ResultHandler resultHandler, SuperMappedStatement ms, Object parameter) throws SQLException;
    int update(SuperMappedStatement ms,Object parameter) throws SQLException;
    Connection getConnection() throws SQLException;
}
