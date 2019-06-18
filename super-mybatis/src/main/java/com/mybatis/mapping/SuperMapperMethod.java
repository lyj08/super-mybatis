package com.mybatis.mapping;

import com.mybatis.MyException;
import com.mybatis.enums.JavaType;
import com.mybatis.paramHandler.ParamModel;
import com.mybatis.session.SuperConfiguration;
import com.mybatis.session.SuperSqlSession;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

/**
 * <br>
 * 该类存储方法的相关信息
 *
 * @author 永健
 * @since 2019/4/23 11:18
 */
public class SuperMapperMethod
{

    /**
     * <br>
     * 全局的配置
     */
    private SuperConfiguration configuration;

    /**
     * <br>
     * sql的方法类型
     */
    private SuperSqlCommandType sqlTpye;

    /**
     * <br>
     * 返回值对象
     */
    private Class<?> returnType;

    /**
     * <br>
     * 方法对象
     */
    private Method method;

    /**
     * <br>
     * mapper类
     */
    private Class<?> mapperInterface;

    public SuperMapperMethod(SuperConfiguration configuration, Method method, Class<?> mapperInterface)
    {
        this.configuration = configuration;
        this.method = method;
        this.mapperInterface = mapperInterface;
    }

    /**
     * <br>
     * 操作数据库的时候
     * <p>
     * 这就是线头
     * <p>
     * 开始往回找-->
     */
    public Object execute(SuperSqlSession sqlSession, Object[] params)
    {
        SuperConfiguration configuration = sqlSession.getConfiguration();
        SuperMappedStatement mappedStatement = configuration.getMappedStatement(method.getDeclaringClass().getName() + "." + method.getName());

        Map<String,ParamModel> map=convertArgsToSqlCommandParam(params);

        Object object = null;
        switch (mappedStatement.getSqlCommandType()) {
            case DELETE:
                object = sqlSession.delete(mappedStatement.getId(),map);
                break;
            case INSERT:
                object = sqlSession.insert(mappedStatement.getId(),map);
                break;
            case SELECT:
                if (method.getReturnType().getName().equals("java.util.List")){
                    object = sqlSession.selectList(mappedStatement.getId(),map);
                }else {
                    object = sqlSession.selectOne(mappedStatement.getId(), map);
                }
                break;
            case UPDATE:
                object = sqlSession.update(mappedStatement.getId(),map);
                break;
        }
        return object;
    }

    /**
     * <br>
     * <p>
     * 将参数转换成map
     */
    private Map<String, ParamModel> convertArgsToSqlCommandParam(Object[] args)
    {
        return args == null ? null : converMap(args);
    }

    private Map<String, ParamModel> converMap(Object[] args)
    {
        HashMap<String, Object> hashMap = new HashMap<>();

        checkParamIsnull(this.method, args, hashMap);

        return converTo(hashMap);

    }

    /**
     * <br/>
     *   转换成 Map<String, ParamModel>  key: 就是属性，最后sql参数赋值时候 拿这个map 参数
     *       value 为参数的name 和值
     * @param [map]
     *
     * @return java.util.Map<java.lang.String,com.mybatis.paramHandler.ParamModel>
     *
     */
    private Map<String, ParamModel> converTo(Map<String, Object> map)
    {
        HashMap<String, ParamModel> hashMap = new HashMap<>();
        Set<Map.Entry<String, Object>> entries = map.entrySet();
        for (Map.Entry<String, Object> entry : entries) {
            Object value = entry.getValue();
            String name = value.getClass().getSimpleName();
            ParamModel paramModel = new ParamModel(value);
            if (JavaType.STRING.getJavaType().equals(name)) {
                paramModel.setJavaType(JavaType.STRING);
            } else if (JavaType.INTEGER.getJavaType().equals(name)) {
                paramModel.setJavaType(JavaType.INTEGER);
            } else if (JavaType.DATE.getJavaType().equals(name)) {
                paramModel.setJavaType(JavaType.DATE);
            } else if (JavaType.DOUBLE.getJavaType().equals(name)) {
                paramModel.setJavaType(JavaType.DOUBLE);
            } else if (JavaType.FLOAT.getJavaType().equals(name)) {
                paramModel.setJavaType(JavaType.FLOAT);
            } else {
                throw new MyException("参数转换失败！");
            }
            hashMap.put(entry.getKey(), paramModel);
        }
        return hashMap;
    }


    /**
     * <br/>
     * 将方法的所以入参 无论是map 或者是基本类型 javaBean 都转换为 key:value 的map
     * @param [list, method, args, map]
     *
     * @return void
     *
     */
    private void checkParamIsnull( Method method, Object[] args, Map<String, Object> map)
    {
        Parameter[] parameters = method.getParameters();
        int i = -1;
        for (Parameter parameter : parameters) {
            ++i;
            String name = parameter.getType().getSimpleName();
            // 基本的参数
            if (JavaType.STRING.getJavaType().equals(name) || JavaType.INTEGER.getJavaType().equals(name)
                    || JavaType.DATE.getJavaType().equals(name)
                    || JavaType.DOUBLE.getJavaType().equals(name)
                    || JavaType.FLOAT.getJavaType().equals(name)
                    || JavaType.DATE.getJavaType().equals(name)) {

                map.put(parameter.getName(), args[i]);
            } else if (name.equals("Map")) {
                // map 判断
                Map arg = (HashMap) args[i];
                map.putAll(arg);
            } else {
                //  javaBean 判断
                Object arg = args[i];
                // 不考虑继承了父类
                Field[] fields = arg.getClass().getDeclaredFields();
                try {
                    for (Field field : fields) {
                        field.setAccessible(true);
                        Object value = field.get(arg);
                        if (value!=null){
                            String fieldName = field.getName();
                            map.put(fieldName, value);
                        }
                    }
                } catch (IllegalAccessException e) {
                    throw new MyException("参数转换失败！");
                }
            }
        }
    }

}
