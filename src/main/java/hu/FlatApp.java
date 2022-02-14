package hu;

import hu.domain.space.FlatType;
import hu.domain.account.Habitant;
import hu.domain.space.Space;

import java.util.List;

public class FlatApp {
    public static void main(String[] args) {
        Habitant habitant;
        Space space;

        habitant = new Habitant("Bruce Wayne",
                5551234,
                "bruce_wayne@gothamail.com",
                "Közös képviselő",
                30000,
                32,
                "CEO a Wayne vállalatnál");

        space = new Space(1,
                6,
                List.of(habitant),
                FlatType.FLAT_A);

        System.out.println(space);

    }
}
