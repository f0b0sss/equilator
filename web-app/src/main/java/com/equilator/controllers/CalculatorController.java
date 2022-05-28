package com.equilator.controllers;

import com.equilator.DAO.DefaultData;
import com.equilator.exceptions.InvalidInputCards;
import com.equilator.models.calculator.CalculatorMainTable;
import com.equilator.models.calculator.GameInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.equilator.services.Calculate;

@Controller
public class CalculatorController {
    private final DefaultData defaultData;
    private final Calculate calculate;
    private String error;

    @Autowired
    private GameInfo gameInfo;
    @Autowired
    private CalculatorMainTable calculatorMainTable;

    @Autowired
    public CalculatorController(DefaultData defaultData, Calculate calculate) {
        this.defaultData = defaultData;
        this.calculate = calculate;
    }

    @GetMapping("/new")
    @PreAuthorize("hasAuthority('access:user')")
    public String newCalc() {
        defaultData.getCalculatorMainTables().get(0).setRangePlayer1(null);
        defaultData.getCalculatorMainTables().get(0).setRangePlayer2(null);
        defaultData.getCalculatorMainTables().get(0).setEquityPlayer1(null);
        defaultData.getCalculatorMainTables().get(0).setEquityPlayer2(null);
        defaultData.getCalculatorMainTables().get(0).setBoard(null);
        gameInfo.getCombsByCard().clear();
        gameInfo.getEquityByRangeP1().clear();
        gameInfo.getEquityByRangeP2().clear();
        gameInfo.getEquityByCardP1().clear();
        gameInfo.getEquityByCardP2().clear();

        return "redirect:/calculator";
    }

    @GetMapping("/calculator")
    @PreAuthorize("hasAuthority('access:user')")
    public String calculator(Model model) {
        model.addAttribute("calculatorMainTable", defaultData.getCalculatorMainTables().get(0));
        model.addAttribute("InvalidInputCards", error);
        model.addAttribute("statsP1", gameInfo.getEquityByCardP1());
        model.addAttribute("statsP2", gameInfo.getEquityByCardP2());
        model.addAttribute("equityByRangeP1", gameInfo.getEquityByRangeP1());
        model.addAttribute("equityByRangeP2", gameInfo.getEquityByRangeP2());

        error = null;

        return "calculator";
    }

    @GetMapping("/clear/{id}")
    @PreAuthorize("hasAuthority('access:user')")
    public String clear(@ModelAttribute("id") int id) {
        if (id == 1) {
            defaultData.getCalculatorMainTables().get(0).setRangePlayer1(null);
        }
        if (id == 2) {
            defaultData.getCalculatorMainTables().get(0).setRangePlayer2(null);
        }
        return "redirect:/calculator";
    }

    @PostMapping("/calculate")
    @PreAuthorize("hasAuthority('access:user')")
    public String calculate(@ModelAttribute("calculatorMainTable") CalculatorMainTable calculatorMainTable) {
        try {
            calculate.calculate(calculatorMainTable);
        } catch (InvalidInputCards e) {
            error = e.getMessage();
        }

        return "redirect:/calculator";
    }

    @GetMapping("/board")
    @PreAuthorize("hasAuthority('access:user')")
    public String selectBoard(Model model) {
        StringBuilder board = new StringBuilder();

        if (defaultData.getCalculatorMainTables().get(0).getBoard() != null) {
            for (int i = 0; i < defaultData.getCalculatorMainTables().get(0).getBoard().length(); i += 2) {
                if (i + 2 == defaultData.getCalculatorMainTables().get(0).getBoard().length()) {
                    board.append("#" + defaultData.getCalculatorMainTables().get(0).getBoard().substring(i, i + 2));
                } else {
                    board.append("#" + defaultData.getCalculatorMainTables().get(0).getBoard().substring(i, i + 2) + ",");
                }
            }
        }

        model.addAttribute("board", board);

        return "board";
    }

    @PostMapping("/board")
    @PreAuthorize("hasAuthority('access:user')")
    public String setBoard(@RequestParam("board") String board) {

        if (board.isEmpty()){
            calculatorMainTable.setBoard(null);
        }else {
            calculatorMainTable.setBoard(board.substring(0, board.length() - 1).replaceAll(",", ""));
        }

        return "redirect:/calculator";
    }

    @GetMapping("/equity-by-hand")
    @PreAuthorize("hasAuthority('access:user')")
    public String statistic(Model model) {
        model.addAttribute("calculatorMainTable", defaultData.getCalculatorMainTables().get(0));
        model.addAttribute("statsP1", gameInfo.getEquityByCardP1());
        model.addAttribute("statsP2", gameInfo.getEquityByCardP2());

        return "equity-by-hand";
    }

}
