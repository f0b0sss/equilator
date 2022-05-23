package config;

import DAO.DBUtils;
import DAO.DefaultData;
import models.Error;
import models.calculator.CalculatorData;
import models.calculator.CalculatorMainTable;
import models.calculator.GameInfo;
import models.calculator.RangeDB;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import repository.RangeRepository;
import repository.RangeRepositoryImpl;
import repository.UserRepository;
import repository.UserRepositoryImpl;
import services.Calculate;

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
    public RangeRepository repository(){
        return new RangeRepositoryImpl(dbUtils);
    }

    @Bean
    public CalculatorMainTable calculatorMainTable(){
        CalculatorMainTable calculatorMainTable = new CalculatorMainTable();
        defaultData().getCalculatorMainTables().add(calculatorMainTable);
        return new CalculatorMainTable();
    }

    @Bean
    @Scope("singleton")
    public CalculatorData calculatorData(){
        return new CalculatorData();
    }

    @Bean
    public Calculate calculate(){
        return new Calculate(defaultData());
    }

    @Bean
    public Error error(){
        return new Error();
    }

    @Bean
    public GameInfo gameInfo(){
        return new GameInfo();
    }

    @Bean
    public RangeDB rangeDB(){
        return new RangeDB();
    }

}
