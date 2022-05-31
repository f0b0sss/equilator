package com.equilator.controllers;

import com.equilator.DAO.DefaultData;
import com.equilator.models.calculator.GameInfo;
import com.equilator.models.calculator.RangeDB;
import com.equilator.services.CombinationGenerator;
import com.equilator.services.RangeService;
import com.equilator.services.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
@RequestMapping("/calculator")
public class RangeController {
    private static Logger logger = LogManager.getLogger(RangeController.class);

    @Autowired
    private DefaultData defaultData;
    @Autowired
    private RangeService rangeService;
    @Autowired
    private UserService userService;
    @Autowired
    private CombinationGenerator combinationGenerator;
    @Autowired
    private GameInfo gameInfo;

    @GetMapping("/range-bar/{id}")
    @PreAuthorize("hasAuthority('access:user')")
    public String range(@ModelAttribute("id") int id,Model model) {
        logger.debug("Open range bar page");

        StringBuilder playerRange = new StringBuilder();
        String[] array = new String[]{};

        if (id == 1) {
            if (defaultData.getCalculatorMainTables().get(0).getRangePlayer1() != null) {
                array = defaultData.getCalculatorMainTables().get(0).getRangePlayer1().split(",");
            }
        }
        if (id == 2) {
            if (defaultData.getCalculatorMainTables().get(0).getRangePlayer2() != null) {
                array = defaultData.getCalculatorMainTables().get(0).getRangePlayer2().split(",");
            }
        }

        logger.debug("Show range selected if it was");
        if (array.length != 0) {
            for (int i = 0; i < array.length; i++) {
                if (i == array.length - 1) {
                    playerRange.append("#" + array[i]);
                } else {
                    playerRange.append("#" + array[i] + ",");
                }
            }
        }

        long accountId = userService.getUserByEmail(SecurityContextHolder
                .getContext()
                .getAuthentication().getName()).getId();

        List<RangeDB> rangeDBList = rangeService.getRanges(0);
        rangeDBList.addAll(rangeService.getRanges(accountId));

        model.addAttribute("playerRange", playerRange);
        model.addAttribute("rangesDB", rangeDBList);
        model.addAttribute("playerId", id);
        model.addAttribute("newRange", new RangeDB());
        model.addAttribute("combs", gameInfo.getCombsByCard());

        return "calculator/range-bar";
    }

    @PostMapping("/range-bar/{id}")
    @PreAuthorize("hasAuthority('access:user')")
    public String setRangeToPLayer(@PathVariable("id") int id,
                                   @RequestParam("playerRange") String playerRange) {
        if (playerRange.length() != 0){
            logger.info("Set selected range to player" + id);
            setRangeToPlayer(playerRange, id);
        }

        return "redirect:/calculator/calculator";
    }

    @GetMapping("/clear-range-bar/{id}")
    @PreAuthorize("hasAuthority('access:user')")
    public String clear(@ModelAttribute("id") int id) {
        logger.info("Clear range player" + id);
        setRangeToPlayer(null, id);

        return "redirect:/calculator/range-bar/" + id;
    }

    @GetMapping("/range/rangeId/{rangeId}/playerId/{playerId}")
    @PreAuthorize("hasAuthority('access:user')")
    public String openRange(@PathVariable("rangeId") int rangeId,
                            @PathVariable("playerId") int playerId, Model model) {

        logger.debug("Open saved range from list");
        setRangeToPlayer(rangeService.getRangeById(rangeId).getRange(), playerId);

        return "redirect:/calculator/range-bar/" + playerId;
    }

    @PostMapping("/save-range/{id}")
    @PreAuthorize("hasAuthority('access:user')")
    public String saveRange(@PathVariable("id") int id,
                            @ModelAttribute("rangeDB") @Valid RangeDB rangeDB,
                            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            logger.warn("Error when save new range. Not entered name or not selected range");
            return "redirect:/calculator/range-bar/" + id;
        }

        long accountId = userService.getUserByEmail(SecurityContextHolder
                .getContext()
                .getAuthentication().getName()).getId();

        rangeDB.setPlayerId(accountId);

        rangeService.addRange(rangeDB);

        setRangeToPlayer(rangeDB.getRange(), id);

        logger.info("Successful save personal range for authorized user");

        return "redirect:/calculator/range-bar/" + id;
    }

    @DeleteMapping("/delete-range/rangeId/{rangeId}/playerId/{playerId}")
    @PreAuthorize("hasAuthority('access:user')")
    public String deleteRange(@PathVariable("rangeId") int rangeId,
                              @PathVariable("playerId") int playerId) {
        logger.info("Deleted personal saved range");

        rangeService.deleteRangeById(rangeId);

        setRangeToPlayer(null, playerId);

        return "redirect:/calculator/range-bar/" + playerId;
    }

    private void setRangeToPlayer(String playerRange, int id) {
        logger.debug("Set range to select player");
        if (id == 1) {
            defaultData.getCalculatorMainTables().get(0).setRangePlayer1(playerRange);
        }
        if (id == 2) {
            defaultData.getCalculatorMainTables().get(0).setRangePlayer2(playerRange);
        }
    }

    @GetMapping("/combinations/{id}")
    @PreAuthorize("hasAuthority('access:user')")
    public String combinations(@RequestParam("rangeForCombinator") String playerRange,
                               @PathVariable("id") int playerId) {

        logger.info("Show combinations by card");

        setRangeToPlayer(playerRange, playerId);

        combinationGenerator.generate(playerRange);

        return "redirect:/calculator/range-bar/" + playerId;
    }

}
