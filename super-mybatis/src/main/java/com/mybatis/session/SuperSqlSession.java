package com.mybatis.session;

import java.sql.Connection;
import java.util.List;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/4/19 12:08
 */
public interface SuperSqlSession
{
    /**
     * 查询一条数据
     * @param statement
     * @param <T>
     * @return
     */
    <T> T selectOne(String statement,Object param);

    <T> List<T> selectList(String statement,Object param);

    int insert(String statement, Object parameter);

    int update(String statement, Object parameter);

    int delete(String statement, Object parameter);

    <T> T getMapper(Class<T> type);

    Connection getConnection();

    SuperConfiguration getConfiguration();

}
