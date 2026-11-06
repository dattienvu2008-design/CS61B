package Entity.Environment;

import Entity.Entity;
import renderengine.TETileRenderer;
import worldassets.tile.Tile;

// TODO: Implement later?
public class Warp extends Entity {
    public Warp getDest() {
        return dest;
    }
    public void setDest(Warp dest) {
        this.dest = dest;
    }
    private Warp dest;

    public Warp(int ID, TETileRenderer skin, Tile currentTile) {
        super(Double.POSITIVE_INFINITY, ID, skin, currentTile);
    }
}
