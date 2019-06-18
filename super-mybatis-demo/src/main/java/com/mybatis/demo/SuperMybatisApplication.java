package com.mybatis.demo;

import com.spring.mybatis.annotation.SuperMapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@SuperMapperScan(basePackages = "com.mybatis.demo.sys.mapper")
public class SuperMybatisApplication
{

    public static void main(String[] args)
    {
        SpringApplication.run(SuperMybatisApplication.class, args);
    }

}
