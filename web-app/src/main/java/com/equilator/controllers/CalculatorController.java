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

    @GetMapping("/clear/{id}")
    @PreAuthorize("hasAuthority('access:user')")
    public String clear(@ModelAttribute("id") int id) {
        if (id == 1){
            defaultData.getCalculatorMainTables().get(0).setRangePlayer1(null);
        }
        if (id == 2){
            defaultData.getCalculatorMainTables().get(0).setRangePlayer2(null);
        }
        return "redirect:/calculator";
    }

    @PostMapping("/calculate")
    @PreAuthorize("hasAuthority('access:user')")
    public String calculate(@ModelAttribute("gameInfo") CalculatorMainTable calculatorMainTable) {
        try {
            calculate.calculate(calculatorMainTable);
        } catch (InvalidInputCards e) {
            error = e.getMessage();
            defaultData.getCalculatorMainTables().add(0, calculatorMainTable);
        }

        return "redirect:/calculator";
    }

    @GetMapping("/board")
    @PreAuthorize("hasAuthority('access:user')")
    public String selectBoard(Model model) {
        StringBuilder board = new StringBuilder();
        String[] array = new String[]{};

        array = defaultData.getCalculatorMainTables().get(0).getBoard().split(",");

        if (array.length != 0) {
            for (int i = 0; i < array.length; i++) {
                if (i == array.length - 1) {
                    board.append("#" + array[i]);
                } else {
                    board.append("#" + array[i] + ",");
                }
            }
        }

        model.addAttribute("board", board);

        return "board";
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
