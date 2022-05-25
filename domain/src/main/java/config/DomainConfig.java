package config;

import DAO.DBUtils;
import DAO.DefaultData;
import models.Error;
import models.UploadForm;
import models.calculator.CalculatorMainTable;
import models.calculator.GameInfo;
import models.calculator.RangeDB;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import repository.RangeRepository;
import repository.RangeRepositoryImpl;
import repository.UserRepository;
import repository.UserRepositoryImpl;
import services.Calculate;
import services.CombinationGenerator;
import services.FileService;

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
    public Calculate calculate(){
        return new Calculate(defaultData());
    }

    @Bean
    public CombinationGenerator combinationGenerator(){
        return new CombinationGenerator();
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

    @Bean
    public FileService saveResult(){
        return new FileService();
    }

    @Bean
    public UploadForm uploadForm(){
        return new UploadForm();
    }

}
