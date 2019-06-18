package com.mybatis.demo.sys.controller;

import com.mybatis.demo.sys.entity.User;
import com.mybatis.demo.sys.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/4/19 22:32
 */
@RestController
public class UserController
{

    @Autowired
    UserMapper userMapper;

    @RequestMapping("/user/{id}")
    public User hello(@PathVariable Integer id)
    {
        return userMapper.findById(id.toString());
    }


    @RequestMapping("/userByageAndName")
    public List<User> sss()
    {
        return userMapper.findByAgeAndName("20","张三");
    }

    @RequestMapping("/list")
    public List<User> list(){
        return userMapper.list();
    }

    @RequestMapping("/map")
    public List<User> ss()
    {
        HashMap<String, Object> map = new HashMap<>();
        map.put("age",20);
        map.put("name","张三");
        return userMapper.listMany(map);
    }

    @RequestMapping("/user/user")
    public List<User> user(){
        User user = new User();
        user.setAge(20);
        return userMapper.userModel(user);
    }


    @RequestMapping("save")
    public Integer save(HttpSession session){
        User user = new User();
        user.setAge(21);
        user.setName(UUID.randomUUID().toString());
        return userMapper.save(user);
    }

    @RequestMapping("delete/{id}")
    public Integer delete(@PathVariable Integer id){
        return userMapper.deleteById(id);
    }


    @RequestMapping("/update")
    public Integer delete(){
        User user = new User();
        user.setName("某某");
        user.setId(1);
        user.setAge(100);
        return userMapper.updateById(user);
    }

}
