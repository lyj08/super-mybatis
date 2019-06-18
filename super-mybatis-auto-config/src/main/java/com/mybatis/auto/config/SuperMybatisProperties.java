package com.mybatis.auto.config;

import com.mybatis.session.SuperConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;

import java.io.IOException;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * <br>
 *  mybatis application.yml属性配置类
 * @author 永健
 * @since 2019/4/19 11:54
 */
@ConfigurationProperties(prefix = SuperMybatisProperties.MINI_MYBATIS_PREFIX)
public class SuperMybatisProperties
{

    // 扫描xml文件
    private static final ResourcePatternResolver resourceResolver = new PathMatchingResourcePatternResolver();

    public static final String MINI_MYBATIS_PREFIX = "super-mybatis";
    private String url;
    private String password;
    private String username;
    private String driverclassname;
    private String alias;



    /**
     * <br>
     *  mapper.xml所在路径
     */
    private String[] mapperlocations;

    /**
     * 排除自动配置问文件赋值
     */
    @NestedConfigurationProperty
    private SuperConfiguration configuration;

    public SuperConfiguration getConfiguration() {
        return configuration;
    }

    public String getUrl()
    {
        return url;
    }

    public void setUrl(String url)
    {
        this.url = url;
    }

    public String getPassword()
    {
        return password;
    }

    public void setPassword(String password)
    {
        this.password = password;
    }

    public String getUsername()
    {
        return username;
    }

    public void setUsername(String username)
    {
        this.username = username;
    }

    public String getDriverclassname()
    {
        return driverclassname;
    }

    public void setDriverclassname(String driverclassname)
    {
        this.driverclassname = driverclassname;
    }

    public String[] getMapperlocations()
    {
        return mapperlocations;
    }

    public void setMapperlocations(String[] mapperlocations)
    {
        this.mapperlocations = mapperlocations;
    }

    public String getAlias()
    {
        return alias;
    }

    public void setAlias(String alias)
    {
        this.alias = alias;
    }



    public Resource[] resolveMapperLocations() {
        return Stream.of(Optional.ofNullable(this.mapperlocations).orElse(new String[0]))
                .flatMap(location -> Stream.of(getResources(location)))
                .toArray(Resource[]::new);
    }

    private Resource[] getResources(String location) {
        try {
            return resourceResolver.getResources(location);
        } catch (IOException e) {
            return new Resource[0];
        }
    }
}
