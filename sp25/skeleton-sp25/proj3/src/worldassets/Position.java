package worldassets;

import world.WorldChunk;
import worldassets.area.Area;

import java.io.Serializable;

public class Position implements Serializable {
    private final int xPos, yPos;

    public Position(int xPos, int yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public int getX() {
        return xPos;
    }

    public int getY() {
        return yPos;
    }

    public int distantTo(Position p) {
        // Calculate manhattan distant from this to p
        return Math.abs((getX() - p.getX())) + Math.abs((getY() - p.getY()));
    }

    public int totalDistantTo(Area[] areas) {
        int totalDist = 0;
        for (Area area : areas) {
            totalDist += (int) this.distantTo(area.getCenter());
        }
        return totalDist;
    }

    public int calculatePositionScore(Area[] areas) {
        int totalDist = 0, currentScore = 0;
        for (Area area : areas) {
            currentScore = (int) this.distantTo(area.getCenter())*10;
//            currentScore = (int) Math.pow(this.distantTo(area.getCenter()), 2)*10;
            if (currentScore < 8) return 1;
            totalDist += currentScore;
        }
        return totalDist + 1;
    }

    public static boolean validPos(Position pos, WorldChunk chunk) {
        return pos.getX() >= 0 && pos.getY() >= 0 && pos.getX() < chunk.width && pos.getY() < chunk.height;
    }
    public static boolean validPos(Position pos, int worldWidth, int worldHeight) {
        return pos.getX() >= 0 && pos.getY() >= 0 && pos.getX() < worldWidth && pos.getY() < worldHeight;
    }
    public static boolean validPos(double x, double y, int worldWidth, int worldHeight) {
        return x >= 0 && y >= 0 && x < worldWidth && y < worldHeight;
    }
    public static boolean validPos(double x, double y, WorldChunk chunk) {
        return x >= 0 && y >= 0 && x < chunk.width && y < chunk.height;
    }

    public boolean isPosInteger() {
        if ((int) this.getX() != this.getX() || (int) this.getY() != this.getY()) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return Double.hashCode(xPos) + Double.hashCode(yPos) + "Position".hashCode();
    }

    @Override
    public boolean equals(Object o) {
        try {
            Position thatPos = (Position) o;
            return thatPos.getX() == getX() && thatPos.getY() == getY();
        } catch (ClassCastException e) {
            return false;
        }
    }
}
