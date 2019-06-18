package com.spring.mybatis.spring;

import com.mybatis.mapping.SuperMappedStatement;
import com.mybatis.session.SuperConfiguration;
import com.mybatis.session.SuperSqlSession;
import com.mybatis.session.SuperSqlSessionFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static java.lang.reflect.Proxy.newProxyInstance;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/4/22 0:08
 */
public class SuperSqlSessionTemplate implements SuperSqlSession
{

    private SuperSqlSessionFactory sqlSessionFactory;
    private SuperSqlSession sqlSessionProxy;

    public SuperSqlSessionTemplate()
    {
    }


    public SuperSqlSessionFactory getSqlSessionFactory()
    {
        return sqlSessionFactory;
    }


    @Override
    public <T> T selectOne(String statement, Object param)
    {
        return this.sqlSessionProxy.selectOne(statement,param);
    }

    @Override
    public <T> List<T> selectList(String statement, Object param)
    {
        return this.sqlSessionProxy.selectList(statement,param);
    }

    @Override
    public int insert(String statement, Object parameter)
    {
        return sqlSessionProxy.insert(statement,parameter);
    }

    @Override
    public int update(String statement, Object parameter)
    {
        return this.sqlSessionProxy.update(statement,parameter);
    }

    @Override
    public int delete(String statement, Object parameter)
    {
        return this.sqlSessionProxy.delete(statement,parameter);
    }

    @Override
    public <T> T getMapper(Class<T> type)
    {
        return getConfiguration().getMapper(type, this);
    }

    @Override
    public Connection getConnection()
    {
        return null;
    }

    @Override
    public SuperConfiguration getConfiguration()
    {
        return this.sqlSessionFactory.getConfiguration();
    }

    /**
     * <br>
     * 构造方法
     *
     * 生成一个
     * SqlSession代理对象
     * 在代理对象执行时候生调用openSession方法
     * 并且实例化 Excutor
     */
    public SuperSqlSessionTemplate(SuperSqlSessionFactory sqlSessionFactory)
    {
        this.sqlSessionFactory = sqlSessionFactory;
        this.sqlSessionProxy = (SuperSqlSession) newProxyInstance(
                SuperSqlSessionFactory.class.getClassLoader(),
                new Class[] { SuperSqlSession.class },
                new SqlSessionInterceptor());
    }

    /**
     * <br>
     *  sqlSession调用的的方法此处做个代理
     *  这里调用openSession
     * 内部类
     */
    private class SqlSessionInterceptor implements InvocationHandler
    {
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
        {
            SuperSqlSession sqlSession =sqlSessionFactory.openSession();
            Object result = method.invoke(sqlSession, args);
            return result;
        }
    }

}
