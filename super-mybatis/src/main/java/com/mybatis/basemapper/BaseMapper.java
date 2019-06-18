package com.mybatis.basemapper;

import java.io.Serializable;
import java.util.List;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/4/24 15:53
 */
public interface BaseMapper<T>
{
    int insert(T t);

    <T> T selectById(Serializable id);

    int deleteById(Serializable id);

    <T> List<T> selectList();

    int updateById();
}
