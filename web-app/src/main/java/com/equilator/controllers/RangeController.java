package com.equilator.controllers;

import DAO.DefaultData;
import models.calculator.RangeDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import services.RangeService;
import services.UserService;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RangeController {
    private String error;

    @Autowired
    private DefaultData defaultData;
    @Autowired
    private RangeService rangeService;
    @Autowired
    private UserService userService;

    @GetMapping("/range-bar/{id}")
    @PreAuthorize("hasAuthority('access:user')")
    public String range(@ModelAttribute("id") int id,Model model) {

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

        return "range-bar";
    }

    @PostMapping("/range-bar/{id}")
    @PreAuthorize("hasAuthority('access:user')")
    public String setRangeToPLayer(@PathVariable("id") int id,
                           @RequestParam("playerRange") String playerRange) {

        setRangeToPlayer(playerRange, id);

        return "redirect:/calculator";
    }

    @GetMapping("/clear-range-bar/{id}")
    @PreAuthorize("hasAuthority('access:user')")
    public String clear(@ModelAttribute("id") int id) {

        setRangeToPlayer(null, id);

        return "redirect:/range-bar/" + id;
    }

    @GetMapping("/range/rangeId/{rangeId}/playerId/{playerId}")
    @PreAuthorize("hasAuthority('access:user')")
    public String openRange(@PathVariable("rangeId") int rangeId,
                            @PathVariable("playerId") int playerId, Model model) {

        setRangeToPlayer(rangeService.getRangeById(rangeId).getRange(), playerId);

        return "redirect:/range-bar/" + playerId;
    }

    @PostMapping("/save-range/{id}")
    @PreAuthorize("hasAuthority('access:user')")
    public String saveRange(@PathVariable("id") int id,
                            @ModelAttribute("rangeDB") @Valid RangeDB rangeDB,
                            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "redirect:/range-bar/" + id;
        }

        long accountId = userService.getUserByEmail(SecurityContextHolder
                .getContext()
                .getAuthentication().getName()).getId();

        rangeDB.setPlayerId(accountId);

        rangeService.addRange(rangeDB);

        setRangeToPlayer(rangeDB.getRange(), id);

        return "redirect:/range-bar/" + id;
    }

    @DeleteMapping("/delete-range/rangeId/{rangeId}/playerId/{playerId}")
    @PreAuthorize("hasAuthority('access:user')")
    public String deleteRange(@PathVariable("rangeId") int rangeId,
                              @PathVariable("playerId") int playerId) {

        rangeService.deleteRangeById(rangeId);

        setRangeToPlayer(null, playerId);

        return "redirect:/range-bar/" + playerId;
    }

    private void setRangeToPlayer(String playerRange, int id) {
        if (id == 1) {
            defaultData.getCalculatorMainTables().get(0).setRangePlayer1(playerRange);
        }
        if (id == 2) {
            defaultData.getCalculatorMainTables().get(0).setRangePlayer2(playerRange);
        }
    }

}
