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
import com.equilator.services.FileService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfig {

    @Bean
    public DBUtils dbUtils(){
        return new DBUtils();
    };

    @Bean
    public DefaultData defaultData(){
        return new DefaultData();
    }

    @Bean
    public UserRepository userRepository(){
        return new UserRepositoryImpl(dbUtils());
    }

    @Bean
    public RangeRepository repository(){
        return new RangeRepositoryImpl(dbUtils());
    }

    @Bean
    public CalculatorMainTable calculatorMainTable(){
        return defaultData().getCalculatorMainTables().get(0);
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
