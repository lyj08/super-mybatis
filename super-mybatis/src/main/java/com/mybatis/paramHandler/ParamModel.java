package com.mybatis.paramHandler;

import com.mybatis.enums.JavaType;

/**
 * <br>
 *
 * @desr
 * @author 永健
 * @since 2019-05-17 15:17
 */
public class ParamModel
{
    /**
     *  <br>
     *  参数值
     */
    private Object value;

    /**
     * <br>
     * 当前值的类型
     */
    private JavaType javaType;

    public ParamModel(Object value)
    {
        this.value = value;
    }

    public Object getValue()
    {
        return value;
    }

    public void setValue(Object value)
    {
        this.value = value;
    }

    public JavaType getJavaType()
    {
        return javaType;
    }

    public void setJavaType(JavaType javaType)
    {
        this.javaType = javaType;
    }
}
