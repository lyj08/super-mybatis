package com.mybatis.mapperpare;

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
 * @since 2019/4/24 18:15
 */
public class SuperXMLMapperBuilder
{
    public static void main(String[] args) throws FileNotFoundException, DocumentException
    {
        //定义一个XML文档解析器
        SAXReader sax = new SAXReader();

        //获取一个document对象
        Document doc = sax.read(new FileInputStream("src/Students.xml"));
        //获取XML文档的根元素
        Element rootElement = doc.getRootElement();
        //把根元素来里面的student标签放到List中去
        List studentList = rootElement.elements("student");
        //使用迭代器来输出这个集合
        for (Iterator iterator = studentList.iterator(); iterator.hasNext(); ) {
            Element element = (Element) iterator.next();
            //获得子元素里面的属性值
            String idcard = element.attributeValue("idcard");
            System.out.println(idcard);
            //获得这个子元素中的迭代器
            Iterator iter = element.elementIterator();
            //取出这些子元素的中的文本内容
            while (iter.hasNext()) {
                Element el = (Element) iter.next();
                String tagName = el.getName();
                if (tagName.equals("name")) {
                    System.out.println(el.getText());
                }
                if (tagName.equals("sex")) {
                    System.out.println(el.getStringValue());
                }
            }
        }
    }
}
