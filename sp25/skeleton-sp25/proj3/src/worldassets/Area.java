package worldassets;

import world.World;

public class Area {
    private final int width, height;
    private final Position position;
    private final Position center;

    /**
    * Full constructor for Area
     * @param pos Top left position of this area
     * @param width width of this area
     * @param height height/length of this area
    * */
    public Area(Position pos, int width, int height) {
        position = pos;
        this.width = width;
        this.height = height;
        int centerX = (int) (pos.getX() + (pos.getX() + width)) / 2;
        int centerY = (int) (pos.getY() + (pos.getY() + height)) / 2;
        center = new Position(centerX, centerY);
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

    public int distantTo(Area a) {
        // Calculate manhattan distant from this to a
        return (int) this.getCenter().distantTo(a.getCenter());
    }

    public static Position centerToTopLeftPos(Position center, int w, int h, World world) {
        int newX = (int) (center.getX() - w/2);
        int newY = (int) (center.getY() + h/2);
        if (!Position.validPos(new Position(newX, 0), world)) {
            newX = 0;
        }
        if (!Position.validPos(new Position(0, newY), world)) {
            newY = world.getHeight() - 1;
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
        double thisLeftSide = this.getPosition().getX();
        double thisRightSide = this.getPosition().getX() + width - 1;
        double thisTopSide = this.getPosition().getY();
        double thisBottomSide = this.getPosition().getY() - height + 1;

        double areaLeftSide = area.getPosition().getX();
        double areaRightSide = area.getPosition().getX() + area.width - 1;
        double areaTopSide = area.getPosition().getY();
        double areaBottomSide = area.getPosition().getY() - area.height + 1;

        return !(thisLeftSide > areaRightSide || thisRightSide < areaLeftSide
                || thisTopSide < areaBottomSide || thisBottomSide > areaTopSide);
    }
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