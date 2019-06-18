package com.spring.mybatis.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * <br>
 *
 * @author 永健
 * @since 2019/4/19 16:43
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
/**
 * 入口在这  重点****
 *
 * SuperMapperScannerRegister.java 为当前注解额处理类
 */
@Import(SuperMapperScannerRegister.class)
public @interface SuperMapperScan
{
    String basePackages() default  "";
}
