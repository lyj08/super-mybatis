package com.spring.mybatis.annotation;

import com.spring.mybatis.mapper.SuperClassPathMapperScanner;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import java.util.Map;

/**
 * <br>
 * 扫描器继承Spring ImportBeanDefinitionRegistrar 扩展接口做扫描
 *
 * @author 永健
 * @since 2019/4/19 16:45
 */
public class SuperMapperScannerRegister implements ImportBeanDefinitionRegistrar
{

    private AnnotationAttributes mapperScanAttrs = null;

    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata, BeanDefinitionRegistry beanDefinitionRegistry)
    {
        this.mapperScanAttrs = AnnotationAttributes.fromMap(annotationMetadata.getAnnotationAttributes(SuperMapperScan.class.getName()));

        /**
         * 扫描器
         */
        SuperClassPathMapperScanner scanner = new SuperClassPathMapperScanner(beanDefinitionRegistry, false);
        // 扫描规则
        scanner.registerDefaultFilters();
        scanner.doScan(this.mapperScanAttrs.getStringArray("basePackages"));
    }


    /**
     * 自定义扫描
     */
//    private void register(Map map, BeanDefinitionRegistry registry)
//    {
//        /**
//         * mapper是没有实现类的，只能通过代理来想Spring注入一个代理对象
//         */
//        Class<? extends MapperProxyFactory> mapperFactoryBeanClass = MapperProxyFactory.class;
//
//        // 注解包下的路径
//        String basePackage = (String) map.get("basePackge");
//        List<Class<?>> classes = ClassUtils.getClasses(basePackage);
//        for (int j = 0; j < classes.size(); j++)
//        {
//            Class<?> aClass = classes.get(j);
//            BeanDefinitionBuilder builder = BeanDefinitionBuilder.genericBeanDefinition(aClass);
//            GenericBeanDefinition definition = (GenericBeanDefinition) builder.getRawBeanDefinition();
//            definition.getConstructorArgumentValues().addGenericArgumentValue(definition.getBeanClassName());
//            definition.setBeanClass(mapperFactoryBeanClass);
//            registry.registerBeanDefinition(toLowerCase(aClass.getSimpleName()), definition);
//            definition.setAutowireMode(GenericBeanDefinition.AUTOWIRE_BY_TYPE);
//        }
//    }
//
//    private static String toLowerCase(String str)
//    {
//        String one = str.substring(0, 1).toLowerCase();
//        String substring = str.substring(1, str.length());
//        return one + substring;
//    }

}
