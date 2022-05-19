package models.calculator.n;

import java.util.List;
import java.util.Objects;

public class Range {
    List<Group> rangeGroups;
    private double rangeEquity;
    private double turnEquity;
    private double riverEquity;

    public List<Group> getRangeGroups() {
        return rangeGroups;
    }

    public void setRangeGroups(List<Group> rangeGroups) {
        this.rangeGroups = rangeGroups;
    }

    public double getRangeEquity() {
        return rangeEquity;
    }

    public void setRangeEquity(double rangeEquity) {
        this.rangeEquity = rangeEquity;
    }

    public double getTurnEquity() {
        return turnEquity;
    }

    public void setTurnEquity(double turnEquity) {
        this.turnEquity = turnEquity;
    }

    public double getRiverEquity() {
        return riverEquity;
    }

    public void setRiverEquity(double riverEquity) {
        this.riverEquity = riverEquity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Range range = (Range) o;
        return Double.compare(range.rangeEquity, rangeEquity) == 0 && Double.compare(range.turnEquity, turnEquity) == 0 && Double.compare(range.riverEquity, riverEquity) == 0 && Objects.equals(rangeGroups, range.rangeGroups);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rangeGroups, rangeEquity, turnEquity, riverEquity);
    }
}
