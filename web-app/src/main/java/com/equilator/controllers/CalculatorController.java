package com.equilator.controllers;

import DAO.DefaultData;
import Services.Calculate;
import models.CalculatorMainTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CalculatorController {
    private final DefaultData defaultData;
    private final Calculate calculate;

    @Autowired
    public CalculatorController(DefaultData defaultData, Calculate calculate) {
        this.defaultData = defaultData;
        this.calculate = calculate;
    }

    @GetMapping("/calculator")
    public String calculator(Model model) {
        model.addAttribute("calculatorMainTable", defaultData.getCalculatorMainTables().get(0));
        return "calculator";

    }

    @PostMapping("/calculate")
    public String calculate(@ModelAttribute("gameInfo") CalculatorMainTable calculatorMainTable) {
        calculate.calculate(calculatorMainTable);
        return "redirect:/calculator";
    }


}
