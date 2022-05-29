package com.equilator.services;

import com.equilator.exceptions.RangeNotFoundException;
import com.equilator.models.calculator.RangeDB;
import com.equilator.repository.RangeRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

public class RangeService {

    @Autowired
    private RangeRepository rangeRepository;

    public List<RangeDB> getRanges(long playerId) {
        List<RangeDB> ranges = rangeRepository.getRanges(playerId);

        return ranges;
    }

    public RangeDB getRangeById(int id) {
        Optional<RangeDB> range = Optional.ofNullable(rangeRepository.getRangeById(id));
        return range.orElseThrow(() -> new RangeNotFoundException("invalid range id"));
    }

    public void addRange(RangeDB range) {
        rangeRepository.addRange(range);
    }

    public void deleteRangeById(int id) {
        rangeRepository.deleteRangeById(id);
    }
}
