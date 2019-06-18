package com.mybatis.paramHandler;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * <br>
 * 一个sql赋值的接口
 * @author 永健
 * @since 2019/5/15 16:53
 */
public interface SuperParameterHandler
{
    void setParameters(PreparedStatement ps)
            throws SQLException;
}
