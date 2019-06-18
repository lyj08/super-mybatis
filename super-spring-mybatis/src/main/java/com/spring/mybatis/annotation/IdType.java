package com.spring.mybatis.annotation;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/4/23 15:56
 */
public enum IdType
{
    /**
     * 数据库ID自增
     */
    AUTO(0),

    /**
     * 该类型为未设置主键类型
     */
    NONE(1);

    private final int key;

    IdType(int key) {
        this.key = key;
    }

    public int getKey()
    {
        return key;
    }}
