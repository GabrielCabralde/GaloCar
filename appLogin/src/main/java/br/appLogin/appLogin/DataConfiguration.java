package br.appLogin.appLogin;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

@Configuration
public class DataConfiguration {
	
	@Bean
	public DataSource datasourse() {
		DriverManagerDataSource dataSourse = new DriverManagerDataSource();
		dataSourse.setDriverClassName("com.mysql.cj.jdbc.Driver");
		dataSourse.setUrl("jdbc:mysql://localhost:3306/applogin?userTimezone=true&serverTimezone=UTC");
		dataSourse.setUsername("root");
		dataSourse.setPassword("root");
		return dataSourse;
		
	}
	
	@Bean
	public JpaVendorAdapter jpaVendorAdapter() {
		HibernateJpaVendorAdapter adapter = new HibernateJpaVendorAdapter();
		adapter.setDatabase(Database.MYSQL);
		adapter.setShowSql(true);
		adapter.setGenerateDdl(true);
		adapter.setDatabasePlatform("org.hibernate.dialect.MariaDBDialect");
		adapter.setPrepareConnection(true);
		return adapter;
	}
	
}
