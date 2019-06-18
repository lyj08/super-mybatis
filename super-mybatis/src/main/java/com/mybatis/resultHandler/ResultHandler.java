package com.mybatis.resultHandler;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * <br>
 * 处理数据库查询的结果集
 *
 * @author 永健
 * @since 2019/5/15 15:03
 */
public interface ResultHandler
{
    <E> List<E> handleResultSets(Statement stmt) throws SQLException;

}
