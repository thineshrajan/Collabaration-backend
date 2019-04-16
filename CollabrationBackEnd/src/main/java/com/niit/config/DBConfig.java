package com.niit.config;


import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import com.niit.model.Job;
import com.niit.model.User;

@Configuration
@EnableTransactionManagement
public class DBConfig{
	 public DBConfig()
	 {
		 System.out.println("DBConfiguration class is instantiated"); 
	 }
	  @Bean
      public SessionFactory sessionFactory() 
	  {
		    System.out.println("Inside sessionFactory()");
			LocalSessionFactoryBuilder lsf=
					new LocalSessionFactoryBuilder(getDataSource());
			Properties hibernateProperties=new Properties();
			hibernateProperties.setProperty(
					"hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
			hibernateProperties.setProperty("hibernate.hbm2ddl.auto", "update");
			hibernateProperties.setProperty("hibernate.show_sql", "true");
			lsf.addProperties(hibernateProperties);
			lsf.scanPackages("com.niit.model");
		    return lsf.buildSessionFactory();
		}
		@Bean
		public DataSource getDataSource()
		{
			System.out.println("Inside getDataSource()");
		    BasicDataSource dataSource = new BasicDataSource();
		    dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
		    dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:XE");
		    dataSource.setUsername("system");
		    dataSource.setPassword("oracle");
		    return dataSource;	    
		}
		@Bean
		public HibernateTransactionManager hibTransManagement()
		{
			return new HibernateTransactionManager(sessionFactory());
		}
}
/*
import java.util.Properties;

import javax.sql.DataSource;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan("com.niit")
@EnableTransactionManagement
public class DBConfig {
	@Bean(name="sessionFactory")
	@Autowired
    public LocalSessionFactoryBean sessionFactory() 
	{
        LocalSessionFactoryBean sessionFactory = new LocalSessionFactoryBean();
       // SessionFactory factory = new Configuration().configure().buildSessionFactory();

        sessionFactory.setDataSource(dataSource());
        sessionFactory.setPackagesToScan(new String[] { "com.niit.model" });
        sessionFactory.setHibernateProperties(hibernateProperties());
        System.out.println("SessionCreated");
        return sessionFactory;
     }
	@Bean(name="dataSource")
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("oracle.jdbc.OracleDriver");
        dataSource.setUrl("jdbc:oracle:thin:@localhost:1521:xe");
        dataSource.setUsername("system");
        dataSource.setPassword("oracle");
		return dataSource;
}

	 private Properties hibernateProperties() {
	        Properties properties = new Properties();
	        properties.setProperty("hibernate.show_sql","true");
			properties.setProperty("hibernate.dialect", "org.hibernate.dialect.Oracle10gDialect");
			properties.setProperty("hibernate.hbm2ddl.auto","update");
			
	        return properties;        
	    }

	 @Bean
	    @Autowired
	    public HibernateTransactionManager transactionManager(SessionFactory s)
	 {
	       HibernateTransactionManager txManager = new HibernateTransactionManager();
	       txManager.setSessionFactory(s);
	       return txManager;
	    }

}
*/