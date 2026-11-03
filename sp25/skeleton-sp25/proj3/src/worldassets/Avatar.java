package worldassets;

import tileengine.TETileRenderer;
import tileengine.Tileset;
import world.World;
import world.WorldMap;

public class Avatar {
    private double health;
    private final int ID;
    private Tile currentPos;
//    private WorldMap map;

    private TETileRenderer skin;
    public TETileRenderer getSkin() {
        return skin;
    }

    public Avatar(int health, int ID, TETileRenderer skin, Tile currentPos) {
        this.health = health;
        this.ID = ID;
        this.skin = skin;
        this.currentPos = currentPos;
        currentPos.setEntity(this);
    }

    public void move(Direction direction) {
        Tile destination = null;
        if (direction == Direction.UP) {
            destination = currentPos.getTopTile();
        } else if (direction == Direction.DOWN) {
            destination = currentPos.getBottomTile();
        } else if (direction == Direction.LEFT) {
            destination = currentPos.getLeftTile();
        } else if (direction == Direction.RIGHT) {
            destination = currentPos.getRightTile();
        }
        if (destination != null && !destination.getTileType().blockable) {
            destination.setEntity(this);
            currentPos.removeEntity();
            currentPos = destination;
        }
    }
}
