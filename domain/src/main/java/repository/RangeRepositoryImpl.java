package repository;

import DAO.DBUtils;
import models.calculator.RangeDB;

import java.sql.SQLException;
import java.util.List;

public class RangeRepositoryImpl implements RangeRepository{

    private final DBUtils dbUtils;

    public RangeRepositoryImpl(DBUtils dbUtils) {
        this.dbUtils = dbUtils;
    }

    @Override
    public List<RangeDB> getRanges(long playerId) {
        try {
            return dbUtils.getRanges(playerId);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public RangeDB getRangeById(int id) {
        try {
            return dbUtils.getRangeById(id);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void addRange(RangeDB range) {
        try {
            dbUtils.addRange(range);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteRangeById(int id) {
        try {
            dbUtils.deleteRangeById(id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
