package me.spring.consumer.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {
	@Value("${mysql-datasource}")
	private String dataSourceUrl;

	@Value("${mysql-username}")
	private String dataSourceUsername;

	@Value("${mysql-password}")
	private String dataSourcePassword;

	@Bean
	public DataSource dataSource() {
		return DataSourceBuilder.create()
			.url(dataSourceUrl)
			.username(dataSourceUsername)
			.password(dataSourcePassword)
			.build();
	}
}
