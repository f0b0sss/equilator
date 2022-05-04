package com.equilator.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:database.properties")
public class DataBaseConfig{
    private final ApplicationContext applicationContext;

    @Autowired
    public DataBaseConfig(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    @Bean
    public DataSource dataSource(@Value("${database.url}") String url,
                                 @Value("${login}") String login,
                                 @Value("${password}") String password,
                                 @Value("${driverClassName}") String driverClassName){
        final HikariConfig config = new HikariConfig();
        config.setJdbcUrl(url);
        config.setUsername(login);
        config.setPassword(password);
        config.setDriverClassName(driverClassName);
        config.setMaxLifetime(30000);

        return new HikariDataSource(config);
    }

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
    JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }



}
