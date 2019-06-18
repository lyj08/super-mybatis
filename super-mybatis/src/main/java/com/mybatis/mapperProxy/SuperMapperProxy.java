package com.mybatis.mapperProxy;

import com.mybatis.mapping.SuperMapperMethod;
import com.mybatis.session.SuperSqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

/**
 * <br>
 *  代理类
 * @author 永健
 * @since 2019/4/19 15:20
 */
public class SuperMapperProxy<T> implements InvocationHandler, Serializable
{
    private Logger logger = LoggerFactory.getLogger(SuperMapperProxy.class);

    private Class<T> mapperInterface;
    private SuperSqlSession sqlSession;
    private  Map<Method, SuperMapperMethod> methodCache;


    public SuperMapperProxy(Class<T> mapperInterface, SuperSqlSession sqlSession, Map<Method, SuperMapperMethod> methodCache)
    {
        this.mapperInterface = mapperInterface;
        this.sqlSession = sqlSession;
        this.methodCache = methodCache;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable
    {

        /**
         * 需要手动实现
         * 不能 method.invoke() 不然死循环 造成内存溢出
         * 因为这是接口 没有有具体实现 所有这里需要手动实现
         */
        // 从缓存里面拿一份 没有则创建

        if (Object.class.equals(method.getDeclaringClass())) {
            return method.invoke(this, args);
        }

        final SuperMapperMethod mapperMethod = cachedMapperMethod(method);
        logger.info("$$$$$$ 执行代理方法...");
        return mapperMethod.execute(sqlSession, args);
    }

    private SuperMapperMethod cachedMapperMethod(Method method) {
        // computeIfAbsent java8写法  先根据key 去获取一遍 没有的话则将k转为value，new个实例 作为vlue put进去 并且放回当前k对象
        return methodCache.computeIfAbsent(method, k -> new SuperMapperMethod(sqlSession.getConfiguration(),method,mapperInterface));
    }
}
