package com.knighttodo.app.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
        entityManagerFactoryRef = "characterEntityManager",
        transactionManagerRef = "characterTransactionManager",
        basePackages = "com.knighttodo.character.gateway.privatedb")
public class CharacterDBConfig {

    @Bean(name = "characterPostgresqlDataSource")
    @ConfigurationProperties(prefix = "spring.character.datasource")
    public DataSource postgresqlDataSource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "characterEntityManager")
    public LocalContainerEntityManagerFactoryBean postgresqlEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(postgresqlDataSource());
        em.setPackagesToScan("com.knighttodo.character.gateway.privatedb.representation");
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

    @Bean(name = "characterTransactionManager")
    public PlatformTransactionManager postgresqlTransactionManager(@Qualifier("characterEntityManager") EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }
}
