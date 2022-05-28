package com.equilator.repository;

import com.equilator.models.calculator.RangeDB;

import java.util.List;

public interface RangeRepository {

    List<RangeDB> getRanges(long playerId);

    RangeDB getRangeById(int id);

    void addRange(RangeDB range);

    void deleteRangeById(int id);

}
