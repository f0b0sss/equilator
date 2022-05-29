package com.equilator.testConfig;

import com.equilator.DAO.DBUtils;
import com.equilator.DAO.DefaultData;
import com.equilator.repository.RangeRepository;
import com.equilator.repository.RangeRepositoryImpl;
import com.equilator.repository.UserRepository;
import com.equilator.repository.UserRepositoryImpl;
import com.equilator.services.RangeService;
import com.equilator.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Configuration
@PropertySource("database.properties")
@EnableTransactionManagement
public class DataBaseTestConfig {

    @Autowired
    private Environment env;

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getProperty("spring.datasource.driver-class-name"));
        dataSource.setUrl(env.getProperty("spring.datasource.url"));
        dataSource.setUsername(env.getProperty("spring.datasource.username"));
        dataSource.setPassword(env.getProperty("spring.datasource.password"));

        return dataSource;
    }

    @Bean
    public DBUtils dbUtils(){
        return new DBUtils();
    };

    @Bean
    public DefaultData defaultData(){
        return new DefaultData();
    }

    @Bean
    public UserService userService(){
        return new UserService();
    }

    @Bean
    public UserRepository userRepository(){
        return new UserRepositoryImpl();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public RangeRepository repository(){
        return new RangeRepositoryImpl(dbUtils());
    }

    @Bean
    public RangeService rangeService(){
        return new RangeService();
    }

}
