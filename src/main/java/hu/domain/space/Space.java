package hu.domain.space;


import hu.domain.account.Habitant;
import hu.domain.space.FlatType;

import java.util.List;

public class Space {

    private int id;
    private int floor;
    private int door;
    private List<Habitant> habitants;
    private FlatType flatType;

    public Space(int floor, int door, List<Habitant> habitants, FlatType flatType) {
        this.floor = floor;
        this.door = door;
        this.habitants = habitants;
        this.flatType = flatType;
    }

    public Space(int id, int floor, int door, List<Habitant> habitants, FlatType flatType) {
        this.id = id;
        this.floor = floor;
        this.door = door;
        this.habitants = habitants;
        this.flatType = flatType;
    }

    @Override
    public String toString() {
        return "Space{" +
                "floor=" + floor +
                ", door=" + door +
                ", habitants=" + habitants.get(0).getName() +
                '}';
    }
}
