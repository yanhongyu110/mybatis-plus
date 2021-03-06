package com.example.demo.config.datasource;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.example.demo.config.datasource.ContextConst.DataSourceType;

/**
 * 自定义注解
 * 更改数据源时 在service方法上使用
 * @author Administrator
 *
 */
@Target({ElementType.TYPE,ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TargetDateSouce {
    DataSourceType value() default DataSourceType.MASTER;
}
