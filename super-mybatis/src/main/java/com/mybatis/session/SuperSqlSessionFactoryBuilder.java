package com.mybatis.session;

import com.mybatis.session.defaut.SuperDefaultSqlSessionFactory;

/**
 * <br>
 * SqlSeSsionFactory构建类
 * @author 永健
 * @since 2019/4/21 15:39
 */
public class SuperSqlSessionFactoryBuilder
{
    public SuperSqlSessionFactory build(SuperConfiguration config) {
        return new SuperDefaultSqlSessionFactory(config);
    }

}
