package com.spring.mybatis.mapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;

import java.util.Set;

/**
 * <br>
 * 利用Spring的扫描器接口 重写方法 扫描自定义的MapperScan注解 将其包下的接口 生成代理类注册到Spring容器中
 *
 * @author 永健
 * @since 2019/4/21 12:07
 */
public class SuperClassPathMapperScanner extends ClassPathBeanDefinitionScanner
{
    private static Logger logger = LoggerFactory.getLogger(SuperClassPathMapperScanner.class);

    private Class<? extends SuperMapperProxyBean> mapperFactoryBeanClass = SuperMapperProxyBean.class;


    /**
     * @param registry
     * @param useDefaultFilters
     *         = false 不使用默认的扫描过滤器
     */
    public SuperClassPathMapperScanner(BeanDefinitionRegistry registry, boolean useDefaultFilters)
    {
        super(registry, useDefaultFilters);
    }

    /**
     * 利用Spring扫描器将包下的类封装成BeanDefinitionHolder 注入到Spring中
     * @param basePackages
     * @return
     */
    @Override
    public Set<BeanDefinitionHolder> doScan(String... basePackages)
    {
        logger.info("$$$$$$ 开始扫描注解包下的类...");
        Set<BeanDefinitionHolder> beanDefinitions = super.doScan(basePackages);
        if(beanDefinitions.size()>0){
            changeMapperToMapperProxy(beanDefinitions);
        }
        return beanDefinitions;
    }

    /**
     * 将代理对象注入到Spring中；
     * 重新修改BeanDefinitionHolder 里面的BeanClass属性 改为代理对象
     * 否则将会实例化mapper失败 它只是一个接口没有实现；
     * SuperMapperProxyBean 实现 FactoryBean 重写getObject() 实例化的时候
     *  从当前方法获取代理对象实例
     *
     * getObject()获取对象的时候通过Configration获取mapper
     *
     * 只是注入Bendefine 并没有实例化
     * 在使用的时候就会实例化 在 SuperMapperProxyBean 的getObject方法去拿
     * @param beanDefinitionHolders
     */
    private void changeMapperToMapperProxy(Set<BeanDefinitionHolder> beanDefinitionHolders){
        //生成代理对象注册bean
        logger.info("$$$$$$ 扫描完成,开始向Spring中注入代理对象...");
        GenericBeanDefinition definition;
        for (BeanDefinitionHolder holder : beanDefinitionHolders)
        {
            definition = (GenericBeanDefinition) holder.getBeanDefinition();
            String beanClassName = definition.getBeanClassName();
            definition.getConstructorArgumentValues().addGenericArgumentValue(beanClassName);
            // 将 原来的Mapper接口 BennClass更换为代理对象
            definition.setBeanClass(this.mapperFactoryBeanClass);
            definition.setAutowireMode(AbstractBeanDefinition.AUTOWIRE_BY_TYPE);
        }
        logger.info("$$$$$$ 完成Spring容器中注入代理对象！");
    }

    /**
     * 重写这个 isCandidateComponent 方法 判断自己要扫描的类型
     *
     * @param beanDefinition
     */
    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition)
    {
        return beanDefinition.getMetadata().isInterface() && beanDefinition.getMetadata().isIndependent();
    }

    /**
     * 重写过滤器 用自己的排除规则
     */
    @Override
    public void registerDefaultFilters()
    {
        /**
         * 扫描过滤器
         * 扫码当前包下的所有的类
         */
        this.addIncludeFilter((metadataReader, metadataReaderFactory) -> true);

        /**
         * 排除 package-info.java
         */
        this.addExcludeFilter((metadataReader, metadataReaderFactory) -> {
            String className = metadataReader.getClassMetadata().getClassName();
            return className.endsWith("package-info");
        });
    }
}
