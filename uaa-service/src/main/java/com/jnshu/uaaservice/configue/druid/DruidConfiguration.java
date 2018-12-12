package com.jnshu.uaaservice.configue.druid;


import com.alibaba.druid.pool.*;
import com.alibaba.druid.support.http.*;
import org.slf4j.*;
import org.springframework.boot.context.properties.*;
import org.springframework.boot.web.servlet.*;
import org.springframework.context.annotation.*;
import org.springframework.context.support.*;
import org.springframework.stereotype.*;

import javax.sql.*;
import java.sql.*;


/**
 * @program: purview
 * @description: DruidConfiguration  配置类
 * @author: Mr.huang
 * @create: 2018-11-29 07:59
 **/
@Configuration
public class DruidConfiguration {
	private static final Logger logger = LoggerFactory.getLogger(DruidConfiguration.class);

	 /**
	  *  配置信息前缀
	  */
	private static final String DB_PREFIX = "spring.datasource";

	/**
	 * @description 注入 servlet 注册的Bean
	 * @param
	 * @return org.springframework.boot.web.servlet.ServletRegistrationBean
	 * @author Mr.HUANG
	 * @date 2018/12/11
	 */
	@Bean
	public ServletRegistrationBean druidServlet() {
		logger.info("init Druid Servlet Configuration ");
		ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(
				new StatViewServlet(), "/druid/*");
		// IP白名单
		servletRegistrationBean.addInitParameter("allow", "*");
		// IP黑名单(共同存在时，deny优先于allow)
		servletRegistrationBean.addInitParameter("deny", "127.0.0.1");
		//控制台管理用户
		servletRegistrationBean.addInitParameter("loginUsername", "admin");
		servletRegistrationBean.addInitParameter("loginPassword", "admin");
		//是否能够重置数据 禁用HTML页面上的“Reset All”功能
		servletRegistrationBean.addInitParameter("resetEnable", "false");
		return servletRegistrationBean;
	}

	/**
	 * @description 注入过滤注册Bean
	 * @param
	 * @return org.springframework.boot.web.servlet.FilterRegistrationBean
	 * @author Mr.HUANG
	 * @date 2018/12/11
	 */
	@Bean
	public FilterRegistrationBean filterRegistrationBean() {
		FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean(new WebStatFilter());
		filterRegistrationBean.addUrlPatterns("/*");
		filterRegistrationBean.addInitParameter("exclusions", "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*");
		return filterRegistrationBean;
	}

