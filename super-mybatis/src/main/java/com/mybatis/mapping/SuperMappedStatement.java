package com.mybatis.mapping;

import com.mybatis.session.SuperConfiguration;

import java.util.LinkedList;
import java.util.List;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/4/21 15:17
 */
public class SuperMappedStatement
{

    /**
     * <br>
     *  Configuration
     */
    private SuperConfiguration configuration;

    /**
     * 获取sql对象
     */
    private SuperSqlSuorce sqlSource =new DynamicSqlSource();


    private LinkedList<SuperParameterMapping> parameterMappingList;




    /**
     * sql枚举 增、删、改、查
     */
    private SuperSqlCommandType sqlCommandType;

    /**
     * 属性JaveBean属性
     */
    private String[] keyProperties;

    /**
     * <br>
     * 方法返回值类型
     */
    private Class<?> returnType;

    /**
     * <br>
     * sql
     */
    private String sql;

    /**
     * <br>
     *  接收值
     */
    private String paramType;

    /**
     * 表中的列
     */
    private String[] keyColumns;

    /**
     *  数据库的表对象名字
     */
    private String databaseId;

    /**
     * statementId
     * 类名+方法名
     */
    private String Id;


    public SuperBounSql getBound(Object paramsObject){
        SuperBounSql bounSql = new SuperBounSql();
        bounSql.setParameterMappings(this.parameterMappingList);
        bounSql.setParamType(this.paramType);
        bounSql.setParamsObject(paramsObject);
        bounSql.setSql(this.sql);
        return bounSql;
    }


    /**
     * <br>
     * #{}中的名字 以及类型
     */

    public SuperConfiguration getConfiguration()
    {
        return configuration;
    }

    public void setConfiguration(SuperConfiguration configuration)
    {
        this.configuration = configuration;
    }

    public SuperSqlSuorce getSqlSource()
    {
        return sqlSource;
    }

    public void setSqlSource(SuperSqlSuorce sqlSource)
    {
        this.sqlSource = sqlSource;
    }

    public SuperSqlCommandType getSqlCommandType()
    {
        return sqlCommandType;
    }

    public void setSqlCommandType(SuperSqlCommandType sqlCommandType)
    {
        this.sqlCommandType = sqlCommandType;
    }

    public String[] getKeyProperties()
    {
        return keyProperties;
    }

    public void setKeyProperties(String[] keyProperties)
    {
        this.keyProperties = keyProperties;
    }

    public String[] getKeyColumns()
    {
        return keyColumns;
    }

    public void setKeyColumns(String[] keyColumns)
    {
        this.keyColumns = keyColumns;
    }

    public String getDatabaseId()
    {
        return databaseId;
    }

    public void setDatabaseId(String databaseId)
    {
        this.databaseId = databaseId;
    }

    public String getId()
    {
        return Id;
    }

    public void setId(String id)
    {
        Id = id;
    }

    public Class<?> getReturnType()
    {
        return returnType;
    }

    public void setReturnType(Class<?> returnType)
    {
        this.returnType = returnType;
    }

    public String getSql()
    {
        return sql;
    }

    public void setSql(String sql)
    {
        this.sql = sql;
    }

    public List<SuperParameterMapping> getParameterMappingList()
    {
        return parameterMappingList;
    }

    public void setParameterMappingList(LinkedList<SuperParameterMapping> parameterMappingList)
    {
        this.parameterMappingList = parameterMappingList;
    }

    public String getParamType()
    {
        return paramType;
    }

    public void setParamType(String paramType)
    {
        this.paramType = paramType;
    }
}
