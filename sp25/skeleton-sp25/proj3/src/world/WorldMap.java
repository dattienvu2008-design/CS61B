package world;

import tileengine.TETile;
import worldassets.Position;
import worldassets.InteractiveTile;

public class WorldMap {
    public final TETile[][] map;
    public final int width, height;
    public WorldMap(int width, int height) {
        this.width = width;
        this.height = height;
        map = new TETile[width][height];
    }

    // Getter:
    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    /**
     * Get Tile at position (x,y). Throw {@link IndexOutOfBoundsException} if (x,y) invalid
     * @param x Tile's xPos
     * @param y Tile's yPos
     * @return Tile at (x,y)
     */
    public TETile getTile(int x, int y) {
        if (!Position.validPos(x, y, width, height)) {
            throw new IndexOutOfBoundsException();
        }
        return map[x][y];
    }

    /**
     * Get Tile at position (x,y)
     * Throw {@link IndexOutOfBoundsException} if pos out of bounds.
     * Throw {@link IllegalArgumentException} if pos.getX() or pos.getY() is noninteger
     * @param pos Tile's pos
     * @return Tile at (x,y)
     */
    public TETile getTile(Position pos) {
        if (Position.validPos(pos, width, height)) {
            throw new IndexOutOfBoundsException();
        } else if (!pos.isPosInteger()) {
            throw new IllegalArgumentException();
        }
        return map[(int) pos.getX()][(int) pos.getY()];
    }

    // Setter:

    /**
     * Set tile at Position pos
     * @param pos Position to set tile
     * @param tile Tile that will be set at pos
     */
    void setTile(Position pos, TETile tile) {
        if (!pos.isPosInteger()) {
            throw new IllegalArgumentException();
        }
        map[(int) pos.getX()][(int) pos.getY()] = tile;
    }

    void setTile(int x, int y, TETile tile) {
        map[x][y] = tile;
    }
    // Private helper method
}
