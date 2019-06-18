package com.mybatis.resultHandler;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * <br>
 *
 * @desr  对jdbc查询的结果集ResultSet进行包装 把数据库的列 以及列的类型进行包装
 *
 * @Author 永健
 * @since 2019-05-18 21:41
 */
public class ResultSetWrapper
{
    private  ResultSet resultSet;
    private  List<String> columnNames = new ArrayList<>();
    private  List<String> classNames = new ArrayList<>();


    public ResultSetWrapper(ResultSet resultSet) throws SQLException
    {
        this.resultSet=resultSet;
        // 获取结果集列的元数据
        ResultSetMetaData metaData = resultSet.getMetaData();

        // 获取个数 有多少个字段
        int columnCount = metaData.getColumnCount();

        // 遍历取 字段的 属性
        for (int i = 1; i <= columnCount; i++) {
            // 列名称
            columnNames.add(metaData.getColumnName(i));
            // 列类型 java.lang.String
            classNames.add(metaData.getColumnClassName(i));
        }
    }

    public ResultSet getResultSet()
    {
        return resultSet;
    }

    public void setResultSet(ResultSet resultSet)
    {
        this.resultSet = resultSet;
    }

    public List<String> getColumnNames()
    {
        return columnNames;
    }

    public void setColumnNames(List<String> columnNames)
    {
        this.columnNames = columnNames;
    }

    public List<String> getClassNames()
    {
        return classNames;
    }

    public void setClassNames(List<String> classNames)
    {
        this.classNames = classNames;
    }
}
