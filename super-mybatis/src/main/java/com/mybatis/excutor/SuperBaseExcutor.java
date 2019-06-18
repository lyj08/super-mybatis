package com.mybatis.excutor;

import com.mybatis.mapping.SuperBounSql;
import com.mybatis.mapping.SuperMappedStatement;
import com.mybatis.resultHandler.ResultHandler;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

/**
 * <br>
 * 这个是处理一级缓存的
 * 查询的时候会先来这里判断本地是否有缓存 有的话就在这里取
 * <p>
 * 一级缓存：
 * 通过请求的sql和参数和Mapper的命名空间+方法名来做的判断
 *
 * @author 永健
 * @since 2019/4/26 10:38
 */
public abstract class SuperBaseExcutor implements SuperExcutor
{

    /**
     * <br>
     * 用map代理一级缓存
     */
    private Map<String, Object> localCach;

    @Override
    public <T> List<T> query(ResultHandler resultHandler, SuperMappedStatement ms, Object parameter) throws SQLException
    {

        // 先从缓存拿

        SuperBounSql bounSql = ms.getBound(parameter);

        return  queryFromDatabase(resultHandler,ms,parameter,bounSql);
    }


    /**
     * <br>
     * 缓存中没有
     * 就去数据库查询数据
     */
    private <E> List<E> queryFromDatabase(ResultHandler resultHandler, SuperMappedStatement ms, Object parameter, SuperBounSql bounSql) throws SQLException
    {
        // 查到后 放一份数据到缓存中
        List<E> list = doQuery(resultHandler,ms, parameter,bounSql);
        return list;
    }


    private String createCacheKey(SuperMappedStatement ms, Object param)
    {
        String key = ms.getId() + "." + ms.getSql() + ":";

        return key;

    }

    @Override
    public int update(SuperMappedStatement ms,Object parameter) throws SQLException
    {
        SuperBounSql bounSql = ms.getBound(parameter);
        return doUpdate(ms,parameter,bounSql);
    }

    @Override
    public Connection getConnection() throws SQLException
    {
        return null;
    }

    protected abstract int doUpdate(SuperMappedStatement ms, Object parameter,SuperBounSql bounSql)
            throws SQLException;


    protected abstract <E> List<E> doQuery(ResultHandler resultHandler, SuperMappedStatement ms, Object parameter, SuperBounSql bounSql)
            throws SQLException;

}
