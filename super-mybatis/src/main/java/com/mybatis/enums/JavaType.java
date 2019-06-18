package com.mybatis.enums;

public enum JavaType
{
    STRING("String","VARCHAR"),INTEGER("Integer","INTEGER"),DATE("Date","TIMESAMP"),DOUBLE("Double","Double"),FLOAT("Float","float");
    private String javaType;
    private String jdbcType;

    JavaType(String javaType, String jdbcType)
    {
        this.javaType = javaType;
        this.jdbcType = jdbcType;
    }

    public String getJavaType()
    {
        return javaType;
    }

    public void setJavaType(String javaType)
    {
        this.javaType = javaType;
    }

    public String getJdbcType()
    {
        return jdbcType;
    }

    public void setJdbcType(String jdbcType)
    {
        this.jdbcType = jdbcType;
    }
}
