package com.mybatis.session.defaut;

import com.mybatis.excutor.SuperExcutor;
import com.mybatis.mapping.SuperMappedStatement;
import com.mybatis.session.SuperConfiguration;
import com.mybatis.session.SuperSqlSession;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/4/21 15:31
 */
public class SuperDefaultSqlSession implements SuperSqlSession
{
    private SuperConfiguration configuration;
    private SuperSqlSession sqlSession;
    private SuperExcutor excutor;

    public SuperDefaultSqlSession()
    {
    }

    public SuperDefaultSqlSession(SuperSqlSession sqlSession)
    {
        this.sqlSession=sqlSession;
    }

    public SuperDefaultSqlSession(SuperConfiguration configuration)
    {
        this.configuration=configuration;
    }


    public SuperDefaultSqlSession(SuperConfiguration configuration, SuperExcutor excutor)
    {
        this.configuration = configuration;
        this.excutor = excutor;
    }

    public void setConfiguration(SuperConfiguration configuration)
    {
        this.configuration = configuration;
    }

    /**
     * <br>
     * 查询一条数据也是调用selectList方法
     */
    @Override
    public <T> T selectOne(String statement,Object param)
    {
        List<T> list = this.selectList(statement, param);
        if (list.size()==1){
            return list.get(0);
        }else if (list.size()>0){
            try {
                throw new Exception("Expected one result (or null) to be returned by selectOne(), but found: " + list.size());
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return null;
    }

    /**
     * <br>
     * 在这调用excutor 在此由SuperSimpleExcutor来处理
     */
    @Override
    public <T> List<T> selectList(String statement,Object param)
    {
        SuperMappedStatement mappedStatement = this.configuration.getMappedStatement(statement);
        try {
            return excutor.query(null,mappedStatement,param);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int insert(String statement, Object parameter)
    {
        return update(statement,parameter);
    }

    @Override
    public int update(String statement, Object parameter)
    {
        SuperMappedStatement mappedStatement = this.configuration.getMappedStatement(statement);
        try {
            return excutor.update(mappedStatement,parameter);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public int delete(String statement, Object parameter)
    {
        return update(statement,parameter);
    }

    @Override
    public <T> T getMapper(Class<T> type)
    {
        return this.configuration.getMapper(type,this);
    }

    @Override
    public Connection getConnection()
    {
        return null;
    }

    @Override
    public SuperConfiguration getConfiguration()
    {
        return this.configuration;
    }
}
