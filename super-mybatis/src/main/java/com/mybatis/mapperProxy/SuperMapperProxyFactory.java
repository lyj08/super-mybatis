package com.mybatis.mapperProxy;

import com.mybatis.mapping.SuperMapperMethod;
import com.mybatis.session.SuperSqlSession;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <br>
 * 生产代理类的工厂代理类
 *
 * @author 永健
 * @since 2019/4/21 22:50
 */
public class SuperMapperProxyFactory<T>
{
    private final Class<T> mapperInterface;
    private final Map<Method, SuperMapperMethod> methodCache = new ConcurrentHashMap<>();

    public SuperMapperProxyFactory(Class<T> mapperInterface)
    {
        this.mapperInterface = mapperInterface;
    }

    public Class<T> getMapperInterface()
    {
        return mapperInterface;
    }

    public Map<Method, SuperMapperMethod> getMethodCache()
    {
        return methodCache;
    }


    /**
     * 生成代理类的方法
     * @param sqlSession
     * @return
     */
    public T newInstance(SuperSqlSession sqlSession)
    {
        final SuperMapperProxy<T> mapperProxy = new SuperMapperProxy(mapperInterface, sqlSession, methodCache);
        return (T) Proxy.newProxyInstance(mapperInterface.getClassLoader(), new Class[]{mapperInterface}, mapperProxy);
    }

}
