package com.jnshu.reception.Configuration;

import com.alibaba.druid.pool.*;
import org.apache.ibatis.session.*;
import org.mybatis.spring.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.boot.autoconfigure.jdbc.*;
import org.springframework.context.annotation.*;
import org.springframework.context.annotation.Configuration;

import javax.sql.*;

/**
 * @program: morepineapple
 * @description:
 * @author: Mr.huang
 * @create: 2019-02-03 21:59
 **/
@Configuration
public class MybatisConfig {
	@Autowired
	private DataSourceProperties dataSourceProperties;


	@Bean(name = "dataSource")
	public DataSource dataSource() {

		DruidDataSource dataSource = new DruidDataSource();
		dataSource.setUrl(dataSourceProperties.getUrl());
		System.out.println(dataSourceProperties.getUrl());
		dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
		dataSource.setUsername(dataSourceProperties.getUsername());
		dataSource.setPassword(dataSourceProperties.getPassword());

		return dataSource;

	}

	public SqlSessionFactory sqlSessionFactory() throws Exception {
		SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
		sqlSessionFactoryBean.setDataSource(dataSource());
		return sqlSessionFactoryBean.getObject();
	}

}
