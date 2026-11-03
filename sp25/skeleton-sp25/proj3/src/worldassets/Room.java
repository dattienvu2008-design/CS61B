package worldassets;

import world.WorldMap;

import java.util.List;

public class Room extends Area {
    public Room(Position pos, int width, int height, WorldMap map) {
        super(pos, width, height, map);
        int xCenter = (int) this.getCenter().getX(), yCenter = (int) this.getCenter().getY();
        int xPos = (int) getPosition().getX(), yPos = (int) getPosition().getY();
    }
}
