package Entity;

import tileengine.TETileRenderer;
import worldassets.Tile;

public class Player extends Entity{
    public Player(int health, int ID, TETileRenderer skin, Tile currentPos) {
        super(health, ID, skin, currentPos);
    }
}
