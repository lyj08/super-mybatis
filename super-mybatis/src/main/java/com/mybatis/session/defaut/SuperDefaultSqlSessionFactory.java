package com.mybatis.session.defaut;

import com.mybatis.excutor.SuperExcutor;
import com.mybatis.session.SuperConfiguration;
import com.mybatis.session.SuperSqlSession;
import com.mybatis.session.SuperSqlSessionFactory;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/4/19 14:02
 */
public class SuperDefaultSqlSessionFactory implements SuperSqlSessionFactory
{

    private SuperConfiguration configuration;


    public SuperDefaultSqlSessionFactory(SuperConfiguration configuration)
    {
        this.configuration = configuration;
    }

    @Override
    public SuperSqlSession openSession()
    {
        return openSessionFromDataSource();

    }

    private SuperSqlSession openSessionFromDataSource() {
        SuperExcutor executor = this.configuration.newExcutor();
        SuperDefaultSqlSession sqlSession = new SuperDefaultSqlSession(this.configuration,executor);
        return sqlSession;
    }



    @Override
    public SuperConfiguration getConfiguration()
    {
        return this.configuration;
    }
}
