package hu.domain.space;


import java.util.List;

public class SpaceMod {

    private int id;
    private int floor;
    private int door;
    private List<Integer> habitants;
    private String spaceType;
    private Integer blockId;

    public SpaceMod(int floor, int door, List<Integer> habitants, String spaceType, Integer block) {
        this.floor = floor;
        this.door = door;
        this.habitants = habitants;
        this.spaceType = spaceType;
        this.blockId = block;
    }

    public SpaceMod(int id, int floor, int door, List<Integer> habitants, String spaceType, Integer block) {
        this.id = id;
        this.floor = floor;
        this.door = door;
        this.habitants = habitants;
        this.spaceType = spaceType;
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

    public String getSpaceType() {
        return spaceType;
    }

    public void setSpaceType(String spaceType) {
        this.spaceType = spaceType;
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
