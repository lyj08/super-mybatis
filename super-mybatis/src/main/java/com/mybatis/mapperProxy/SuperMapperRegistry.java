package com.mybatis.mapperProxy;

import com.mybatis.session.SuperConfiguration;
import com.mybatis.session.SuperSqlSession;

import java.util.HashMap;
import java.util.Map;

/**
 * <br>
 * Mapper代理注册类
 *
 * @author 永健
 * @since 2019/4/21 22:49
 */
public class SuperMapperRegistry
{
    private final SuperConfiguration config;

    private Map<Class<?>, SuperMapperProxyFactory<?>> knownMappers = new HashMap<>();

    public SuperMapperRegistry(SuperConfiguration config)
    {
        this.config = config;
    }

    public <T> T getMapper(Class<T> type, SuperSqlSession sqlSession)
    {
        //先重knownMappers这里拿如果没有代理工厂对象 有就获取一个代理对象实例
        SuperMapperProxyFactory<?> mapperProxyFactory = (SuperMapperProxyFactory<T>)knownMappers.get(type);

        if (mapperProxyFactory == null){
            try
            {
                throw new Exception("找不到代理对象。。。");
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }
        return (T) mapperProxyFactory.newInstance(sqlSession);
    }


    /**
     * 添加一个代理对象工厂 并不是一个代理对象实例
     * @param type
     * @param <T>
     */
    public <T> void addMapper(Class<T> type)
    {
        if (type.isInterface())
        {
            knownMappers.put(type, new SuperMapperProxyFactory<>(type));
        }
    }


}
