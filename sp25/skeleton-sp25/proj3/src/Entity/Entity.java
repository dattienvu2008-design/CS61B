package Entity;

import renderengine.TETileRenderer;
import world.WorldChunk;
import worldassets.Direction;
import worldassets.tile.Tile;

public class Entity {
    private double maxHealth;
    private double currentHealth;

    public double getMaxHealth() {
        return maxHealth;
    }
    public double getCurrentHealth() {
        return currentHealth;
    }
    public double getHealthPercentage() {
        return currentHealth/maxHealth;
    }

    /**
     * Apply damage on this entity.
     * @param damage Value of damage that this entity will be taken
     * @return True if this entity is dead (currentHealth < 0), false otherwise
     */
    public boolean takeDamage(double damage) {
        currentHealth -= damage;
        if (currentHealth > 0) {
            return false;
        } else {
            currentHealth = 0;
            this.currentTile.removeEntity();
            return true;
        }
    }
    public void heal(double healAmount) {
        currentHealth += healAmount;
        if (currentHealth > maxHealth) {
            currentHealth = maxHealth;
        }
    }

    private final int ID;
    private Tile currentTile;
    public Tile getCurrentTile() {
        return currentTile;
    }
    protected void setCurrentTile(Tile tile) {
        currentTile = tile;
    }
//    private WorldMap map;

    private TETileRenderer skin;
    public TETileRenderer getSkin() {
        return skin;
    }
    public void setSkin(TETileRenderer newSkin) {
        skin = newSkin;
    }

    public Entity(int maxHealth, int ID, TETileRenderer skin, Tile currentTile) {
        this.maxHealth = this.currentHealth = maxHealth;
        this.ID = ID;
        this.skin = skin;
        this.currentTile = currentTile;
        currentTile.moveEntityToThis(this);
    }

    public Entity(double maxHealth, int ID, TETileRenderer skin, Tile currentTile) {
        this.maxHealth = this.currentHealth = maxHealth;
        this.ID = ID;
        this.skin = skin;
        this.currentTile = currentTile;
        currentTile.moveEntityToThis(this);
    }

    public void move(Direction direction) {
        Tile destination = direction.getTile(currentTile);
        if (destination != null && destination.isTileWalkable()) {
            destination.moveEntityToThis(this);
            currentTile.removeEntity();
            currentTile = destination;
        }
    }

    public void teleport(Tile destination) {
        if (destination != null && destination.isTileWalkable()) {
            destination.moveEntityToThis(this);
            currentTile.removeEntity();
            currentTile = destination;
        }
    }
}
