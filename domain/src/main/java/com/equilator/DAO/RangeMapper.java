package com.equilator.DAO;

import com.equilator.models.calculator.RangeDB;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RangeMapper implements RowMapper<RangeDB> {
    @Override
    public RangeDB mapRow(ResultSet rs, int rowNum) throws SQLException {
        RangeDB range = new RangeDB();

        range.setId(rs.getLong("id"));
        range.setPlayerId(rs.getLong("player_id"));
        range.setName(rs.getString("name"));
        range.setRange(rs.getString("range"));

        return range;
    }
}
