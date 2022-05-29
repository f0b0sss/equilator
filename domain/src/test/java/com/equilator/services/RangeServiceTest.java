package com.equilator.services;

import com.equilator.exceptions.RangeNotFoundException;
import com.equilator.models.calculator.RangeDB;
import com.equilator.testConfig.DataBaseTestConfig;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = {DataBaseTestConfig.class},
        loader = AnnotationConfigContextLoader.class)
@Sql(scripts = {"classpath:schema_ranges.sql", "classpath:data_ranges.sql"})
class RangeServiceTest {

    @Autowired
    RangeService rangeService;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @AfterEach
    void drop(){
        jdbcTemplate.execute("DROP TABLE ranges cascade ");
    }

    @Test
    void getRanges_default() {
        assertEquals(5, rangeService.getRanges(0).size());
    }

    @Test
    void getRangeById_throwRangeNotFoundException_whenIdDoesNotExist() {
        assertThrows(RangeNotFoundException.class, new Executable() {
            @Override
            public void execute() throws Throwable {
                rangeService.getRangeById(6);
            }
        });
    }

    @Test
    void getRangeById() {
        assertEquals("ep", rangeService.getRangeById(1).getName());
        assertEquals("mp", rangeService.getRangeById(2).getName());
    }

    @Test
    void addRange() {
        RangeDB newRange = new RangeDB();
        newRange.setPlayerId(2l);
        newRange.setName("newRange");
        newRange.setRange("AA,KK,QQ,JJ,TT,99,88,77,66,55,AKo,AQo,KQo,AJo,AKs,AQs," +
                "AJs,ATs,A5s,A4s,A3s,A2s,KQs,KJs,KTs,QJs,QTs,JTs");

        rangeService.addRange(newRange);

        assertEquals("newRange", rangeService.getRangeById(6).getName());
    }

    @Test
    void addRange_IdIsSerial() {
        RangeDB newRange = new RangeDB();
        newRange.setPlayerId(2l);
        newRange.setName("newRange");
        newRange.setRange("AA,KK,QQ,JJ,TT,99,88,77,66,55,AKo,AQo,KQo,AJo,AKs,AQs," +
                "AJs,ATs,A5s,A4s,A3s,A2s,KQs,KJs,KTs,QJs,QTs,JTs");

        rangeService.addRange(newRange);

        assertEquals(6, rangeService.getRanges(2).get(0).getId());
    }

    @Test
    void deleteRangeById() {
        RangeDB newRange = new RangeDB();
        newRange.setPlayerId(2l);
        newRange.setName("newRange");
        newRange.setRange("AA,KK,QQ,JJ,TT,99,88,77,66,55,AKo,AQo,KQo,AJo,AKs,AQs," +
                "AJs,ATs,A5s,A4s,A3s,A2s,KQs,KJs,KTs,QJs,QTs,JTs");

        rangeService.deleteRangeById(6);

        assertTrue(rangeService.getRanges(2).isEmpty());
    }
}