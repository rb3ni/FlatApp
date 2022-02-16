package hu.domain.space;

public class SpaceType {
    private String spaceType;
    private double numberOfRooms;
    private int area; // in square meters
    private boolean hasBalcony;
    private int cost; //in HUF
    private String description;
    private int blockId;

    public SpaceType(String space_type, double number_of_rooms, int area, boolean hasBalcony, int cost, String description, int blockId) {
        this.spaceType = space_type;
        this.numberOfRooms = number_of_rooms;
        this.area = area;
        this.hasBalcony = hasBalcony;
        this.cost = cost;
        this.description = description;
        this.blockId = blockId;
    }

    public String getSpaceType() {
        return spaceType;
    }

    public double getNumberOfRooms() {
        return numberOfRooms;
    }

    public int getArea() {
        return area;
    }

    public boolean hasHasBalcony() {
        return hasBalcony;
    }

    public int getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }

    public int getBlockId() {
        return blockId;
    }
}
