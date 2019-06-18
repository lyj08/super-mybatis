package com.mybatis.mapping;

import java.util.LinkedList;

/**
 * <br>
 * 存储sql对象 sql语句参数
 * @author 永健
 * @since 2019/4/21 15:19
 */
public class SuperBounSql
{
    private  String sql;
    private LinkedList<SuperParameterMapping> parameterMappings;
    private String paramType;
    private Object paramsObject;

    public SuperBounSql()
    {
    }

    public SuperBounSql(String sql,Object paramsObject)
    {
        this.sql = sql;
        this.paramsObject=paramsObject;
    }

    public String getSql()
    {
        return sql;
    }

    public void setSql(String sql)
    {
        this.sql = sql;
    }

    public LinkedList<SuperParameterMapping> getParameterMappings()
    {
        return parameterMappings;
    }

    public void setParameterMappings(LinkedList<SuperParameterMapping> parameterMappings)
    {
        this.parameterMappings = parameterMappings;
    }

    public String getParamType()
    {
        return paramType;
    }

    public void setParamType(String paramType)
    {
        this.paramType = paramType;
    }

    public Object getParamsObject()
    {
        return paramsObject;
    }

    public void setParamsObject(Object paramsObject)
    {
        this.paramsObject = paramsObject;
    }
}
