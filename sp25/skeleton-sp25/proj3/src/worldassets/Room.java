package worldassets;

public class Room extends Area {
    public final Position doorNorth, doorSouth, doorEast, doorWest;
    public Room(Position pos, int width, int height) {
        super(pos, width, height);
        int xCenter = (int) this.getCenter().getX(), yCenter = (int) this.getCenter().getY();
        int xPos = (int) getPosition().getX(), yPos = (int) getPosition().getY();
        doorNorth = new Position(xCenter, yPos);
        doorSouth = new Position(xCenter, yPos - height);
        doorEast = new Position(xPos + width, yCenter);
        doorWest = new Position(xPos, yCenter);
    }
}
