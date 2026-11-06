package worldassets.area;

import world.World;
import world.WorldChunk;
import worldassets.Position;

import java.io.Serializable;

public class Area implements Serializable {
    // May add HALLWAY and HALLWAY_INTERSECTION in the future
    public static final Area VOID = null;
    public int getWidth() {
        return width;
    }
    public int getHeight() {
        return height;
    }
    private final int width, height;
    private final Position position;
    private final Position center;

    /**
    * Full constructor for Area
     * @param pos Top left position of this area
     * @param width width of this area
     * @param height height/length of this area
    * */
    public Area(Position pos, int width, int height, WorldChunk map) {
        position = pos;
        this.width = width;
        this.height = height;
        int centerX = (int) (pos.getX() + (pos.getX() + width)) / 2;
        int centerY = (int) (pos.getY() + (pos.getY() - height)) / 2;
        center = map.getTile(centerX, centerY).getPos();
    }

    public Position getPosition() {
        return position;
    }

    public Position getCenter() {
        return center;
    }

    public Position getTopRightCorner() {
        return new Position(this.getPosition().getX() + width - 1, this.getPosition().getY());
    }

    public Position getBottomLeftCorner() {
        return new Position(this.getPosition().getX(), this.getPosition().getY() - height + 1);
    }

    public Position getBottomRightCorner() {
        return new Position(getPosition().getX() + width - 1, getPosition().getY() - height + 1);
    }

    public int distantFromCenterTo(Area a) {
        // Calculate manhattan distant from this to a
        return this.getCenter().distantTo(a.getCenter());
    }

    /**
     * Convert center position of area to Top Left corner Position
     * @param center Position object, center of the room
     * @param w Room's width
     * @param h Room's height
     * @param world the world
     * @return Position Object at top left corner
     */
    public static Position centerToTopLeftPos(Position center, int w, int h, WorldChunk world) {
        int newX = (center.getX() - w/2);
        int newY = (center.getY() + h/2);
        if (!Position.validPos(new Position(newX, 0), world)) {
            newX = 0;
        }
        if (!Position.validPos(new Position(0, newY), world)) {
            newY = world.height - 1;
        }
        return new Position(newX, newY);
    }

    public boolean containPosition(Position pos) {
        double x = pos.getX();
        double y = pos.getY();
        return (x >= getPosition().getX() && x <= getPosition().getX() + width - 1
                && y <= getPosition().getY() && y >= getPosition().getY() - height + 1);
    }

    public boolean isOverlapped(Area area) {
        int thisLeftSide = this.getPosition().getX();
        int thisRightSide = this.getPosition().getX() + width - 1;
        int thisTopSide = this.getPosition().getY();
        double thisBottomSide = this.getPosition().getY() - height + 1;

        double areaLeftSide = area.getPosition().getX();
        double areaRightSide = area.getPosition().getX() + area.width - 1;
        double areaTopSide = area.getPosition().getY();
        double areaBottomSide = area.getPosition().getY() - area.height + 1;

        return !(thisLeftSide > areaRightSide || thisRightSide < areaLeftSide
                || thisTopSide < areaBottomSide || thisBottomSide > areaTopSide);
    }

    public boolean isAdjacentOrOverlap(Area area) {
        // Cite: Use LLM to create a slightly-different-copy of isOverlapped
        int buffer = 1;

        double thisLeftSide = this.getPosition().getX() - buffer;
        double thisRightSide = this.getPosition().getX() + width - 1 + buffer;
        double thisTopSide = this.getPosition().getY() + buffer;
        double thisBottomSide = this.getPosition().getY() - height + 1 - buffer;

        double areaLeftSide = area.getPosition().getX() - buffer;
        double areaRightSide = area.getPosition().getX() + area.width - 1 + buffer;
        double areaTopSide = area.getPosition().getY() + buffer;
        double areaBottomSide = area.getPosition().getY() - area.height + 1 - buffer;

        return !(thisLeftSide > areaRightSide || thisRightSide < areaLeftSide
                || thisTopSide < areaBottomSide || thisBottomSide > areaTopSide);
    }

//    public PositionFromArea[] findRelativePosition(Position pos, WorldMap world) {
//        PositionFromArea xRelativePos, yRelativePos; // 1 is top/left, 0 is center, -1 is bottom/right
//        if (pos.getX() > getPosition().getX() + width - 1) {
//            xRelativePos = PositionFromArea.RIGHT;
//        } else if (pos.getX() < getPosition().getX()) {
//            xRelativePos = PositionFromArea.LEFT;
//        } else {
//            xRelativePos = PositionFromArea.CENTER;
//        }
//
//        if (pos.getY() > getPosition().getY()) {
//            yRelativePos = PositionFromArea.TOP;
//        } else if (pos.getY() < getPosition().getY() - height + 1) {
//            yRelativePos = PositionFromArea.BOTTOM;
//        } else {
//            yRelativePos = PositionFromArea.CENTER;
//        }
//        return new PositionFromArea[] {xRelativePos, yRelativePos};
//    }
}

















//Trash code, may use in the future, but not for now!


//    public double getCenteredX() {
//        double x = (double) xPos, w = (double) width;
//        return (x + (x + w))/2;
//    }
//
//    public double getCenteredY() {
//        double y = (double) yPos, h = (double) height;
//        return (y + (y + h))/2;
//    }