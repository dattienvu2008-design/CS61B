package Entity;

import renderengine.TETileRenderer;
import worldassets.Tile;

public class Mob extends Entity{
    public Mob(int health, int ID, TETileRenderer skin, Tile currentPos) {
        super(health, ID, skin, currentPos);
    }
}
