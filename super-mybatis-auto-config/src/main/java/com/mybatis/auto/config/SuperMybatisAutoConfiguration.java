package com.mybatis.auto.config;

import com.mybatis.session.SuperConfiguration;
import com.mybatis.session.SuperSqlSessionFactory;
import com.spring.mybatis.spring.SuperSqlSessionFactoryBean;
import com.spring.mybatis.spring.SuperSqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * <br>
 * 自动配置类
 *
 * @author 永健
 * @since 2019/4/19 11:54
 */
@Configuration
//@ConditionalOnClass({SuperSqlSessionFactory.class,SuperSqlSessionFactoryBean.class})

// MybatisProperties 先加载
@EnableConfigurationProperties(SuperMybatisProperties.class)
public class SuperMybatisAutoConfiguration implements InitializingBean
{
    private static Logger logger = LoggerFactory.getLogger(SuperMybatisAutoConfiguration.class);
    /**
     * 注入 配置文件
     */
    private SuperMybatisProperties mybatisProperties;


    public SuperMybatisAutoConfiguration(SuperMybatisProperties mybatisProperties)
    {
        logger.info("$$$$$$ 注入配置文件");
        this.mybatisProperties = mybatisProperties;
    }

    @Bean
    public SuperJdbc superJdbc()
    {
        SuperJdbc superJdbc = new SuperJdbc(mybatisProperties);
        return superJdbc;
    }

    @Bean
    public SuperDatasource superDatasource(SuperJdbc superJdbc)
    {
        logger.info("$$$$$$ 注入数据源");
        SuperDatasource superDatasource = new SuperDatasource();
        superDatasource.setConnection(superJdbc.getConnection());
        return superDatasource;
    }


    /**
     * 注入SuperSqlSessionFactory
     *
     * @throws Exception
     */
    @Bean
    @ConditionalOnMissingBean
    public SuperSqlSessionFactory superSqlSessionFactory() throws Exception
    {
        logger.info("$$$$$$ 注入SuperSqlSessionFactory");
        SuperSqlSessionFactoryBean factoryBean = new SuperSqlSessionFactoryBean();
        // configration 也从此开始实例化
        SuperConfiguration configuration = this.mybatisProperties.getConfiguration();
        configuration = configuration == null ? new SuperConfiguration() : configuration;
        configuration.setAlias(this.mybatisProperties.getAlias());
        configuration.setConnection(this.superJdbc().getConnection());
        factoryBean.setConfiguration(configuration);
        factoryBean.setMapperLocations(this.mybatisProperties.resolveMapperLocations());
        return factoryBean.getObject();
    }


    @Bean
    @ConditionalOnMissingBean
    public SuperSqlSessionTemplate sqlSessionTemplate(SuperSqlSessionFactory sqlSessionFactory)
    {
        return new SuperSqlSessionTemplate(sqlSessionFactory);
    }


    @Override
    public void afterPropertiesSet() throws Exception
    {
        if (this.mybatisProperties == null) {
            throw new Exception("找不到数据源的配置");
        }
    }


}
