package com.mybatis.paramHandler;

import com.mybatis.MyException;
import com.mybatis.enums.JavaType;
import com.mybatis.mapping.SuperBounSql;
import com.mybatis.mapping.SuperMappedStatement;
import com.mybatis.mapping.SuperParameterMapping;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.Map;

/**
 * <br>
 * 为sql的 ？ 替换成值的
 *
 * @author 永健
 * @since 2019/5/15 16:55
 */
public class SuperDefaultParameterHandler implements SuperParameterHandler
{

    private SuperMappedStatement mappedStatement;
    private Object parameterObject;
    private SuperBounSql boundSql;

    public SuperDefaultParameterHandler(SuperMappedStatement mappedStatement, Object parameterObject, SuperBounSql boundSql)
    {
        this.mappedStatement = mappedStatement;
        this.parameterObject = parameterObject;
        this.boundSql = boundSql;
    }

    @Override
    public void setParameters(PreparedStatement ps) throws SQLException
    {
        LinkedList<SuperParameterMapping> parameterMappings = boundSql.getParameterMappings();

        Map<String, ParamModel> parameterObject = (Map<String, ParamModel>) this.parameterObject;

        int i = 0;
        for (SuperParameterMapping parameterMapping : parameterMappings) {
            String name = parameterMapping.getName();
            ParamModel paramModel = parameterObject.get(name);
            if (paramModel == null) {
                exc(name);
            } else {
                setParam(++i, ps, paramModel);
            }
        }
    }


    private void exc(String name){
        throw new MyException("找不到与 " + name + " " + "匹配的参数");
    }


    private void setParam(int i, PreparedStatement ps, ParamModel paramModel)
    {
        Object value = paramModel.getValue();
        JavaType javaType = paramModel.getJavaType();
        try {
            switch (javaType) {
                case DATE:
                    ps.setDate(i, (Date) value);
                    break;
                case FLOAT:
                    ps.setFloat(i, (Float) value);
                    break;
                case DOUBLE:
                    ps.setDouble(i, (Double) value);
                    break;
                case STRING:
                    ps.setString(i, value.toString());
                    break;
                case INTEGER:
                    ps.setInt(i, (Integer) value);
                    break;
                default:
                    break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
