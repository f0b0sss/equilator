package com.equilator.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:database.properties")
@EnableTransactionManagement
public class DatabaseConfig {


    @Bean
    public Flyway flyway(DataSource dataSource){
        return Flyway.configure()
                .outOfOrder(true)
                .locations("classpath:db/migration")
                .dataSource(dataSource)
                .load();
    }

    @Bean
    public InitializingBean flywayMigrate(Flyway flyway){
        return flyway::migrate;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }





}
