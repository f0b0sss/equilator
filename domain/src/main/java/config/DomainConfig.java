package config;

import DAO.DBUtils;
import DAO.DefaultData;
import models.Error;
import models.calculator.CalculatorMainTable;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import repository.UserRepository;
import repository.UserRepositoryImpl;
import services.Calculate;
import services.Calculate2;
import services.Calculate3;

@Configuration
@ComponentScan("DAO")
public class DomainConfig {

    private final DBUtils dbUtils;

    public DomainConfig(@Qualifier("dbUtils") DBUtils dbUtils) {
        this.dbUtils = dbUtils;
    }

    @Bean
    public DefaultData defaultData(){
        return new DefaultData();
    }

    @Bean
    public UserRepository userRepository(){
        return new UserRepositoryImpl(dbUtils);
    }

    @Bean
    public CalculatorMainTable calculatorMainTable(){
        CalculatorMainTable calculatorMainTable = new CalculatorMainTable();
        defaultData().getCalculatorMainTables().add(calculatorMainTable);
        return new CalculatorMainTable();
    }

    @Bean
    public Calculate calculate(){
        return new Calculate(defaultData());
    }

    @Bean
    public Calculate2 calculate2(){
        return new Calculate2(defaultData());
    }

    @Bean
    public Calculate3 calculate3(){
        return new Calculate3(defaultData());
    }

    @Bean
    public Error error(){
        return new Error();
    }

}
