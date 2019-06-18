package com.spring.mybatis.mapper;

import com.mybatis.session.SuperSqlSessionFactory;
import com.mybatis.session.defaut.SuperDefaultSqlSession;
import com.spring.mybatis.support.SuperSqlSessionDaoSupport;
import org.springframework.beans.factory.FactoryBean;

/**
 * <br>
 * 这个就是代理对象了 网Spring中注入的就是它
 *
 * @author 永健
 * @since 2019/4/21 15:11
 */
public class SuperMapperProxyBean<T> extends SuperSqlSessionDaoSupport implements FactoryBean<T>
{
    private Class<T> mapperInterface;



    public SuperMapperProxyBean(Class<T> mapperInterface)
    {
        this.mapperInterface = mapperInterface;
    }


    @Override
    public T getObject()
    {
        return getSqlSession().getMapper(this.mapperInterface);
    }

    @Override
    public boolean isSingleton()
    {
        return true;
    }

    @Override
    public Class<T> getObjectType()
    {
        return this.mapperInterface;
    }


    /**
     * <br>
     *  使用set注入
     * @param sqlSessionFactory:在自动配置类已经注入
     */
//    @Override
//    public void setSqlSessionFactory(SuperSqlSessionFactory sqlSessionFactory) {
//        this.sqlSessionFactory=sqlSessionFactory;
//
//    }
}
