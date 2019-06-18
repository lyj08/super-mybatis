package com.mybatis.resultHandler;

import com.mybatis.excutor.SuperExcutor;
import com.mybatis.mapping.SuperBounSql;
import com.mybatis.mapping.SuperMappedStatement;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <br>
 * 对查询的结果集进行包装的累
 *
 * @author 永健
 * @since 2019/5/15 15:05
 */
public class DefaultResultSetHandler implements ResultSetHandler
{

    private SuperExcutor excutor;
    private SuperMappedStatement mappedStatement;
    private ResultHandler resultHandler;
    private SuperBounSql bounSql;

    public DefaultResultSetHandler(SuperExcutor excutor, SuperMappedStatement mappedStatement, ResultHandler resultHandler, SuperBounSql bounSql)
    {
        this.excutor = excutor;
        this.mappedStatement = mappedStatement;
        this.resultHandler = resultHandler;
        this.bounSql = bounSql;
    }

    @Override
    public List<Object> handleResultSets(Statement stmt) throws SQLException
    {
        ResultSet rs = stmt.getResultSet();
        ResultSetWrapper rsw = getFirstResultSet(stmt);

        List<Object> multipleResults = new ArrayList<>();

        if (rsw != null) {
            // 有多少行

            // 获取查询方法的接收值类型
            Class<?> returnType = mappedStatement.getReturnType();
            while (rs.next()){
                handleResultSet(rsw, returnType, multipleResults);
            }
        }
        return multipleResults;
    }

    private void handleResultSet(ResultSetWrapper rsw, Class<?> returnType, List<Object> multipleResults)
    {
        handleRowValues(rsw, returnType, multipleResults);
    }


    /**
     * <br/>
     * 将查询出来的列 进行封装 到一个集合中
     *
     * @param [statement]
     *
     * @return com.mybatis.resultHandler.ResultSetWrapper
     */

    private ResultSetWrapper getFirstResultSet(Statement statement) throws SQLException
    {
        ResultSet resultSet = statement.getResultSet();
        return resultSet != null ? new ResultSetWrapper(resultSet) : null;
    }

    /**
     * <br/>
     * 此处根据mapper的返回值类型 做结果集的封装
     *
     * @param [rsw]
     *
     * @return void
     */

    private void handleRowValues(ResultSetWrapper rsw, Class<?> returnType, List<Object> multipleResults)
    {
        Object rowValues = getRowValues(rsw, returnType);
        multipleResults.add(rowValues);
    }

    /**
     * <br/>
     * 将结果集 封装成returnType 对象
     *
     * @param [rsw,
     *         returnType]
     *
     * @return java.lang.Object
     */

    private Object getRowValues(ResultSetWrapper rsw, Class<?> returnType)
    {
        Object rowValue = createResultObject(rsw, returnType);
        applyAutomaticMappings(rsw, returnType, rowValue);
        return rowValue;
    }

    /**
     * <br/>
     * 故名思议 开始对Object 进行赋值
     *
     * @param []
     *
     * @return void
     */
    private void applyAutomaticMappings(ResultSetWrapper rsw, Class<?> returnType, Object object)
    {
        List<String> classNames = rsw.getClassNames();
        List<String> columnNames = rsw.getColumnNames();
        // 列名 处理 驼峰啥的 此处不处理 与 javaBean 属性比较 赋值


        ResultSet resultSet = rsw.getResultSet();
        try {

            for (int i = 0; i < columnNames.size(); i++) {

                Object value = null;
                if (classNames.get(i).equals("java.lang.String")) {
                    value = resultSet.getString(columnNames.get(i));
                } else if (classNames.get(i).equals("java.lang.Integer")) {
                    value = resultSet.getInt(columnNames.get(i));
                }

                // 接收结果是不是map
                if ("Map".equals(returnType.getSimpleName())) {
                    Method m = HashMap.class.getDeclaredMethod("put", new Class[]{Object.class, Object.class});
                    m.invoke(object, columnNames.get(i), value);
                } else {
                    // 否则就当javaBean处理
                    Field field = returnType.getDeclaredField(columnNames.get(i));

                    if (field == null) {
                        continue;
                    }
                    field.setAccessible(true);

                    field.set(object, value);
                }
            }
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }


    /**
     * <br/>
     * 通过构造器创建一个实例
     *
     * @param [rsw,
     *         aClass]
     *
     * @return java.lang.Object
     */
    private Object createResultObject(ResultSetWrapper rsw, Class<?> returnType)
    {
        Constructor<?> constructor;
        try {
            if ("java.util.Map".equals(returnType.getName())) {
                constructor = Class.forName("java.util.HashMap").getDeclaredConstructor();
            } else {
                constructor = returnType.getDeclaredConstructor();
            }
            return constructor.newInstance();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }
}
