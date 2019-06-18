package com.mybatis.demo.test;

import com.mybatis.mapping.SuperMappedStatement;
import com.mybatis.mapping.SuperSqlCommandType;
import com.mybatis.utils.SuperReflectUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/4/24 18:04
 */
public class Test
{
    public static void main(String[] args)
    {
        // F:\IDEAWorkParth\superMybatis\super-mybatis-demo\target\classes\mapper\UserMapper.xml
        List<String> mapper = SuperReflectUtil.getFilePath("mapper");


    }


}
