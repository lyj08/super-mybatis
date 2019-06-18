package com.mybatis.session;

import com.mybatis.excutor.SuperExcutor;
import com.mybatis.excutor.SuperSimpleExecutor;
import com.mybatis.mapperProxy.SuperMapperRegistry;
import com.mybatis.mapperpare.TableEntity;
import com.mybatis.mapping.SuperBounSql;
import com.mybatis.mapping.SuperMappedStatement;
import com.mybatis.paramHandler.SuperDefaultParameterHandler;
import com.mybatis.paramHandler.SuperParameterHandler;
import com.mybatis.resultHandler.DefaultResultSetHandler;
import com.mybatis.resultHandler.ResultHandler;
import com.mybatis.resultHandler.ResultSetHandler;
import com.mybatis.statment.SuperPrepareStatementHandler;
import com.mybatis.statment.SuperStatementHandler;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/4/21 15:15
 */
public class SuperConfiguration
{

    /**
     * <br>
     * mapper所在包
     */
    private String mapperPackge;

    /**
     * <br>
     *  mapper.xml所在路径
     */
    private String[] mapperlocations;

    /**
     * <br>
     * 别名包
     */
    private String alias;

    /**
     * <br>
     *  实体数据
     */
    private Map<String, TableEntity> tableEntityMap = new HashMap<>();


    /**
     * <br>
     * 初始化时候将方法 的对应数据缓存起来 key 类+方法名 value 为方法的相关信息类
     */
    private Map<String, SuperMappedStatement> mappedStatement = new HashMap<>();

    /**
     * mapper注册类
     * mapperRegistry:在mybatis源码中 会在SqlSessionFactoryBuilder.buid()方法中去解析xml的时候
     * 通过包名 或者类路径 或者命名空间 将mapper.class作为key SuperMapperProxyFactory 作为value 放到它的属性 knownMappers中
     */
    private final SuperMapperRegistry mapperRegistry = new SuperMapperRegistry(this);


    public void addMappedStatement(SuperMappedStatement ms)
    {
        mappedStatement.put(ms.getId(), ms);
    }
    public SuperMappedStatement getMappedStatement(String statemenId)
    {
        SuperMappedStatement statement = this.mappedStatement.get(statemenId);
        if (statement==null){
            try {
                throw new Exception("找不到："+statemenId+"() 方法");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return statement;
    }


    private Connection connection;

    public TableEntity getTableEntity(String id)
    {
        return this.tableEntityMap.get(id);
    }

    public void addTableEntityMap(TableEntity tableEntity)
    {
        this.tableEntityMap.put(tableEntity.getId(),tableEntity);
    }

    public SuperMapperRegistry getMapperRegistry()
    {
        return mapperRegistry;
    }

    public <T> T getMapper(Class<T> type, SuperSqlSession sqlSession)
    {
        return mapperRegistry.getMapper(type, sqlSession);
    }




    public <T> void addMappers(String packege)
    {
        try {
            mapperRegistry.addMapper(Class.forName(packege));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public String getMapperPackge()
    {
        return mapperPackge;
    }

    public void setMapperPackge(String mapperPackge)
    {
        this.mapperPackge = mapperPackge;
    }

    public String getAlias()
    {
        return alias;
    }

    public void setAlias(String alias)
    {
        this.alias = alias;
    }

    public void setMappedStatement(Map<String, SuperMappedStatement> mappedStatement)
    {
        this.mappedStatement = mappedStatement;
    }

    public String[] getMapperlocations()
    {
        return mapperlocations;
    }

    public void setMapperlocations(String[] mapperlocations)
    {
        this.mapperlocations = mapperlocations;
    }

    public Connection getConnection()
    {
        return connection;
    }

    public void setConnection(Connection connection)
    {
        this.connection = connection;
    }

    public ResultSetHandler newResultSetHandler(SuperExcutor executor, SuperMappedStatement mappedStatement,
                                                ResultHandler resultHandler, SuperBounSql boundSql) {
        ResultSetHandler resultSetHandler = new DefaultResultSetHandler(executor, mappedStatement, resultHandler, boundSql);
        return resultSetHandler;
    }

    /**
     * <br>
     * 参数处理接口
     */
    public SuperParameterHandler newParameterHandler(SuperMappedStatement mappedStatement, Object parameterObject, SuperBounSql boundSql) {
        SuperParameterHandler parameterHandler =  new SuperDefaultParameterHandler(mappedStatement,parameterObject,boundSql);
        return parameterHandler;
    }


    public SuperStatementHandler newStatementHandler(ResultHandler resultHandler, SuperExcutor executor, SuperMappedStatement mappedStatement, Object parameterObject, SuperBounSql boundSql) {
        return new SuperPrepareStatementHandler(resultHandler,executor, mappedStatement, parameterObject, boundSql);
    }


    public SuperExcutor newExcutor(){
        return new SuperSimpleExecutor(connection,this);
    }
}
