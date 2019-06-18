package com.mybatis.auto.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/4/19 14:19
 */
public class SuperJdbc
{
    private static Logger logger = LoggerFactory.getLogger(SuperJdbc.class);
    private SuperMybatisProperties mybatisProperties;
    private static Connection connection = null;
    private PreparedStatement preparedStatement = null;
    private ResultSet rs = null;

    public SuperJdbc(SuperMybatisProperties mybatisProperties)
    {
        this.mybatisProperties = mybatisProperties;
    }

    public Connection getConnection()
    {
        if (connection == null)
        {
            try
            {
                Class.forName(this.mybatisProperties.getDriverclassname());
                logger.info("@_@!  数据库驱动加载成功");
                connection = DriverManager.getConnection(this.mybatisProperties.getUrl(), this.mybatisProperties.getUsername(), this.mybatisProperties.getPassword());
                logger.info("@_@!  数据库连接成功");
            } catch (Exception e)
            {
                logger.info("@_@!  数据库连接失败,"+e);
                e.printStackTrace();
            }
        }
        return connection;
    }

    public PreparedStatement getPreparedStatement(String sql)
    {
        try
        {
            this.preparedStatement = getConnection().prepareStatement(sql);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return this.preparedStatement;
    }

    public ResultSet executeQuery(String sql)
    {
        try
        {
            rs = getPreparedStatement(sql).executeQuery();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return rs;
    }

    public int executeUpdate(String sql)
    {
        int i = 0;
        try
        {
            i = getPreparedStatement(sql).executeUpdate();
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return i;
    }


    public static void close(ResultSet rs, Statement ps, Connection conn)
    {
        try
        {
            if (rs != null)
            {
                rs.close();
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            if (ps != null)
            {
                ps.close();
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            if (conn != null)
            {
                conn.close();
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void close(Statement ps, Connection conn)
    {
        try
        {
            if (ps != null)
            {
                ps.close();
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        try
        {
            if (conn != null)
            {
                conn.close();
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public static void close(Connection conn)
    {
        try
        {
            if (conn != null)
            {
                conn.close();
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}
