package com.example.demo.config.datasource;

import java.util.HashMap;

import javax.sql.DataSource;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
@Configuration
public class DataSourceConfig {
	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.druid.master")
	public DruidDataSource masterDataSource() {
		return DruidDataSourceBuilder.create().build();
	}

	@Bean
	@ConfigurationProperties(prefix = "spring.datasource.druid.cluster")
	public DruidDataSource clusterDataSource() {
		return DruidDataSourceBuilder.create().build();
	}

	@Primary
	@Bean
	public DataSource dynamicDataSource() {
		// 创建当前数据源
		DynamicDataSource dynamicDataSource = new DynamicDataSource();
		// 配置默认数据源
		dynamicDataSource.setDefaultTargetDataSource(masterDataSource());
		// 配置多数据源这里的key一定要是string类型
		HashMap<Object, Object> dataSourceMap = new HashMap<Object, Object>();
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
