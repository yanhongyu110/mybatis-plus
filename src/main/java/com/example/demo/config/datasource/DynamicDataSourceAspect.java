package com.example.demo.config.datasource;

import java.lang.reflect.Method;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;
/**
 * 动态数据源通知
 * @author Administrator
 *
 */
@Component
@Aspect
@Order(-1) //保证在@Transactional之前执行，必须加上，不然无法分辨是哪个数据源在执行事务
@Slf4j
public class DynamicDataSourceAspect {
	/**
	 * 执行service前 开启数据源
	 * @param point
	 */
	@SuppressWarnings("rawtypes")
	@Before("execution(* com.example.demo.service..*.*(..))")
	public void before(JoinPoint point) {
		try {
			TargetDateSouce annotationOfClass = point.getTarget().getClass().getAnnotation(TargetDateSouce.class);
			//获取service方法名称
			String methodName = point.getSignature().getName();
			//获取 方法 参数类型
			Class[] parameterTypes = ((MethodSignature) point.getSignature()).getParameterTypes();
			Method method = point.getTarget().getClass().getMethod(methodName, parameterTypes);
			TargetDateSouce methodAnnotation = method.getAnnotation(TargetDateSouce.class);
			//获取 数据源注解
			methodAnnotation = methodAnnotation == null ? annotationOfClass : methodAnnotation;
			//默认使用MASTER
			ContextConst.DataSourceType dataSourceType = methodAnnotation != null && methodAnnotation.value() != null
					? methodAnnotation.value()
					: ContextConst.DataSourceType.MASTER;
			//设置数据源
			DataSourceContextHolder.setDataSource(dataSourceType.name());
		} catch (NoSuchMethodException e) {
			log.error("error", e);
		}
	}

	/**
	 * service 执行完后清理数据源
	 * @param point
	 */
	@After("execution(* com.example.demo.service..*.*(..))")
	public void after(JoinPoint point) {
		DataSourceContextHolder.clearDataSource();
	}
}
