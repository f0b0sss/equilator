package com.equilator.controllers;

import DAO.DefaultData;
import models.calculator.CalculatorMainTable;
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

    @Autowired
    public CalculatorController(DefaultData defaultData, Calculate calculate) {
        this.defaultData = defaultData;
        this.calculate = calculate;
    }

    @GetMapping("/calculator")
    @PreAuthorize("hasAuthority('access:user')")
    public String calculator(Model model) {
        model.addAttribute("calculatorMainTable", defaultData.getCalculatorMainTables().get(0));
        return "calculator";

    }

    @PostMapping("/calculate")
    @PreAuthorize("hasAuthority('access:user')")
    public String calculate(@ModelAttribute("gameInfo") CalculatorMainTable calculatorMainTable) {
        calculate.calculate(calculatorMainTable);
        return "redirect:/calculator";
    }


}
