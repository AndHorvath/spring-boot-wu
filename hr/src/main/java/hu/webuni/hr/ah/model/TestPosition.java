package hu.webuni.hr.ah.model;

import java.util.ArrayList;
import java.util.List;

public class TestPosition {

    public static List<Position> initializeList() {
        return new ArrayList<>(List.of(
            new Position("PositionA", Qualification.NONE, 1000),
            new Position("PositionB", Qualification.ADVANCED_LEVEL, 1100),
            new Position("PositionC", Qualification.COLLAGE, 1200),
            new Position("PositionD", Qualification.UNIVERSITY, 1300),
            new Position("PositionE", Qualification.PHD, 1400)
        ));
    }
}