package services;

import exceptions.RangeNotFoundException;
import models.calculator.RangeDB;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repository.RangeRepository;

import java.util.List;
import java.util.Optional;

@Service
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
