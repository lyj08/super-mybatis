package com.spring.mybatis.annotation;

import java.lang.annotation.*;

/**
 * <br>
 * 实体类注解
 * @author 永健
 * @since 2019/4/23 15:50
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface SuperTableName
{
    /**
     * <br>
     * 表名
     */
    String name() default "" ;
}
