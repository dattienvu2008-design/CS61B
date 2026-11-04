package Entity;

import tileengine.TETileRenderer;
import world.World;
import worldassets.Tile;

public class Player extends Entity{
    public final Cooldown movementCooldown = new Cooldown();

    public int getMovementCooldownMillis() {
        return movementCooldownMillis;
    }
    public void setMovementCooldownMillis(int movementCooldownMillis) {
        this.movementCooldownMillis = movementCooldownMillis;
    }
    private int movementCooldownMillis = 100;
    private World world;


    public Player(int health, int ID, TETileRenderer skin, Tile currentPos, World world) {
        super(health, ID, skin, currentPos);
    }
}
