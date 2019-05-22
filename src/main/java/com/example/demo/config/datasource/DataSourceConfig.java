package com.example.demo.config.datasource;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;

public class DataSourceConfig {
	@Bean // 2.0.0版本只需要修改pom文件中的依赖，yml的配置，和@ConfigurationProperties注解引入yml文件配置，其余不变 //
	@ConfigurationProperties(prefix = "spring.datasource.master")
	public DruidDataSource masterDataSource() {
		return DruidDataSourceBuilder.create().build();
	}

	@Bean //
	@ConfigurationProperties(prefix = "spring.datasource.cluster")
	public DruidDataSource clusterDataSource() {
		return DruidDataSourceBuilder.create().build();
	}

	@Primary
	@Bean
	public DataSource dynamicDataSource() {
		DynamicDataSource dynamicDataSource = new DynamicDataSource();
		// 配置默认数据源
		dynamicDataSource.setDefaultTargetDataSource(masterDataSource());
		// 配置多数据源这里的key一定要是string类型，枚举类型并不支持，所以用到枚举中name()方法转成string，或者用toString方法。
		HashMap<Object, Object> dataSourceMap = new HashMap();
		dataSourceMap.put(ContextConst.DataSourceType.MASTER.name(), masterDataSource());
		dataSourceMap.put(ContextConst.DataSourceType.CLUSTER.name(), clusterDataSource());
		dynamicDataSource.setTargetDataSources(dataSourceMap);
		return dynamicDataSource;
	}

	/**
	 * 配置@Transactional注解事务
	 * 
	 * @return
	 */
	@Bean
	public PlatformTransactionManager transactionManager() {
		return new DataSourceTransactionManager(dynamicDataSource());
	}

}
