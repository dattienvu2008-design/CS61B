package Entity;

import tileengine.TETileRenderer;
import worldassets.Direction;
import worldassets.Tile;

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
     * Apply damage on this entity. Return True if this entity is dead (currentHealth < 0), false otherwise
     * @param damage
     * @return
     */
    public boolean takeDamage(double damage) {
        currentHealth -= damage;
        if (currentHealth > 0) {
            return false;
        } else {
            currentHealth = 0;
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
//    private WorldMap map;

    private TETileRenderer skin;
    public TETileRenderer getSkin() {
        return skin;
    }

    public Entity(int maxHealth, int ID, TETileRenderer skin, Tile currentTile) {
        this.maxHealth = this.currentHealth = maxHealth;
        this.ID = ID;
        this.skin = skin;
        this.currentTile = currentTile;
        currentTile.setEntity(this);
    }

    public void move(Direction direction) {
        Tile destination = direction.getTile(currentTile);
        if (destination != null && !destination.getTileType().blockable) {
            destination.setEntity(this);
            currentTile.removeEntity();
            currentTile = destination;
        }
    }
}
