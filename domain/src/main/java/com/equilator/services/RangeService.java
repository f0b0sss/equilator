package com.equilator.services;

import com.equilator.exceptions.RangeNotFoundException;
import com.equilator.models.calculator.RangeDB;
import com.equilator.repository.RangeRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class RangeService {
    private static Logger logger = LogManager.getLogger(RangeService.class);

    @Autowired
    private RangeRepository rangeRepository;

    public List<RangeDB> getRanges(long playerId) {
        logger.debug("Get saved ranges list");
        List<RangeDB> ranges = rangeRepository.getRanges(playerId);

        return ranges;
    }

    public RangeDB getRangeById(int id) {
        logger.debug("Open selected saved range");
        Optional<RangeDB> range = Optional.ofNullable(rangeRepository.getRangeById(id));
        return range.orElseThrow(() -> new RangeNotFoundException("invalid range id"));
    }

    public void addRange(RangeDB range) {
        logger.debug("Add new range to list");
        rangeRepository.addRange(range);
    }

    public void deleteRangeById(int id) {
        logger.debug("Delete range from list");
        rangeRepository.deleteRangeById(id);
    }
}
