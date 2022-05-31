package com.equilator.controllers;

import com.equilator.DAO.DefaultData;
import com.equilator.exceptions.InvalidInputCards;
import com.equilator.models.calculator.CalculatorMainTable;
import com.equilator.models.calculator.GameInfo;
import com.equilator.services.Calculate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/calculator")
public class CalculatorController {
    private static Logger logger = LogManager.getLogger(AccountController.class);
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
        logger.debug("Start new calculate operation");
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

        return "redirect:/calculator/calculator";
    }

    @GetMapping("/calculator")
    @PreAuthorize("hasAuthority('access:user')")
    public String calculator(Model model) {
        logger.debug("Open calculator page");
        model.addAttribute("calculatorMainTable", defaultData.getCalculatorMainTables().get(0));
        model.addAttribute("InvalidInputCards", error);
        model.addAttribute("statsP1", gameInfo.getEquityByCardP1());
        model.addAttribute("statsP2", gameInfo.getEquityByCardP2());
        model.addAttribute("equityByRangeP1", gameInfo.getEquityByRangeP1().entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).collect(Collectors.toList()));
        model.addAttribute("equityByRangeP2", gameInfo.getEquityByRangeP2().entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).collect(Collectors.toList()));

        error = null;

        return "calculator/calculator";
    }

    @GetMapping("/clear/{id}")
    @PreAuthorize("hasAuthority('access:user')")
    public String clear(@ModelAttribute("id") int id) {
        logger.debug("Clear player" + id + " range");
        if (id == 1) {
            defaultData.getCalculatorMainTables().get(0).setRangePlayer1(null);
        }
        if (id == 2) {
            defaultData.getCalculatorMainTables().get(0).setRangePlayer2(null);
        }
        return "redirect:/calculator/calculator";
    }

    @PostMapping("/calculate")
    @PreAuthorize("hasAuthority('access:user')")
    public String calculate(@ModelAttribute("calculatorMainTable") CalculatorMainTable calculatorMainTable) {
        logger.debug("Start calculate process");
        try {
            calculate.calculate(calculatorMainTable);
        } catch (InvalidInputCards e) {
            logger.warn("Invalid input calculator data");
            error = e.getMessage();
        }

        return "redirect:/calculator/calculator";
    }

    @GetMapping("/board")
    @PreAuthorize("hasAuthority('access:user')")
    public String selectBoard(Model model) {
        logger.debug("Open select board window");
        StringBuilder board = new StringBuilder();

        if (defaultData.getCalculatorMainTables().get(0).getBoard() != null) {
            logger.info("Add present cards to table");
            for (int i = 0; i < defaultData.getCalculatorMainTables().get(0).getBoard().length(); i += 2) {
                if (i + 2 == defaultData.getCalculatorMainTables().get(0).getBoard().length()) {
                    board.append("#" + defaultData.getCalculatorMainTables().get(0).getBoard().substring(i, i + 2));
                } else {
                    board.append("#" + defaultData.getCalculatorMainTables().get(0).getBoard().substring(i, i + 2) + ",");
                }
            }
        }

        model.addAttribute("board", board);

        return "calculator/board";
    }

    @PostMapping("/board")
    @PreAuthorize("hasAuthority('access:user')")
    public String setBoard(@RequestParam("board") String board) {
        logger.info("Add selected cards to main window");

        if (board.isEmpty()){
            defaultData.getCalculatorMainTables().get(0).setBoard(null);
        }else {
            defaultData.getCalculatorMainTables().get(0).setBoard(board.substring(0, board.length() - 1).replaceAll(",", ""));
        }

        return "redirect:/calculator/calculator";
    }

    @GetMapping("/equity-by-hand")
    @PreAuthorize("hasAuthority('access:user')")
    public String statistic(Model model) {
        logger.info("Show equity on next card");
        model.addAttribute("calculatorMainTable", defaultData.getCalculatorMainTables().get(0));
        model.addAttribute("statsP1", gameInfo.getEquityByCardP1());
        model.addAttribute("statsP2", gameInfo.getEquityByCardP2());

        return "calculator/equity-by-hand";
    }

}
