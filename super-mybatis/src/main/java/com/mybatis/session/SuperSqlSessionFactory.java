package com.mybatis.session;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/4/19 12:08
 */
public interface SuperSqlSessionFactory
{
    SuperSqlSession openSession();
    SuperConfiguration getConfiguration();
}
