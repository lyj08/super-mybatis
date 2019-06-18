package com.spring.mybatis.spring;


import com.mybatis.mapperpare.MapperBuidler;
import com.mybatis.mapperpare.TableEntity;
import com.mybatis.session.SuperConfiguration;
import com.mybatis.session.SuperSqlSessionFactory;
import com.mybatis.session.SuperSqlSessionFactoryBuilder;
import com.mybatis.utils.SuperReflectUtil;
import com.spring.mybatis.annotation.SuperTableId;
import com.spring.mybatis.annotation.SuperTableName;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;

import java.lang.reflect.Field;
import java.util.List;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/4/19 11:46
 */
public class SuperSqlSessionFactoryBean implements FactoryBean<SuperSqlSessionFactory>, InitializingBean
{
    private Resource[] mapperLocations;

    private SuperSqlSessionFactory sqlSessionFactory;
    private SuperSqlSessionFactoryBuilder sqlSessionFactoryBuilder = new SuperSqlSessionFactoryBuilder();
    private SuperConfiguration configuration;

    public Resource[] getMapperLocations()
    {
        return mapperLocations;
    }

    public void setMapperLocations(Resource[] mapperLocations)
    {
        this.mapperLocations = mapperLocations;
    }

    /**
     * <br>
     * 创建bean的时候回调用此方法
     *
     * @param
     */
    @Override
    public SuperSqlSessionFactory getObject() throws Exception
    {
        if (this.sqlSessionFactory == null) {
            afterPropertiesSet();
        }
        return this.sqlSessionFactory;
    }

    @Override
    public Class<?> getObjectType()
    {
        return this.sqlSessionFactory == null ? SuperSqlSessionFactory.class : this.sqlSessionFactory.getClass();
    }

    @Override
    public void afterPropertiesSet()
    {
        this.sqlSessionFactory = buildSqlSessionFactory();
    }

    /**
     * 配置初始化Configration 就在这里开始
     * 此处简化操作
     *
     * @return
     */
    private SuperSqlSessionFactory buildSqlSessionFactory()
    {
        final SuperConfiguration configuration = this.configuration;

        // 代理对象的生成也应该在这
        configuration.setMapperPackge(configuration.getMapperPackge());
        MapperBuidler mapperBuidler = new MapperBuidler(this.configuration,this.mapperLocations);

        // 解析实体
        pareEntity();

        // 解析Mapper方法
        mapperBuidler.pare();
        return this.sqlSessionFactory = this.sqlSessionFactoryBuilder.build(configuration);
    }

    public SuperConfiguration getConfiguration()
    {
        return configuration;
    }

    public void setConfiguration(SuperConfiguration configuration)
    {
        this.configuration = configuration;
    }


    private void pareEntity(){
        String alias = configuration.getAlias();
        List<Class<?>> classes = SuperReflectUtil.getClasses(alias);
        TableEntity tableEntity = new TableEntity();
        String[] fileds=null;
        for (Class<?> aClass : classes) {
            SuperTableName annotation = aClass.getAnnotation(SuperTableName.class);
            if (annotation!=null){
                tableEntity.setId(aClass.getName());
                Field[] fieldlist = aClass.getFields();
                for (int i=0;i<fieldlist.length;i++) {
                    SuperTableId tableId = fieldlist[i].getAnnotation(SuperTableId.class);
                    if (tableId!=null){
                        tableEntity.setPrimarykey(fieldlist[i].getName());
                        tableEntity.setKeyType(tableId.type().getKey());
                    }
                    fileds=new String[fieldlist.length];
                    fileds[i]=fieldlist[i].getName();
                }
            }
            configuration.addTableEntityMap(tableEntity);
        }
    }


}
