package hu.domain.space;

public enum FlatType {

    FLAT_A (2,
            53,
            true,
            25000,
            "Két szobás, erkéllyel rendelkező lakás, belső elhelyezkedésű."),

    FLAT_B   (2,
            51,
            false,
            25000,
            "Két szobás lakás, belső elhelyezkedésű."),

    FLAT_C   (1.5,
            45,
            false,
            25000,
            "Két szobás lakás, belső elhelyezkedésű."),

    PARKING_SLOT (1,
            6,
            false,
            15000,
            "Parkolóhely"),

    RENTAL_SPACE (4,
            106,
            false,
            120000,
            "Kiadó üzlethelyiség, korábban pékség, bútorozottt");

    private final double number_of_rooms;
    private final int area; // in square meters
    private final boolean hasBalcony;
    private final int cost; //in HUF
    private final String description;

    FlatType(double number_of_rooms, int area, boolean hasBalcony, int cost, String description) {
        this.number_of_rooms = number_of_rooms;
        this.area = area;
        this.hasBalcony = hasBalcony;
        this.cost = cost;
        this.description = description;
    }

    public double getNumber_of_rooms() {
        return number_of_rooms;
    }

    public int getArea() {
        return area;
    }

    public boolean isHasBalcony() {
        return hasBalcony;
    }

    public int getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }
}