	/**
	 * @description 属性来源占位配置Bean
	 * @param
	 * @return org.springframework.context.support.PropertySourcesPlaceholderConfigurer
	 * @author Mr.HUANG
	 * @date 2018/12/11
	 */
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
		return new PropertySourcesPlaceholderConfigurer();
	}


	@Component
	@ConfigurationProperties(prefix = DB_PREFIX) //解决 spring.datasource.filters=stat,wall,log4j 无法正常注册进去
	class IDataSourceProperties {

		private String url;
		private String username;
		private String password;
		private String driverClassName;

		 /**
		  * 初始化大小
		  */
		private int initialSize;

		private int minIdle;

		private int maxActive;

		private int maxWait;

		/**
		 *  配置隔多久进行一次检测(检测可以关闭的空闲连接)
		 */
		private int timeBetweenEvictionRunsMillis;

		/**
		 * 配置连接在池中的最小生存时间
		 */
		private int minEvictableIdleTimeMillis;

		private String validationQuery;

		private boolean testWhileIdle;

		private boolean testOnBorrow;

		private boolean testOnReturn;

		/**
		 * 打开PSCache，并且指定每个连接上PSCache的大小
		 */
		private boolean poolPreparedStatements;

		private int maxPoolPreparedStatementPerConnectionSize;

		/**
		 * 配置监控统计拦截的filters，去掉后监控界面sql无法统计，'wall'用于防火墙
		 */
		private String filters;

		/**
		 *  通过connectProperties属性来打开mergeSql功能；慢SQL记录
		 */
		private String connectionProperties;

		/**
		 * @description 声明其为Bean实例
		 * @param
		 * @return javax.sql.DataSource
		 * @author Mr.HUANG
		 * @date 2018/12/11
		 * 备注： Primary 的作用 在同样的DataSource中，首先使用被标注的DataSource
		 */
		@Bean
		@Primary
		public DataSource dataSource() {
			DruidDataSource datasource = new DruidDataSource();

			datasource.setUrl(url);
			datasource.setUsername(username);
			datasource.setPassword(password);
			datasource.setDriverClassName(driverClassName);
			datasource.setInitialSize(initialSize);
			datasource.setMinIdle(minIdle);
			datasource.setMaxActive(maxActive);
			datasource.setMaxWait(maxWait);
			datasource.setTimeBetweenEvictionRunsMillis(timeBetweenEvictionRunsMillis);
			datasource.setMinEvictableIdleTimeMillis(minEvictableIdleTimeMillis);
			datasource.setValidationQuery(validationQuery);
			datasource.setTestWhileIdle(testWhileIdle);
			datasource.setTestOnBorrow(testOnBorrow);
			datasource.setTestOnReturn(testOnReturn);
			datasource.setPoolPreparedStatements(poolPreparedStatements);
			datasource.setMaxPoolPreparedStatementPerConnectionSize(maxPoolPreparedStatementPerConnectionSize);
			try {
				datasource.setFilters(filters);
			} catch (SQLException e) {
				System.err.println("druid configuration initialization filter: " + e);
			}
			datasource.setConnectionProperties(connectionProperties);
			return datasource;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getDriverClassName() {
			return driverClassName;
		}

		public void setDriverClassName(String driverClassName) {
			this.driverClassName = driverClassName;
		}

		public int getInitialSize() {
			return initialSize;
		}

		public void setInitialSize(int initialSize) {
			this.initialSize = initialSize;
		}

		public int getMinIdle() {
			return minIdle;
		}

		public void setMinIdle(int minIdle) {
			this.minIdle = minIdle;
		}

		public int getMaxActive() {
			return maxActive;
		}

		public void setMaxActive(int maxActive) {
			this.maxActive = maxActive;
		}

		public int getMaxWait() {
			return maxWait;
		}

		public void setMaxWait(int maxWait) {
			this.maxWait = maxWait;
		}

		public int getTimeBetweenEvictionRunsMillis() {
			return timeBetweenEvictionRunsMillis;
		}

		public void setTimeBetweenEvictionRunsMillis(int timeBetweenEvictionRunsMillis) {
			this.timeBetweenEvictionRunsMillis = timeBetweenEvictionRunsMillis;
		}

		public int getMinEvictableIdleTimeMillis() {
			return minEvictableIdleTimeMillis;
		}

		public void setMinEvictableIdleTimeMillis(int minEvictableIdleTimeMillis) {
			this.minEvictableIdleTimeMillis = minEvictableIdleTimeMillis;
		}

		public String getValidationQuery() {
			return validationQuery;
		}

		public void setValidationQuery(String validationQuery) {
			this.validationQuery = validationQuery;
		}

		public boolean isTestWhileIdle() {
			return testWhileIdle;
		}

		public void setTestWhileIdle(boolean testWhileIdle) {
			this.testWhileIdle = testWhileIdle;
		}

		public boolean isTestOnBorrow() {
			return testOnBorrow;
		}

		public void setTestOnBorrow(boolean testOnBorrow) {
			this.testOnBorrow = testOnBorrow;
		}

		public boolean isTestOnReturn() {
			return testOnReturn;
		}

		public void setTestOnReturn(boolean testOnReturn) {
			this.testOnReturn = testOnReturn;
		}

		public boolean isPoolPreparedStatements() {
			return poolPreparedStatements;
		}

		public void setPoolPreparedStatements(boolean poolPreparedStatements) {
			this.poolPreparedStatements = poolPreparedStatements;
		}

		public int getMaxPoolPreparedStatementPerConnectionSize() {
			return maxPoolPreparedStatementPerConnectionSize;
		}

		public void setMaxPoolPreparedStatementPerConnectionSize(int maxPoolPreparedStatementPerConnectionSize) {
			this.maxPoolPreparedStatementPerConnectionSize = maxPoolPreparedStatementPerConnectionSize;
		}

		public String getFilters() {
			return filters;
		}

		public void setFilters(String filters) {
			this.filters = filters;
		}

		public String getConnectionProperties() {
			return connectionProperties;
		}

		public void setConnectionProperties(String connectionProperties) {
			this.connectionProperties = connectionProperties;
		}
	}

}
