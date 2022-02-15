package hu.domain.space;


import hu.domain.Block;
import hu.domain.account.Habitant;
import hu.domain.space.FlatType;

import java.util.List;

public class Space {

    private int id;
    private int floor;
    private int door;
    private List<Integer> habitants;
    private FlatType flatType;
    private Integer blockId;

    public Space(int floor, int door, List<Integer> habitants, FlatType flatType, Integer block) {
        this.floor = floor;
        this.door = door;
        this.habitants = habitants;
        this.flatType = flatType;
        this.blockId = block;
    }

    public Space(int id, int floor, int door, List<Integer> habitants, FlatType flatType, Integer block) {
        this.id = id;
        this.floor = floor;
        this.door = door;
        this.habitants = habitants;
        this.flatType = flatType;
        this.blockId = block;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }

    public int getDoor() {
        return door;
    }

    public void setDoor(int door) {
        this.door = door;
    }

    public List<Integer> getHabitants() {
        return habitants;
    }

    public void setHabitants(List<Integer> habitants) {
        this.habitants = habitants;
    }

    public FlatType getFlatType() {
        return flatType;
    }

    public void setFlatType(FlatType flatType) {
        this.flatType = flatType;
    }

    public Integer getBlockId() {
        return blockId;
    }

    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
    }

    @Override
    public String toString() {
        return "Space{" +
                "floor=" + floor +
                ", door=" + door +
                ", habitants=" + habitants +
                '}';
    }
}
