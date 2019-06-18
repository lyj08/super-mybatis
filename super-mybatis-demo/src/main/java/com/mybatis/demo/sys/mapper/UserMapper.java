package com.mybatis.demo.sys.mapper;

import com.mybatis.basemapper.BaseMapper;
import com.mybatis.demo.sys.entity.User;

import java.util.List;
import java.util.Map;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/4/21 16:59
 */
public interface UserMapper extends BaseMapper<User>
{
    User findById(String id);

    List<User> findByAgeAndName(String age,String name);

    List<User> listMany(Map<String,Object> map);

    List<User> userModel(User user);

    int deleteById(Integer id);

    int updateById(User user);

    int save(User user);

    List<User> list();
}
