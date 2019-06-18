package com.mybatis.mapping;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/4/21 15:18
 */
public interface SuperSqlSuorce
{
    /**
     * 获取Sql 通过参数拼接好的
     * @param paramObject
     * @return
     */
    SuperBounSql getSuperBounSql(Object paramObject);
}
