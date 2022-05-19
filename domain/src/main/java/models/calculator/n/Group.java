package models.calculator.n;

import models.calculator.enums.Type;

import java.util.List;
import java.util.Objects;

public class Group {
    private String groupName;
    private List<Hand> groupHands;
    private double groupEquity;
    private double turnEquity;
    private Type type;

    public List<Hand> getGroupHands() {
        return groupHands;
    }

    public void setGroupHands(List<Hand> groupHands) {
        this.groupHands = groupHands;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public double getGroupEquity() {
        return groupEquity;
    }

    public void setGroupEquity(double groupEquity) {
        this.groupEquity = groupEquity;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public double getTurnEquity() {
        return turnEquity;
    }

    public void setTurnEquity(double turnEquity) {
        this.turnEquity = turnEquity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Group group = (Group) o;
        return Double.compare(group.groupEquity, groupEquity) == 0 && Objects.equals(groupName, group.groupName) && Objects.equals(groupHands, group.groupHands) && type == group.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(groupName, groupHands, groupEquity, type);
    }
}
