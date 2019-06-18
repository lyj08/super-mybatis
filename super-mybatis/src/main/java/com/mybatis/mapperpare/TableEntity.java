package com.mybatis.mapperpare;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/4/24 17:09
 */
public class TableEntity
{

    /**
     * <br>
     * 类对应的表名
     */
    private String tableName;

    /**
     * <br>
     *  主键类型
     */
    private Integer keyType;

    /**
     * <br>
     * 主键
     */
    private String primarykey;


    /**
     * <br>
     * 类对象
     */
    private Class<?> aClass;

    /**
     * <br>
     * 当前类的全名
     */
    private String id;

    /**
     * <br>
     * 字段名
     */
    private String[] field;

    public String getTableName()
    {
        return tableName;
    }

    public void setTableName(String tableName)
    {
        this.tableName = tableName;
    }

    public String getPrimarykey()
    {
        return primarykey;
    }

    public void setPrimarykey(String primarykey)
    {
        this.primarykey = primarykey;
    }

    public Class<?> getaClass()
    {
        return aClass;
    }

    public void setaClass(Class<?> aClass)
    {
        this.aClass = aClass;
    }

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public Integer getKeyType()
    {
        return keyType;
    }

    public void setKeyType(Integer keyType)
    {
        this.keyType = keyType;
    }

    public String[] getField()
    {
        return field;
    }

    public void setField(String[] field)
    {
        this.field = field;
    }
}
