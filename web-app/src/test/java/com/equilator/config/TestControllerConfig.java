package com.equilator.config;


import com.equilator.DAO.DBUtils;
import com.equilator.DAO.DefaultData;
import com.equilator.models.Error;
import com.equilator.models.UploadForm;
import com.equilator.models.calculator.CalculatorMainTable;
import com.equilator.models.calculator.GameInfo;
import com.equilator.models.calculator.RangeDB;
import com.equilator.repository.RangeRepository;
import com.equilator.repository.RangeRepositoryImpl;
import com.equilator.repository.UserRepository;
import com.equilator.repository.UserRepositoryImpl;
import com.equilator.services.Calculate;
import com.equilator.services.CombinationGenerator;
import com.equilator.services.RangeService;
import com.equilator.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.filter.HiddenHttpMethodFilter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.thymeleaf.extras.springsecurity5.dialect.SpringSecurityDialect;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;

import javax.sql.DataSource;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

@Configuration
@PropertySource("database.properties")
public class TestControllerConfig {

    @Autowired
    private Environment env;


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
    public DBUtils dbUtils() {
        return new DBUtils();
    }

    ;

    @Bean
    public DefaultData defaultData() {
        return new DefaultData();
    }


    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(dataSource());
    }

    @Bean
    public RangeRepository repository() {
        return new RangeRepositoryImpl(dbUtils());
    }

    @Bean
    public UserRepository userRepository() {
        return new UserRepositoryImpl();
    }


    @Bean
    public RangeService rangeService() {
        return new RangeService();
    }

    @Bean
    public CalculatorMainTable calculatorMainTable() {
        return defaultData().getCalculatorMainTables().get(0);
    }

    @Bean
    public Calculate calculate() {
        return new Calculate(defaultData());
    }

    @Bean
    public CombinationGenerator combinationGenerator() {
        return new CombinationGenerator();
    }

    @Bean
    public Error error() {
        return new Error();
    }

    @Bean
    public GameInfo gameInfo() {
        return new GameInfo();
    }

    @Bean
    public RangeDB rangeDB() {
        return new RangeDB();
    }

    @Bean
    public UploadForm uploadForm() {
        return new UploadForm();
    }

    @Bean
    public UserService userService() {
        return new UserService();
    }

    @Bean
    public SpringSecurityDialect springSecurityDialect() {
        return new SpringSecurityDialect();
    }

    @Bean
    HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

    @Bean
    public ClassLoaderTemplateResolver adminClassLoaderTemplateResolver() {
        ClassLoaderTemplateResolver classLoaderTemplateResolver = new ClassLoaderTemplateResolver();
        classLoaderTemplateResolver.setPrefix("templates/admin/");
        classLoaderTemplateResolver.setSuffix(".html");
        classLoaderTemplateResolver.setTemplateMode(TemplateMode.HTML);
        classLoaderTemplateResolver.setCharacterEncoding("UTF-8");
        classLoaderTemplateResolver.setOrder(0);
        classLoaderTemplateResolver.setCheckExistence(true);

        return classLoaderTemplateResolver;
    }

    @Bean
    public ClassLoaderTemplateResolver userClassLoaderTemplateResolver() {
        ClassLoaderTemplateResolver classLoaderTemplateResolver = new ClassLoaderTemplateResolver();
        classLoaderTemplateResolver.setPrefix("templates/user/");
        classLoaderTemplateResolver.setSuffix(".html");
        classLoaderTemplateResolver.setTemplateMode(TemplateMode.HTML);
        classLoaderTemplateResolver.setCharacterEncoding("UTF-8");
        classLoaderTemplateResolver.setOrder(0);
        classLoaderTemplateResolver.setCheckExistence(true);

        return classLoaderTemplateResolver;
    }

    @Bean
    public ClassLoaderTemplateResolver calculatorClassLoaderTemplateResolver() {
        ClassLoaderTemplateResolver classLoaderTemplateResolver = new ClassLoaderTemplateResolver();
        classLoaderTemplateResolver.setPrefix("templates/calculator/");
        classLoaderTemplateResolver.setSuffix(".html");
        classLoaderTemplateResolver.setTemplateMode(TemplateMode.HTML);
        classLoaderTemplateResolver.setCharacterEncoding("UTF-8");
        classLoaderTemplateResolver.setOrder(0);
        classLoaderTemplateResolver.setCheckExistence(true);

        return classLoaderTemplateResolver;
    }

    @Bean
    public CommonsMultipartResolver multipartResolver() {
        return new CommonsMultipartResolver();
    }


    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    protected DaoAuthenticationProvider daoAuthenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        daoAuthenticationProvider.setUserDetailsService(userService());
        return daoAuthenticationProvider;
    }

    @Bean
    public Validator validator() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        return factory.getValidator();
    }


}
