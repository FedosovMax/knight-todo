package com.knighttodo.app.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(
        entityManagerFactoryRef = "todocoreEntityManager",
        transactionManagerRef = "todocoreTransactionManager",
        basePackages = "com.knighttodo.todocore.gateway.privatedb")
public class TodocoreDBConfig {

    @Primary
    @Bean(name = "todocorePostgresqlDataSource")
    @ConfigurationProperties(prefix = "spring.todocore.datasource")
    public DataSource postgresqlDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Primary
    @Bean(name = "todocoreEntityManager")
    public LocalContainerEntityManagerFactoryBean postgresqlEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(postgresqlDataSource());
        em.setPackagesToScan("com.knighttodo.todocore.gateway.privatedb.representation");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaProperties(additionalProperties());
        return em;
    }

    private Properties additionalProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", "org.hibernate.dialect.PostgreSQL10Dialect");
        return properties;
    }

    @Primary
    @Bean(name = "todocoreTransactionManager")
    public PlatformTransactionManager postgresqlTransactionManager(@Qualifier("todocoreEntityManager") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
