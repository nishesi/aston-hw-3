package ru.astondevs.attractionservice.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import jakarta.persistence.EntityManagerFactory;
import liquibase.integration.spring.SpringLiquibase;
import lombok.AllArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.astondevs.attractionservice.model.Attraction;
import ru.astondevs.attractionservice.model.Service;
import ru.astondevs.attractionservice.model.Settlement;

import javax.sql.DataSource;

import static org.hibernate.cfg.JdbcSettings.JAKARTA_JTA_DATASOURCE;
import static org.hibernate.cfg.SchemaToolingSettings.HBM2DDL_AUTO;


@Configuration
@PropertySource("classpath:/application.properties")
@EnableJpaRepositories("ru.astondevs.attractionservice.dao")
@EnableTransactionManagement
@AllArgsConstructor
public class ModelConfig {

    private final Environment env;

    @Bean(destroyMethod = "close")
    HikariDataSource dataSource() {
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setJdbcUrl(env.getProperty("datasource.url"));
        hikariConfig.setUsername(env.getProperty("datasource.username"));
        hikariConfig.setPassword(env.getProperty("datasource.password"));
        hikariConfig.setDriverClassName(env.getProperty("datasource.driver-class-name"));
        hikariConfig.setMaximumPoolSize(20);
        return new HikariDataSource(hikariConfig);
    }

    @Bean
    SpringLiquibase liquibase(DataSource dataSource) {
        SpringLiquibase liquibase = new SpringLiquibase();
        liquibase.setChangeLog("classpath:liquibase/master.sql");
        liquibase.setDataSource(dataSource);
        return liquibase;
    }

    @DependsOn("liquibase")
    @Bean(name = {"sessionFactory", "entityManagerFactory"}, destroyMethod = "close")
    SessionFactory sessionFactory(DataSource dataSource) {
        StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySetting(JAKARTA_JTA_DATASOURCE, dataSource)
                .applySetting(HBM2DDL_AUTO, env.getProperty("jpa.hibernate.ddl-auto"))
                .build();
        return new MetadataSources(serviceRegistry)
                .addAnnotatedClass(Settlement.class)
                .addAnnotatedClass(Attraction.class)
                .addAnnotatedClass(Service.class)
                .buildMetadata()
                .buildSessionFactory();
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
        JpaTransactionManager txManager = new JpaTransactionManager();
        txManager.setEntityManagerFactory(entityManagerFactory);
        return txManager;
    }
}
