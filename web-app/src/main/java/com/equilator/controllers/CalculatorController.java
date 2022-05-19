package com.equilator.controllers;

import DAO.DefaultData;
import exceptions.InvalidInputCards;
import models.calculator.CalculatorMainTable;
import models.calculator.GameInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import services.Calculate;

@Controller
public class CalculatorController {
    private final DefaultData defaultData;
    private final Calculate calculate;
    private String error;

    @Autowired
    private GameInfo gameInfo;

    @Autowired
    public CalculatorController(DefaultData defaultData, Calculate calculate) {
        this.defaultData = defaultData;
        this.calculate = calculate;
    }

    @GetMapping("/calculator")
    @PreAuthorize("hasAuthority('access:user')")
    public String calculator(Model model) {
        model.addAttribute("calculatorMainTable", defaultData.getCalculatorMainTables().get(0));
        model.addAttribute("InvalidInputCards", error);
        model.addAttribute("statsP1", gameInfo.getEquityByCardP1());
        model.addAttribute("statsP2", gameInfo.getEquityByCardP2());

        error = null;

        return "calculator";
    }

    @PostMapping("/calculate")
    @PreAuthorize("hasAuthority('access:user')")
    public String calculate(@ModelAttribute("gameInfo") CalculatorMainTable calculatorMainTable) {
        try{
            calculate.calculate(calculatorMainTable);
        }catch (InvalidInputCards e){
            error = e.getMessage();
            defaultData.getCalculatorMainTables().add(0, calculatorMainTable);
        }

        return "redirect:/calculator";
    }

    @GetMapping("/statistic")
    @PreAuthorize("hasAuthority('access:user')")
    public String statistic(Model model) {
        model.addAttribute("calculatorMainTable", defaultData.getCalculatorMainTables().get(0));
        model.addAttribute("statsP1", gameInfo.getEquityByCardP1());
        model.addAttribute("statsP2", gameInfo.getEquityByCardP2());

        return "statistic";
    }





}
