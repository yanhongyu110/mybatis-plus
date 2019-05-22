package com.example.demo.config.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * 数据源路由实现类
 * AbstractRoutingDataSource(每执行一次数据库，动态获取DataSource)
 * @author Administrator
 */
public class DynamicDataSource extends AbstractRoutingDataSource  {

	@Override
	protected Object determineCurrentLookupKey() {
		//获取当前数据源
        return DataSourceContextHolder.getDataSource();
    }

}
