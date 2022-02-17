package hu.domain.space;


import java.util.List;

public class Space {

    private int id;
    private int floor;
    private int door;
    private List<Integer> habitants;
    private String spaceType;
    private Integer blockId;
    private int balance;

    public Space(int floor, int door, List<Integer> habitants, String spaceType, Integer blockId, int balance) {
        this.floor = floor;
        this.door = door;
        this.habitants = habitants;
        this.spaceType = spaceType;
        this.blockId = blockId;
        this.balance = balance;
    }

    public Space(int id, int floor, int door, List<Integer> habitants, String spaceType, Integer blockId, int balance) {
        this.id = id;
        this.floor = floor;
        this.door = door;
        this.habitants = habitants;
        this.spaceType = spaceType;
        this.blockId = blockId;
        this.balance = balance;
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

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
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
