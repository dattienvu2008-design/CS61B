package worldassets;

import world.World;
import world.WorldMap;

public class Position {
    private final double xPos, yPos;

    public Position(double xPos, double yPos) {
        this.xPos = xPos;
        this.yPos = yPos;
    }

    public double getX() {
        return xPos;
    }

    public double getY() {
        return yPos;
    }

    public double distantTo(Position p) {
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
        return totalDist;
    }

    public static boolean validPos(Position pos, World world) {
        return pos.getX() >= 0 && pos.getY() >= 0 && pos.getX() < world.getWidth() && pos.getY() < world.getHeight();
    }
    public static boolean validPos(Position pos, WorldMap world) {
        return pos.getX() >= 0 && pos.getY() >= 0 && pos.getX() < world.getWidth() && pos.getY() < world.getHeight();
    }
    public static boolean validPos(Position pos, int worldWidth, int worldHeight) {
        return pos.getX() >= 0 && pos.getY() >= 0 && pos.getX() < worldWidth && pos.getY() < worldHeight;
    }
    public static boolean validPos(double x, double y, World world) {
        return x >= 0 && y >= 0 && x < world.getWidth() && y < world.getHeight();
    }
    public static boolean validPos(double x, double y, int worldWidth, int worldHeight) {
        return x >= 0 && y >= 0 && x < worldWidth && y < worldHeight;
    }

    public boolean isPosInteger() {
        if ((int) this.getX() != this.getX() || (int) this.getY() != this.getY()) {
            return false;
        }
        return true;
    }
}
