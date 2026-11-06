package worldassets;

import worldassets.tile.Tile;

import java.io.Serializable;

public enum Direction implements Serializable {
    LEFT, RIGHT, UP, DOWN;

    public Tile getTile(Tile tile) {
        if (this == LEFT) {
            return tile.getLeftTile();
        } else if (this == RIGHT) {
            return tile.getRightTile();
        } else if (this == UP) {
            return tile.getTopTile();
        } else if (this == DOWN) {
            return tile.getBottomTile();
        }
        return null;
    }
}
