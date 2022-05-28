package com.equilator.models.calculator;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RangeDB {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long playerId;

    @NotNull
    @Size(min = 1, message = "name min 2 symbols")
    private String name;
    @NotNull(message = "select range to save")
    private String range;

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRange() {
        return range;
    }

    public void setRange(String range) {
        this.range = range;
    }

    @Override
    public String toString() {
        return "RangeDB{" +
                "id=" + id +
                ", playerId=" + playerId +
                ", name='" + name + '\'' +
                ", range='" + range + '\'' +
                '}';
    }
}
