package com.equilator.config;

import DAO.DefaultData;
import models.calculator.CalculatorMainTable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import services.Calculate;

@Configuration
public class DomainConfig {

    @Bean
    public DefaultData defaultData(){
        return new DefaultData();
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

}
