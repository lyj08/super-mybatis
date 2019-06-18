package com.spring.mybatis.annotation;

import java.lang.annotation.*;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/4/23 15:55
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Documented
public @interface SuperTableId
{
    /**
     * <p>
     * 字段值（驼峰命名方式，该值可无）
     * </p>
     */
    String value() default "";

    /**
     * <p>
     * 主键ID
     * </p>
     * {@link IdType}
     */
    IdType type() default IdType.NONE;
}
