package com.mybatis.mapperpare;

import com.mybatis.MyException;
import com.mybatis.genericTokenParser.GenericTokenParser;
import com.mybatis.mapping.JdbcType;
import com.mybatis.mapping.SuperMappedStatement;
import com.mybatis.mapping.SuperParameterMapping;
import com.mybatis.mapping.SuperSqlCommandType;
import com.mybatis.session.SuperConfiguration;
import com.mybatis.utils.SuperReflectUtil;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.core.io.Resource;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/4/23 17:13
 */
public class MapperBuidler
{
    private SuperConfiguration configuration;
    private List<String> nameSpace;
    private Resource[] mapperLoactions;

    private LinkedList<SuperParameterMapping> parameterMappings;
    private SuperMappedStatement mappedStatement;


    public MapperBuidler(SuperConfiguration configuration,Resource[] mapperLoactions)
    {
        this.configuration = configuration;
        this.mapperLoactions = mapperLoactions;
    }

    /**
     * <br>
     * 解析方法
     */
    public void pare()
    {
        check();
        // 解析xml 并进行绑定
        pareXml();
    }


    /**
     * <br>
     * 解析xml节点
     */
    private void pareXml()
    {
        bindMapperFromNameSpace();
    }

    public String handleToken(String property)
    {
        parameterMappings.add(buildParameterMappings(property));
        return "?";
    }

    private SuperParameterMapping buildParameterMappings(String property)
    {
        SuperParameterMapping parameterMapping = new SuperParameterMapping();
        parameterMapping.setName(property);
        parameterMapping.setType(null);
        return parameterMapping;
    }


    /**
     * <br>
     * 解析xml 拿到命名空间
     * 将命名空间的类添加到代理对象的map里面
     */
    private void bindMapperFromNameSpace()
    {
        //定义一个XML文档解析器
        SAXReader sax = new SAXReader();

        int length = this.mapperLoactions.length;


        try {
            for (Resource resource:this.mapperLoactions) {
                //获取一个document对象
                Document doc = sax.read(new FileInputStream(resource.getURL().getPath()));
                //获取XML文档的根元素
                Element rootElement = doc.getRootElement();
                // 读取mapper 标签的属性 值
                String namespace = rootElement.attributeValue("namespace");

                // 将代理对象放到configration中去 根据全量名字生成代理
                this.configuration.addMappers(namespace);

                //把根元素来里面的student标签放到List中去
                List<Element> elements = rootElement.elements();

                // 解析节点
                pareSqlNode(elements, namespace);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * <br>
     * 解析SqlNode select|update|select|delete
     */
    private void pareSqlNode(List<Element> elements, String namespace) throws ClassNotFoundException
    {
        //拿到 mapper标签下的属性 select update insert delete 标签
        for (Iterator iterator = elements.iterator(); iterator.hasNext(); ) {
            Element element = (Element) iterator.next();

            // 标签名字
            String name = element.getName();
            // 获取sql类型
            SuperSqlCommandType type = getType(name);
            // 返回值
            String resultType = element.attributeValue("resultType");
            // 入参
            String parameterType = element.attributeValue("parameterType");
            // sql Id
            String sqlId = element.attributeValue("id");

            // statmentId
            String stateMentId = namespace + "." + sqlId;

            // sql文本内容
            String sql = element.getStringValue();

            SuperMappedStatement mappedStatement = new SuperMappedStatement();
            mappedStatement.setParamType(parameterType);
            this.parameterMappings = new LinkedList<>();
            mappedStatement.setId(stateMentId);
            mappedStatement.setSqlCommandType(type);

            switch (type) {
                case SELECT:
                    if (resultType == null) {
                        throw new MyException(stateMentId + " 查询语句没有返回值！");
                    }

                default:
                    if (resultType == null) {
                        resultType = "java.lang.Integer";
                    }
            }
            mappedStatement.setReturnType(Class.forName(resultType));

            // 处理掉这个东西 #{}
            sql = GenericTokenParser.parse(this, sql);
            mappedStatement.setSql(sql);
            this.configuration.addMappedStatement(mappedStatement);
            mappedStatement.setParameterMappingList(this.parameterMappings);
            mappedStatement.setConfiguration(this.configuration);
        }
    }


    private static SuperSqlCommandType getType(String a)
    {

        if ("delete".equals(a)) {
            return SuperSqlCommandType.DELETE;
        } else if ("update".equals(a)) {
            return SuperSqlCommandType.UPDATE;
        } else if ("insert".equals(a)) {
            return SuperSqlCommandType.INSERT;
        } else if ("select".equals(a)) {
            return SuperSqlCommandType.SELECT;
        } else {
            return SuperSqlCommandType.UNKNOWN;
        }
    }

    /**
     * <br>
     * 检查
     */
    private void check()
    {
        if (this.configuration == null) {
            try {
                throw new IllegalAccessException("null");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }
}
